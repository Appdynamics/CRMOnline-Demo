#!/bin/bash

./stop-servers.sh

source env.sh

export PROP="-Xms512m -Xmx512m -Dactivemq.port=$ACTIVEMQ_PORT -Djdbc.connectionUrl=$MYSQL_URL -Djdbc.username=$MYSQL_USER -Djdbc.password=$MYSQL_PWD -Dappdynamics.controller.hostName=$CONTROLLER_HOST -Dappdynamics.controller.port=$CONTROLLER_PORT -Dappdynamics.controller.ssl.enabled=false -Dcrmonline-demo-server=true"

export WAR_PATH="crmonline-demo.war"

java  $PROP -Djetty.port=8800 -Dappdynamics.agent.applicationName=CRMOnline -Dappdynamics.agent.tierName=CRMOnline_Tier -Dappdynamics.agent.nodeName=CRMOnline_Node1 -javaagent:$JAVA_AGENT_PATH  -jar $WAR_PATH &
