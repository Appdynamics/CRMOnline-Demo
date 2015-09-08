#! /bin/bash

# This script is provided for illustration purposes only.
#
# To build these Docker containers, you will need to download the following components:
# 1. An appropriate version of the Oracle Java 7 JDK
#    (http://www.oracle.com/technetwork/java/javase/downloads/index.html)
# 2. Correct versions for the AppDynamics App Server Agent and Analytics Agent 
#    (https://download.appdynamics.com)

ORACLE_JDK7=jdk-linux-x64.rpm

cleanUp() {
  # Cleanup temp dir and files
  rm -rf .appdynamics
  rm -f cookies.txt index.html*

  # Remove dangling images left-over from build
  if [[ `docker images -q --filter "dangling=true"` ]] 
  then
    echo
    echo "Deleting intermediate containers..."
    docker images -q --filter "dangling=true" | xargs docker rmi -f;
  fi
}
trap cleanUp EXIT

downloadOracleJDK() {
  JDK7_DOWNLOAD=http://download.oracle.com/otn-pub/java/jdk/7u71-b14/jdk-7u71-linux-x64.rpm

  # Install Oracle Java 7
  wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie	" ${JDK7_DOWNLOAD} -O jdk-linux-x64.rpm
}

promptForInstallers() {
  read -e -p "Enter path to App Server Agent: " APP_SERVER_AGENT
  cp ${APP_SERVER_AGENT} .appdynamics/AppServerAgent.zip
  read -e -p "Enter path to Analytics Agent: " ANALYTICS_AGENT
  cp ${ANALYTICS_AGENT} .appdynamics/AnalyticsAgent.zip
}

# Build docker container
buildDemoContainer() {
  echo
  echo "Building Telecom-Demo Container (appdynamics/telecom-demo)"
  echo
  cp .appdynamics/AppServerAgent.zip .
  cp .appdynamics/AnalyticsAgent.zip .
  (docker build --no-cache -t appdynamics/crmonline-demo .)
}

# Temp dir for agents
mkdir -p .appdynamics

# Prompt for location of App Server and Analytics Agents if called without arguments
if  [ $# -eq 0 ]
then
  promptForAgents
else
  # Allow user to specify locations of App Server and Analytics Agents
  while getopts "a:y:j:" opt; do
    case $opt in
      a)
        APP_SERVER_AGENT=$OPTARG
        if [ ! -e ${APP_SERVER_AGENT} ]
        then
          echo "Not found: ${APP_SERVER_AGENT}"
          exit
        fi
        cp ${APP_SERVER_AGENT} .appdynamics/AppServerAgent.zip
        ;;
      y)
        ANALYTICS_AGENT=$OPTARG
        if [ ! -e ${ANALYTICS_AGENT} ]
        then
          echo "Not found: ${ANALYTICS_AGENT}"
          exit
        fi
        cp ${ANALYTICS_AGENT} .appdynamics/AnalyticsAgent.zip
        ;;
      j)
        JDK7_OPTARG=$OPTARG
        if [ ! -e ${JDK7_OPTARG} ]
        then
          echo "Not found: ${JDK7_OPTARG}"
          exit
        fi
        cp ${JDK7_OPTARG} ${ORACLE_JDK7}
        ;;
      \?)
        echo "Invalid option: -$OPTARG"
        exit
        ;;
    esac
  done
fi

if [ ! -e ${ORACLE_JDK7} ]; then
  downloadOracleJDK
fi

buildDemoContainer
