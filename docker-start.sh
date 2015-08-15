#/bin/bash
(cd /CRM-Demo/target/crmonline-demo-dist; ./import-applications.sh && ./start-servers.sh && ./start-load.sh)
