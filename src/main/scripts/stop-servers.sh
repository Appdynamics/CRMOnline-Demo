#!/bin/bash

# while read line
# do
#     if [ -z "$line" ]
#     then
#         echo ""
#     else
#         echo "Killing the process $line"
#         kill -9 $line
#     fi
# done < pids.txt
# echo "" > pids.txt

kill -9 $(ps -ef|grep crmonline-demo-server|grep -v grep|awk '{print $2}')