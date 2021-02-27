#!/bin/sh

DEFAULT_MODE='env'
MODE=${1:-$DEFAULT_MODE}

checkStatus() {
  status=$?

  if test $status -ne 0; then
    echo "\"$1\" step execution completed with code $status. Process stop!"
    exit $status
  fi
}

buildService() {
  COMPOSE_CONTENT="docker-compose"

  for var in "$@"; do
    COMPOSE_CONTENT="$COMPOSE_CONTENT -f ./env/files/docker-compose.$var.yml"
  done

  COMPOSE_CONTENT="$COMPOSE_CONTENT build"

  echo "Send to console: $COMPOSE_CONTENT"
  $COMPOSE_CONTENT 2>&1

  checkStatus buildService
}

upNetwork() {
  BUIDL_NETWORK="docker network create env_global-routing-network"
  echo "Send to console: $BUIDL_NETWORK"
  $BUIDL_NETWORK 2>&1
}

upService() {
  COMPOSE_CONTENT="docker-compose"

  for var in "$@"; do
    COMPOSE_CONTENT="$COMPOSE_CONTENT -f ./env/files/docker-compose.$var.yml"
  done

  COMPOSE_CONTENT="$COMPOSE_CONTENT up"

  echo "Send to console: $COMPOSE_CONTENT"
  $COMPOSE_CONTENT 2>&1

  checkStatus upService
}

downService() {
  COMPOSE_CONTENT="docker-compose"

  for var in "$@"; do
    COMPOSE_CONTENT="$COMPOSE_CONTENT -f ./env/files/docker-compose.$var.yml"
  done

  COMPOSE_CONTENT="$COMPOSE_CONTENT down"

  echo "Send to console: $COMPOSE_CONTENT"
  $COMPOSE_CONTENT 2>&1

  checkStatus downService
}

buildMicroservice() {
  MVN="mvn clean install -Dmaven.test.skip=true -f ./pom.xml"

  echo "Build microservice: $MVN"
  $MVN 2>&1

  checkStatus buildMicroservice
}

cd "$(dirname "$0")" || exit
cd ".." || exit

case $MODE in
"env")
  downService kafka

  buildService kafka

  upNetwork
  upService kafka
  ;;
esac

echo "Error:Unsupported argument - $MODE"
echo "Please read project README.md file # "
echo "If you got any problems with service hosts or dns names, please read README.md section 'Hosts setting'"
exit 1
