#!/bin/bash

DOCKER_IMAGE_TAG=bhcb-backend
DOCKER_IMAGE_DT_TAG=$DOCKER_IMAGE_TAG-$(date +%Y%m%d%H%M)

./mvnw clean compile package
docker build -t $DOCKER_IMAGE_TAG -t $DOCKER_IMAGE_DT_TAG .
