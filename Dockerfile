FROM openjdk:11 as build
COPY target/*SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Xmx400M","-jar","/app.jar"]

