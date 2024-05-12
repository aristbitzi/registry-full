FROM tomcat:8.5.43-jdk8
ADD ./registry-rest/target/registry-rest.war /usr/local/tomcat/webapps
ADD ./tomcat-users.xml /usr/local/tomcat/conf