# Use an official Maven image to build the project
FROM maven:3.9.0-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a minimal JDK image for runtime
FROM openjdk:17-jdk-slim AS runtime

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/jenkins-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
