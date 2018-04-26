# DigitalPreservation_2

Starting the application manually:
mvn clean install
mvn tomcat7:run

Using docker:
docker build -t webapp-example .
docker run -ti --rm -p 8080:8080 webapp-example

Open in browser: http://localhost:8080