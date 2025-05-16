FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR application
COPY target/bhcb-backend.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jdk-jammy
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENTRYPOINT java "org.springframework.boot.loader.launch.JarLauncher"