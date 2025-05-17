ARG ARG_JAVA_VERSION=17

ARG ARG_UBUNTU_VERSION=noble-20250415.1

ARG ARG_SPRING_BOOT_USER=javauser
ARG ARG_SPRING_BOOT_GROUP=javauser
ARG ARG_SPRING_BOOT_USER_ID=2000
ARG ARG_SPRING_BOOT_GROUP_ID=2000
ARG ARG_SPRING_BOOT_DIRECTORY=/opt/server
ARG ARG_SPRING_BOOT_LAUNCHER=org.springframework.boot.loader.launch.JarLauncher

################################################################################
# create application layers
################################################################################
FROM eclipse-temurin:${ARG_JAVA_VERSION}-jdk-jammy as layered-app

ARG ARG_JAVA_VERSION

WORKDIR workdir
COPY target/bhcb-backend.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

################################################################################
# create a custom jre by analyzing the app.jar
################################################################################
FROM eclipse-temurin:${ARG_JAVA_VERSION}-jdk as jre-builder

ARG ARG_JAVA_VERSION

WORKDIR workdir
COPY target/bhcb-backend.jar app.jar

RUN set -ex \
    && jar xf app.jar \
    && jdeps \
        --ignore-missing-deps \
        --print-module-deps \
        --multi-release ${ARG_JAVA_VERSION} \
        --recursive \
        --class-path 'BOOT-INF/lib/*' \
        app.jar >> modules.txt \
    && if [ ${ARG_JAVA_VERSION} -lt 22 ]; then echo "jdk.crypto.ec,`cat modules.txt`" > modules.txt; fi \
    && $JAVA_HOME/bin/jlink \
         --add-modules $(cat modules.txt) \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime \
    && strip -p --strip-unneeded /javaruntime/lib/server/libjvm.so \
    && find /javaruntime -name '*.so' | xargs -i strip -p --strip-unneeded {}

################################################################################
# create final image
################################################################################
FROM ubuntu:${ARG_UBUNTU_VERSION}

# intentional declaring arg again
ARG ARG_SPRING_BOOT_USER
ARG ARG_SPRING_BOOT_GROUP
ARG ARG_SPRING_BOOT_USER_ID
ARG ARG_SPRING_BOOT_GROUP_ID
ARG ARG_SPRING_BOOT_DIRECTORY
ARG ARG_SPRING_BOOT_LAUNCHER

ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"

# copy custom jre
COPY --from=jre-builder /javaruntime $JAVA_HOME

# create dedicated user
ENV ENV_SPRING_BOOT_USER=${ARG_SPRING_BOOT_USER}
ENV ENV_SPRING_BOOT_GROUP=${ARG_SPRING_BOOT_GROUP}
ENV ENV_SPRING_BOOT_USER_ID=${ARG_SPRING_BOOT_USER_ID}
ENV ENV_SPRING_BOOT_GROUP_ID=${ARG_SPRING_BOOT_GROUP_ID}
ENV ENV_SPRING_BOOT_DIRECTORY=${ARG_SPRING_BOOT_DIRECTORY}

RUN set -ex \
    && apt-get -qq update \
    && apt-get -qq -y upgrade \
    && groupadd --gid "${ENV_SPRING_BOOT_GROUP_ID}" "${ENV_SPRING_BOOT_GROUP}" \
    && useradd \
       --uid "${ENV_SPRING_BOOT_USER_ID}" \
       --gid "${ENV_SPRING_BOOT_GROUP}" \
       --no-create-home \
       --home /nonexistent \
       --comment "${ENV_SPRING_BOOT_USER}" \
       --shell /bin/false \
       "${ENV_SPRING_BOOT_USER}" \
    && install -d -m 0750 -o "${ENV_SPRING_BOOT_USER}" -g "${ENV_SPRING_BOOT_GROUP}" "${ENV_SPRING_BOOT_DIRECTORY}" \
    && chmod g+s "${ENV_SPRING_BOOT_DIRECTORY}"

WORKDIR "${ENV_SPRING_BOOT_DIRECTORY}"

# copy layers of app.jar
COPY --chown="${ENV_SPRING_BOOT_USER}":"${ENV_SPRING_BOOT_GROUP}" --from=layered-app /workdir/snapshot-dependencies/ ./
COPY --chown="${ENV_SPRING_BOOT_USER}":"${ENV_SPRING_BOOT_GROUP}" --from=layered-app /workdir/dependencies/ ./
COPY --chown="${ENV_SPRING_BOOT_USER}":"${ENV_SPRING_BOOT_GROUP}" --from=layered-app /workdir/spring-boot-loader/ ./
COPY --chown="${ENV_SPRING_BOOT_USER}":"${ENV_SPRING_BOOT_GROUP}" --from=layered-app /workdir/application/ ./

USER "${ENV_SPRING_BOOT_USER}"

ENV ENV_SPRING_BOOT_LAUNCHER=${ARG_SPRING_BOOT_LAUNCHER}
CMD java "${ENV_SPRING_BOOT_LAUNCHER}"
