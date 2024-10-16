# Stage 1: Build the JAR file
FROM openjdk:21 AS builder
WORKDIR /app
COPY build/libs/api-module-0.0.1-SNAPSHOT.jar app.jar

# Stage 2: Create a runtime image
FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/app.jar app.jar
EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=$STAGE

ENTRYPOINT ["java", "-jar", "app.jar", "-XX:+UseZGC -XX:+ZGenerational", "-Dfile.encoding=UTF-8"]
