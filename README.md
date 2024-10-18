# Medhead Users Microservice

## Description
Ce projet fait partie de la suite Medhead et représente un microservice pour la gestion des utilisateurs.
Le microservice fournit des fonctionnalités pour gérer les utilisateurs et les rôles associés ainsi que la sécurisation des endpoints.

## Fonctionnalités
- Gestion des utilisateurs
- Distribution token JWT
- Sécurisation des endpoints avec différents rôles.
- Intégration de tests unitaires et d'intégration avec couverture Jacoco.

## Prérequis
- **Java 17** : Le projet utilise la version 17 de Java.
- **Maven** : Pour la gestion de dépendances et le processus de build.
- **Docker** : Pour la construction et le déploiement de l'image Docker.
- **PostgreSQL** : Base de données utilisée pour stocker les informations.
- **SonarCloud** : Utilisé pour l’analyse de la qualité du code (facultatif).

## Installation et Configuration

### 1. Clonez le projet
Clonez le dépôt Git sur votre machine locale :

### 2.Configuration de la base de données

Modifiez les informations de connexion dans le fichier application.properties pour correspondre à votre instance de PostgreSQL.

### 3. Configuration du fichier application.properties

Ajoutez les configurations nécessaires pour les éléments suivants :

Informations de connexion à PostgreSQL.
Configuration de sécurité (par exemple, JWT pour l’authentification).

### 4. Lancer le service en local

Pour lancer l’application en local sans Docker :

```console
mvn spring-boot:run
```

## Pipeline CI/CD avec Jenkins

Ce projet est configuré pour fonctionner avec une pipeline Jenkins pour :

    1. Exécuter les tests unitaires et d’intégration.
    2. Analyser la couverture du code avec Jacoco.
    3. Effectuer une analyse de code avec SonarCloud.
    4. Construire et pousser une image Docker sur Docker Hub.

## Tests

Les tests sont divisés en deux catégories :

    1. Tests Unitaires : Pour valider le fonctionnement des unités de code.
    2. Tests d’Intégration : Pour vérifier les interactions entre les différentes parties de l’application.

Pour exécuter tous les tests :

```console
mvn test
```
Pour générer le rapport de couverture Jacoco :

```console
mvn verify
```