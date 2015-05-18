#!/bin/bash

source env.sh
source ~/appdynamics-env.sh

if [ "$CONTROLLER_USER" = "" ]
then
    echo "CONTROLLER_USER NOT Found, using default"
    CONTROLLER_USER=user1
fi

if [ "$CONTROLLER_PWD" = "" ]
then
    echo "CONTROLLER_PWD NOT Found, using default"
    CONTROLLER_PWD=welcome
fi

if [ "$CONTROLLER_ACCOUNT" = "" ]
then
    echo "CONTROLLER_ACCOUNT NOT Found, using default"
    CONTROLLER_ACCOUNT=customer1
fi

baseUrl=http://$CONTROLLER_HOST:$CONTROLLER_PORT/controller
importApp() {
    echo "Importing the Application $2"
    curl -s -v --basic --user "$CONTROLLER_USER@$CONTROLLER_ACCOUNT:$CONTROLLER_PWD" -F $2=@$1 "$baseUrl/ConfigObjectImportExportServlet"
}

importApp applications/CRMOnline.xml "CRMOnline"




