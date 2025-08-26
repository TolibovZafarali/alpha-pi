# -------- build stage (GHCR, not Docker Hub) --------
FROM ghcr.io/adoptium/temurin:21-jdk AS build
WORKDIR /workspace

# Install Maven (so we don't pull maven:... from Docker Hub)
RUN apt-get update && apt-get install -y --no-install-recommends maven \
  && rm -rf /var/lib/apt/lists/*

# Cache deps
COPY backend/pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Build app
COPY backend/src ./src
RUN mvn -q -DskipTests package

# -------- runtime stage (GHCR) --------
FROM ghcr.io/adoptium/temurin:21-jre
WORKDIR /app

# Copy built jar
COPY --from=build /workspace/target/*.jar app.jar

# Railway provides PORT; Spring reads server.port=${PORT:8080}
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
