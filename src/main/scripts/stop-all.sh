while read line
do
    if [ -z "$line" ]
    then
        echo ""
    else
        echo "Killing the process $line"
        kill -15 $line
    fi
done < pids.txt
echo "" > pids.txt

