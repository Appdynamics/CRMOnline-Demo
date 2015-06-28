#!/bin/bash

source ~/appdynamics-env.sh

if [ "$CONTROLLER_HOST" = "" ]
then
    echo "CONTROLLER_HOST NOT Found, setting it to localhost"
    CONTROLLER_HOST=localhost
fi

if [ "$CONTROLLER_PORT" = "" ]
then
    echo "CONTROLLER_PORT NOT Found, setting it to 8090"
    CONTROLLER_PORT=8090
fi

if [ "$JAVA_AGENT_PATH" = "" ]
then
    echo "JAVA_AGENT_PATH NOT Found, setting it to /Users/schoudhury/dev/appdynamics/appagent/javaagent.jar"
    JAVA_AGENT_PATH=/Users/schoudhury/dev/appdynamics/appagent/javaagent.jar
fi

if [ "$ACCOUNT_NAME" = "" ]
then
echo "ACCOUNT_NAME NOT Found, setting to default"
ACCOUNT_NAME=customer1
fi

if [ "$ACCOUNT_ACCESS_KEY" = "" ]
then
echo "ACCOUNT_ACCESS_KEY NOT Found, setting to default"
ACCOUNT_ACCESS_KEY=
fi

# This is the port on which active mq starts
ACTIVEMQ_PORT=61617
