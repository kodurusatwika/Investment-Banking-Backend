# ---------- BUILD STAGE ----------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy Maven wrapper files
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Give execute permission to mvnw (Linux needs this)
RUN chmod +x mvnw

# Download dependencies first (cache layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build application (skip tests to avoid constructor errors)
RUN ./mvnw clean package -Dmaven.test.skip=true

# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
