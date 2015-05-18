## CRMOnline Demo App
 The source code and the exported application to demo the analytics functionality.
 
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

