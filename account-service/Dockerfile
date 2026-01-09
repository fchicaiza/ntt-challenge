# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copy parent pom
COPY pom.xml .
# Copy child poms
COPY customer-service/pom.xml customer-service/
COPY account-service/pom.xml account-service/
# Resolve dependencies
RUN mvn dependency:go-offline -pl account-service -am
# Copy source
COPY account-service/src account-service/src
COPY account-service/src/main/resources/openapi.yaml account-service/src/main/resources/openapi.yaml
# Build
RUN mvn clean package -DskipTests -pl account-service -am

# Run stage
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/account-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
