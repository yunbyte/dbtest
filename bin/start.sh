#!/bin/bash
PRG="${0}"
PRGDIR=`dirname ${PRG}`
CURRENT_HOME=`cd "${PRGDIR}/.." > /dev/null;pwd `
PORT=8088
APP=dbtest
pidfile=${CURRENT_HOME}/${APP}.pid
LOGDIR=${CURRENT_HOME}/logs/${APP}-${PORT}-console
export LANG=zh_CN.UTF-8
export JAVA_HOME=${CURRENT_HOME}/jdk1.8.0_251
export CLASSPATH=.:${JAVA_HOME}/jar/lib/rt.jar:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar
export PATH=${JAVA_HOME}"/bin":$PATH

TestPid(){
    if [ -f ${pidfile} ] ; then
        if ps ux | grep `cat $pidfile` | grep "${CURRENT_HOME}" | grep -v "grep" > /dev/null ; then
            echo "`date +"%F %T"`: `basename $0` is running!  main process id = $(cat ${pidfile})"
            exit 1
        else
            rm -f $pidfile
        fi
    fi
}
# Trap
trap "rm -f ${pidfile} ; exit 1 " 1 2 3 15
TestPid
# create log dir
if [ ! -d ${LOGDIR} ] ; then
    mkdir ${LOGDIR}
fi

cd ${CURRENT_HOME}
nohup java -Xms1024m -Xmx1024m -Djava.awt.headless=true -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -Dfile.encoding=UTF-8 -cp ${CURRENT_HOME}/conf:${CURRENT_HOME}/* com.yunbyte.dbtest.DbTestApplication 1>${LOGDIR}/${APP}-${PORT}.out 2>${LOGDIR}/${APP}-${PORT}.err &
echo "${APP} start successful"
echo $! > ${pidfile}


