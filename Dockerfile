FROM java:8

# Install maven
RUN apt-get -y update && apt-get install -y maven


# Prepare by downloading dependencies
ADD pom.xml /pom.xml

# Adding source, compile and package into a fat jar
ADD src /src
RUN ["mvn", "package"]

EXPOSE 8080
CMD ["mvn", "tomcat7:run"]