FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -pl frontend-service -am

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/frontend-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
