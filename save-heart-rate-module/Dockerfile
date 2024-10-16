# Stage 1: Build the JAR file
FROM openjdk:21 AS builder
WORKDIR /app
COPY build/libs/save-heart-rate-module-0.0.1-SNAPSHOT.jar app.jar

# Stage 2: Create a runtime image
FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/app.jar app.jar
EXPOSE 8081

ENV SPRING_PROFILES_ACTIVE=$STAGE

ENTRYPOINT ["java", "-jar", "app.jar", "-Dfile.encoding=UTF-8"]
