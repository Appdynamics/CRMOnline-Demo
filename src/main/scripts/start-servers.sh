#!/bin/bash
source env.sh
echo "Starting CRM Demo Servers..."

./stop-servers.sh

export PROP="-Xms512m -Xmx512m -Dactivemq.port=$ACTIVEMQ_PORT -Djdbc.connectionUrl=$MYSQL_URL -Djdbc.username=$MYSQL_USER -Djdbc.password=$MYSQL_PWD -Dappdynamics.controller.hostName=$CONTROLLER_HOST -Dappdynamics.controller.port=$CONTROLLER_PORT -Dappdynamics.agent.accountName=${ACCOUNT_NAME%%_*} -Dappdynamics.agent.accountAccessKey=$ACCOUNT_ACCESS_KEY -Dappdynamics.controller.ssl.enabled=false -Dtelecom-demo-server=true"

export WAR_PATH="crmonline-demo.war"

java  $PROP -Djetty.port=8800 -Dappdynamics.agent.applicationName=CRMOnline -Dappdynamics.agent.tierName=CRMOnline_Tier -Dappdynamics.agent.nodeName=CRMOnline_Node1 -javaagent:$JAVA_AGENT_PATH  -jar $WAR_PATH &

echo "Done"
