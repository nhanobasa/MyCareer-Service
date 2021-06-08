#!/bin/sh
SERVER=$(curl ifconfig.co)
JMX_PORT=10699

CALMOB_HOME=$(dirname "$0")
JMX_PARAM=" -Djava.rmi.server.hostname=$SERVER -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
DEBUG_PARAM="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5555 "
echo $JMX_PARAM
cd "$CALMOB_HOME"

java -Xmx2048M -Xms312M $JMX_PARAM -cp conf/:target/*:target/libs/*:libs/*:src/main/resources/ vn.vccorp.adtech.bigdata.ServiceAnalyticMain "$@" &
echo $! >pid
