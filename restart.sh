#!/bin/sh
PID=$1

if [ -z "$PID" ]; then
  PID=$(cat pid)
fi
echo $PID

if [ -n "$PID" -a -e /proc/$PID ]; then
  echo "process exists, start kill and restart"
  kill $PID
  nohup ./run.sh >>nohup.out 2>&1
else
  echo "process does not exists, please check it"
fi
sleep 1
tail -f -n -200 logs/log.log
