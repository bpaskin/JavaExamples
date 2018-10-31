#!/bin/bash
declare -i MAX=10
declare -i SUM=0
for i in {1..10}; do
   rm /tmp/results.txt
   jmeter -n -t "PerfRESTJEEMulti.jmx"
   NUM=`sed -e 1b -e '$!d' /tmp/results.txt | sed '1!G;h;$!d' |  cut -f1 -d, | awk '{print $1}'| paste -sd- - | bc`
   SUM=$(($SUM+$NUM))	
   sleep 20
done

echo $(($SUM / $MAX))
