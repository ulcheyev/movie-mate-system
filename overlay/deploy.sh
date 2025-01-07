#!/bin/bash

BRANCH="test"
DOCKER_REGISTRY="movie-mate"
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

export VERSION=$VERSION
export DOCKER_REGISTRY=$DOCKER_REGISTRY

docker compose down --rmi all

mvn clean install -DskipTests
mvn spring-boot:build-image -Ddocker.registry=$DOCKER_REGISTRY -DskipTests

echo "Using version: $VERSION and registry: $DOCKER_REGISTRY"

echo "Restarting services with Docker Compose"

docker compose --env-file ./.env  up -d


