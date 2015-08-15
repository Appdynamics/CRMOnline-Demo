#!/bin/bash

DEMO_SERVERS="$(ps -ef|grep crmonline-demo-server|grep -v grep|awk '{print $2}')"

if [ ! -z "${DEMO_SERVERS}" ]; then
    kill -9 "${DEMO_SERVERS}"
fi
