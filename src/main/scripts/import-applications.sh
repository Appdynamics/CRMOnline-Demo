#!/bin/bash

source env.sh

baseUrl=http://$CONTROLLER_HOST:$CONTROLLER_PORT/controller
importApp() {
    echo "Importing the Application $2"
    curl -s -v --basic --user "$CONTROLLER_USER@$CONTROLLER_ACCOUNT:$CONTROLLER_PWD" -F $2=@$1 "$baseUrl/ConfigObjectImportExportServlet"
}

importApp applications/CRMOnline.xml "CRMOnline"
