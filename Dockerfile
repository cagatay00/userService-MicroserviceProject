# Step 1: Use an official Maven + Java image for building
FROM maven:3.9.2-eclipse-temurin-17 as build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn package -DskipTests

# Step 2: Use a smaller JRE image to run the final jar
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/userservice-0.0.1-SNAPSHOT.jar userservice.jar

# Expose port
EXPOSE 8081

# Start the service
ENTRYPOINT ["java", "-jar", "userservice.jar"]
