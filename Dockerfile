# Use Java + Maven image
FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

# Copy everything
COPY . .

# Build project
RUN mvn clean package -DskipTests

# Run app
CMD ["java", "-jar", "target/project-0.0.1-SNAPSHOT.jar"]