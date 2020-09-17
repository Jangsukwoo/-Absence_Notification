FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.7_10-slim as build

WORKDIR /src

COPY ./mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw -B dependency:go-offline

COPY src src
RUN ./mvnw package -DskipTests

FROM adoptopenjdk/openjdk11:x86_64-alpine-jdk-11.0.7_10-slim

RUN mkdir -p /app
WORKDIR /app

COPY --from=build /src/target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "./app.jar"]

EXPOSE 8080
