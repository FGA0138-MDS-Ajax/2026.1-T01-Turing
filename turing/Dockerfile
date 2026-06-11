FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml  .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B


FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080 

COPY --from=build turing/target/turing-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

