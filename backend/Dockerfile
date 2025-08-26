# ---------- build stage ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache deps first
COPY backend/pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

# Build
COPY backend/src ./src
RUN mvn -q -DskipTests package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar (adjust if your artifactId/version change)
COPY --from=build /workspace/target/*.jar app.jar

# Railway provides PORT; Spring reads server.port=${PORT:8080}
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
