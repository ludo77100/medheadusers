# Étape 1 : Utiliser Maven avec OpenJDK 17 d'Eclipse Temurin pour construire l'application
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom.xml et les fichiers sources
COPY pom.xml .
COPY src ./src

# Construire l'application avec Maven
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser une image légère OpenJDK pour exécuter l'application
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copier l'artefact JAR depuis l'étape de build
COPY --from=build /app/target/usersmicroservice-0.0.1-SNAPSHOT.jar /app/usersmicroservice.jar

# Exposer le port 8080 (ou tout autre port utilisé par ton application)
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "/app/usersmicroservice.jar"]