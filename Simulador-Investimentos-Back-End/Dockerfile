FROM eclipse-temurin:25-jre-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src/ ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:25-jre-alpine
RUN mkdir "/app"
WORKDIR /app
EXPOSE 8080
COPY target/*.jar /app/app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:+UseContainerSupport"
CMD ["java","-jar","/app/app.jar"]