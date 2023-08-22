# Use a base image with Java support, such as OpenJDK
FROM openjdk:11-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file to the container
COPY target/visionrent-1.0.0.jar /app.jar

# Command to run the Spring Boot application
CMD ["java", "-jar", "/app.jar"]


#docker build -t sametyaprak/school-management:0.0.1-SNAPSHOT .
#docker run sametyaprak/school-management:0.0.1-SNAPSHOT