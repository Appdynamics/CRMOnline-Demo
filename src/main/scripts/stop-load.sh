#! /bin/bash
LOAD_SERVER="$(ps -ef|grep crmonline-demo-load|grep -v grep|awk '{print $2}')"

if [ ! -z "${LOAD_SERVER}" ]; then
    kill -9 "${LOAD_SERVER}"
fi
