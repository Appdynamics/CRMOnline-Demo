## CRMOnline Demo App
Source code and exported application configuration to demo AppDynamics Transaction Analytics.
 
### Build from source
1. mvn clean install
2. This will create a directory target/crmonline-demo-dist

### Installation
1. Edit the file env.sh and update the controller and agent details [Uncomment the agent path]
2. 'import-applications.sh': Import the Application from the applications directory into the controller

### Start
1. Start the Apps: 'start-servers.sh'
2. Start Load: 'start-load.sh'

### Stop
1. 'stop-servers.sh'
2. 'stop-load.sh'

### Docker
1. Add AppServerAgent.zip, AnalyticsAgent.zip, Oracle JDK 7 and Maven distros to build directory
2. Build with: `docker build -t appdynamics/telecom-demo .`
3. Run with: `docker run --name crmonline -d -e CONTROLLER_HOST=<host> -e CONTROLLER_PORT=<port> -e CONTROLLER_USER=<user> -e CONTROLLER_PWD=<pwd> -e ACCOUNT_NAME=<account> -e ACCOUNT_ACCESS_KEY=<key> appdynamics/crmonline-demo`
4. See [env.sh](https://github.com/Appdynamics/CRMOnline-Demo/blob/master/src/main/scripts/env.sh) for details of environment variables
5. To start Analytics Agent: `docker exec -it crmonline start-analytics <events-service-endpoint>` where `<events-service-endpoint>` is `host:port`
