FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline
COPY src/ ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:25-jre-jammy
WORKDIR /app
EXPOSE 8082
COPY --from=build /app/target/*.jar /app/app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:+UseContainerSupport"
CMD ["java","-jar","/app/app.jar"]