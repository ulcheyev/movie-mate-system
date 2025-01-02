#!/bin/bash

BRANCH="test"
DOCKER_REGISTRY="movie-mate"

echo "Pulling the latest code from branch: $BRANCH"
git fetch origin $BRANCH
git checkout $BRANCH
git pull origin $BRANCH


mvn spring-boot:build-image -Ddocker.registry=$DOCKER_REGISTRY -DskipTests

export VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
export DOCKER_REGISTRY=$DOCKER_REGISTRY

echo "Using version: $VERSION and registry $DOCKER_REGISTRY"

echo "Restarting services with Docker Compose"
docker-compose down
docker-compose up -d --build


