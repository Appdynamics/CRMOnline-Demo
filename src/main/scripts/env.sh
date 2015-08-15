#!/bin/bash

if [ -z ${CONTROLLER_HOST} ]
then
    echo "CONTROLLER_HOST not set, defaulting to localhost"
    CONTROLLER_HOST=localhost
else
    echo "CONTROLLER_HOST: ${CONTROLLER_HOST}"
fi

if [ -z ${CONTROLLER_PORT} ]
then
    echo "CONTROLLER_PORT not set, defaulting to 8090"
    CONTROLLER_PORT=8090
else
    echo "CONTROLLER_PORT: ${CONTROLLER_PORT}"
fi

if [ -z ${JAVA_AGENT_PATH} ]
then
    JAVA_AGENT_PATH=/AppDynamics/javaagent.jar
    echo "JAVA_AGENT_PATH: ${JAVA_AGENT_PATH}"
else
    echo "JAVA_AGENT_PATH: ${JAVA_AGENT_PATH}"
fi

if [ -z ${CONTROLLER_USER} ]
then
    echo "CONTROLLER_USER not Found, using default"
    CONTROLLER_USER=user1
else
    echo "CONTROLLER_USER: ${CONTROLLER_USER}"
fi

if [ -z ${CONTROLLER_PWD} ]
then
    echo "CONTROLLER_PWD not Found, using default"
    CONTROLLER_PWD=welcome
else
    echo "CONTROLLER_PWD: ${CONTROLLER_PWD}"
fi

if [ -z ${CONTROLLER_ACCOUNT} ]
then
    echo "CONTROLLER_ACCOUNT not Found, using default"
    CONTROLLER_ACCOUNT=customer1
else
    echo "CONTROLLER_ACCOUNT: ${CONTROLLER_ACCOUNT}"
fi

if [ -z "${ACCOUNT_NAME}" ]
then
    echo "ACCOUNT_NAME NOT Found, setting to default"
    ACCOUNT_NAME=customer1
fi

# This is the port on which active mq starts
ACTIVEMQ_PORT=61617
