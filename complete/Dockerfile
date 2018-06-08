FROM tomcat:9.0.8-jre8

ADD build/libs/complete.war /usr/local/tomcat/webapps/complete.war

ADD tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
ADD server.xml /usr/local/tomcat/conf/server.xml
ADD gcp_spring_poc.json /usr/local/tomcat/conf/gcp_spring_poc.json

CMD ["catalina.sh", "run"]

EXPOSE 8080