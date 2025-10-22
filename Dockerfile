FROM openjdk:latest
ADD target/backend-0.0.1-SNAPSHOT.jar tmp/backend-0.0.1-SNAPSHOT.jar
#ADD copies files from your host machine into the container’s filesystem.

ENTRYPOINT ["java", "-jar", "/tmp/backend-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT defines the main command that runs when the container starts.
#change cmd equivalent to  docker commit --change='CMD ["java", "-jar", "/tmp/backend-0.0.1-SNAPSHOT.jar"]' objective_banzai backend_image:v2


#FROM openjdk:17-jre-slim
#WORKDIR /app
#COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"] 