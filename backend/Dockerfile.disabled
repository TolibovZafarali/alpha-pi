# ---- build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src
RUN mvn -f backend/pom.xml -DskipTests package

# ---- run stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV PORT=8080
COPY --from=build /src/backend/target/*.jar app.jar
EXPOSE 8080
CMD ["sh","-c","java -jar app.jar"]
