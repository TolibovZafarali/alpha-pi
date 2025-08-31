# ---- Build stage
FROM maven:3.9-amazoncorretto-21-alpine AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

# ---- Run stage
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=${PORT}","-jar","/app/app.jar"]
