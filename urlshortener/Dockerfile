# Start from a base JDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built JAR from host to container
COPY target/urlshortener-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]