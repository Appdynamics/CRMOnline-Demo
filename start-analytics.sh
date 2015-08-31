#! /bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: start-analytics <events-service-endpoint>"
    exit
fi

EVENT_ENDPOINT=${1}
ACCOUNT_NAME=${ACCOUNT_NAME}
ACCESS_KEY=${ACCOUNT_ACCESS_KEY}

# Configure analytics-agent.properties
aaprop=${ANALYTICS_AGENT_HOME}/conf/analytics-agent.properties

if [ "$(grep '^http.event.endpoint=' $aaprop)" ]; then
        echo "${aaprop}: setting event.endpoint: ${EVENT_ENDPOINT}"
        sed -i "/^http.event.endpoint=/c\http.event.endpoint=http:\/\/${EVENT_ENDPOINT}\/v1" ${aaprop}
else
        echo "${aaprop}: http.event.endpoint property not found"
fi

if [ "$(grep '^http.event.accountName=' $aaprop)" ]; then
        echo "${aaprop}: setting event.accountName: ${ACCOUNT_NAME}"
        sed -i "/^http.event.accountName=/c\http.event.accountName=${ACCOUNT_NAME}" ${aaprop}
else
        echo "${aaprop}: http.event.accountName property not found"
fi

if [ "$(grep '^http.event.accessKey=' $aaprop)" ]; then
        echo "${aaprop}: setting event.accessKey: ${ACCESS_KEY}"
        sed -i "/^http.event.accessKey=/c\http.event.accessKey=${ACCESS_KEY}" ${aaprop}
else
        echo "${aaprop}: http.event.accessKey property not found"
fi

# Configure monitor.xml
monxml=${ANALYTICS_AGENT_HOME}/monitor.xml

if [ "$(grep '<enabled>false</enabled>' $monxml)" ]; then
        echo "${monxml}: setting to "true""
        sed -i 's#<enabled>false</enabled>#<enabled>true</enabled>#g' ${monxml}
else
        echo "${monxml}: already enabled or doesn't exist"
fi

echo "Starting Analytics Agent..."
(cd ${ANALYTICS_AGENT_HOME}; sh bin/analytics-agent start)
