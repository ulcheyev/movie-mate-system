#!/bin/bash

BRANCH="test"
DOCKER_REGISTRY="movie-mate"

mvn clean install -DskipTests
mvn spring-boot:build-image -Ddocker.registry=$DOCKER_REGISTRY -DskipTests

VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

export VERSION=$VERSION
export DOCKER_REGISTRY=$DOCKER_REGISTRY

echo "Using version: $VERSION and registry: $DOCKER_REGISTRY"

echo "Restarting services with Docker Compose"
docker compose down
docker compose --env-file ./.env  up


