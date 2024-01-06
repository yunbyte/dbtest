#!/bin/bash
PRG="${0}"
PRGDIR=`dirname ${PRG}`
CURRENT_HOME=`cd "${PRGDIR}/.." > /dev/null;pwd `
APP=dbtest
pidfile=${CURRENT_HOME}"/${APP}.pid"

TestPid(){

    if [ -f ${pidfile} ] ; then
       if ps ux | grep `cat ${pidfile}`| grep "${CURRENT_HOME}" | grep -v "grep" > /dev/null ; then
          kill `cat ${pidfile}`
          echo "${APP} stop success"
       else
         "the ${APP} is not running"
       fi
    else
      echo "${APP} is not running"
   fi
}

TestPid

rm -rf ${pidfile}
