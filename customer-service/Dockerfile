# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copy parent pom
COPY pom.xml .
# Copy child poms
COPY customer-service/pom.xml customer-service/
COPY account-service/pom.xml account-service/
# Resolve dependencies
RUN mvn dependency:go-offline -pl customer-service -am
# Copy source
COPY customer-service/src customer-service/src
# Build
RUN mvn clean package -DskipTests -pl customer-service -am

# Run stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/customer-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
