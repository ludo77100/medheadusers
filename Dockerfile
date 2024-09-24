# Étape 1 : Utiliser OpenJDK 17 pour construire l'application
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom.xml et les fichiers sources
COPY pom.xml .
COPY src ./src

# Construire l'application avec Maven
RUN mvn clean package -DskipTests

# Copier le fichier .jar depuis l'étape précédente
COPY --from=build /app/target/usersmicroservice-0.0.1-SNAPSHOT.jar /app/usersmicroservice-0.0.1-SNAPSHOT.jar

# Indiquer le fichier final à stocker dans l'image Docker
CMD ["echo", "Docker image ready to be pushed"]