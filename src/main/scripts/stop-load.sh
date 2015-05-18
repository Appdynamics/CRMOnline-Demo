# while read line
# do
#     if [ -z "$line" ]
#     then
#         echo ""
#     else
#         echo "Killing the process $line"
#         kill -9 $line
#     fi
# done < load-pids.txt
# echo "" > load-pids.txt

kill -9 $(ps -ef|grep crmonline-demo-load|grep -v grep|awk '{print $2}')