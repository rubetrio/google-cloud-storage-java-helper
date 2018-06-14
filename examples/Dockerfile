FROM tomcat:9.0.8-jre8

ADD build/libs/rubetrio-google-cloud-storage-example-0.1.0.war /usr/local/tomcat/webapps/rubetrio.war

ADD tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
ADD context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml
ADD server.xml /usr/local/tomcat/conf/server.xml

CMD ["catalina.sh", "run"]

EXPOSE 8080
