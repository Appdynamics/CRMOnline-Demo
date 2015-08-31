FROM centos:centos6

RUN yum -y install unzip
RUN yum -y install tar
RUN yum -y install ntp

# Install Oracle JDK
ADD jdk-7u79-linux-x64.rpm /
RUN rpm -Uvh /jdk-7u79-linux-x64.rpm
RUN rm /jdk-7u79-linux-x64.rpm

# Install Apache Maven
ADD apache-maven-3.3.3-bin.tar.gz /usr/local/
RUN ln -s /usr/local/apache-maven-3.3.3/bin/mvn /usr/bin/mvn

ENV JAVA_HOME /usr/java/default
ENV PATH $PATH:$JAVA_HOME/bin

RUN mkdir -p /AppDynamics
ADD AppServerAgent.zip /AppDynamics/
RUN cd /AppDynamics && unzip -q AppServerAgent.zip && rm AppServerAgent.zip

RUN mkdir -p /CRM-Demo
COPY src /CRM-Demo/src
ADD pom.xml /CRM-Demo/

RUN cd /CRM-Demo && mvn clean install
ADD docker-start.sh /CRM-Demo/target/crmonline-demo-dist/
RUN chmod +x /CRM-Demo/target/crmonline-demo-dist/docker-start.sh
RUN chmod +x /CRM-Demo/target/crmonline-demo-dist/import-applications.sh
RUN chmod +x /CRM-Demo/target/crmonline-demo-dist/start-servers.sh
RUN chmod +x /CRM-Demo/target/crmonline-demo-dist/start-load.sh
RUN chmod +x /CRM-Demo/target/crmonline-demo-dist/stop-servers.sh
RUN chmod +x /CRM-Demo/target/crmonline-demo-dist/stop-load.sh

ENV ANALYTICS_AGENT_HOME /analytics-agent
ADD AnalyticsAgent.zip /
RUN unzip AnalyticsAgent.zip
RUN rm -f AnalyticsAgent.zip
ADD start-analytics.sh /usr/bin/start-analytics
RUN chmod 744 /usr/bin/start-analytics

WORKDIR /CRM-Demo/target/crmonline-demo-dist/
CMD ["bash", "docker-start.sh"]
