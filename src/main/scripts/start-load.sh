#! /bin/bash
echo "Starting load gen server..."
./stop-load.sh
nohup java -cp crmonline-demo.war -Dcrmonline-demo-load=true com.appdynamics.loadgen.CRMOnlineLoadGen
