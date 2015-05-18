./stop-all.sh
source env.sh
export PROP="-Dactivemq.port=$ACTIVEMQ_PORT -Djdbc.connectionUrl=$MYSQL_URL -Djdbc.username=$MYSQL_USER -Djdbc.password=$MYSQL_PWD -Dappdynamics.controller.host=$CONTROLLER_HOST -Dappdynamics.controller.port=$CONTROLLER_PORT -Dappdynamics.controller.ssl.enabled=false"
export WAR_PATH="../../../target/crmonline-demo.war"

java  -Djetty.port=8800 -Dappdynamics.agent.applicationName=CRMOnline -Dappdynamics.agent.tierName=CRMOnline_Tier -Dappdynamics.agent.nodeName=CRMOnline_Node1 -javaagent:$JAVA_AGENT_PATH  -jar crmonline.war &
echo $! >> pids.txt

java -jar crmonline-loadgen.jar
echo $! >> pids.txt


