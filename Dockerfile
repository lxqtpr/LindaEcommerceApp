FROM openjdk:17
EXPOSE 8080
ADD /build/libs/linda-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]
