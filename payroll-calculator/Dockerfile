FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /work
COPY --from=build /app/target/*-runner.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
