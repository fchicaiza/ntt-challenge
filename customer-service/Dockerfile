# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS build
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
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/customer-service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
