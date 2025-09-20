#!/bin/bash
# run "export DOCKER_PASSWORD=your_dockerhub_password" before script (./build-and-push.sh)

# Exit immediately if a command exits with a non-zero status
set -e

# Variables
DOCKER_USERNAME="prajjwalgupta0973"
IMAGE_NAME="payroll-calculator"
TAG="latest"
FULL_IMAGE_NAME=$DOCKER_USERNAME/$IMAGE_NAME:$TAG

# Build Docker image
echo "Building Docker image..."
docker build -t $FULL_IMAGE_NAME .
# Login to Docker Hub using environment variable
if [ -z "$DOCKER_PASSWORD" ]; then
  echo "Error: Please set DOCKER_PASSWORD environment variable."
  exit 1
fi
echo "Logging in to Docker Hub..."
echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
    
# Push image to Docker Hub
echo "Pushing Docker image to Docker Hub..."
docker push $FULL_IMAGE_NAME

echo "Docker image docker push $FULL_IMAGE_NAME successfully built and pushed!"
