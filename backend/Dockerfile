FROM eclipse-temurin:17-jdk

WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy everything
COPY . .

# Go into Spring Boot folder
WORKDIR /app/backend

# Build the jar
RUN mvn clean package -DskipTests

EXPOSE 8080

# Run Spring Boot
CMD ["java", "-jar", "target/*.jar"]
