# 🅿️3️⃣ Développez le back-end en utilisant Java et Spring

## 💁🏻‍♀️ Résumé du projet ChâTop

### 👉🏻 Contexte:

Vous venez de rejoindre l'équipe de ChâTop, une entreprise spécialisée dans la location saisonnière sur la côte basque, et vous êtes chargé de développer le back-end de leur nouveau portail de mise en relation entre locataires et propriétaires.

### 💁🏻‍♀️ Mission:

Implémenter le back-end de l'application en Java avec Spring, en suivant les spécifications techniques fournies dans le repository GitHub existant.
Assurer l'authentification des utilisateurs (locataires et propriétaires) pour accéder aux annonces de location.
Documenter l'API avec Swagger pour une meilleure compréhension et utilisation.
Respecter les bonnes pratiques de développement en utilisant des entités, des repositories, des DTO, des controllers et des services.
Configurer la sécurité avec Spring Boot Security et JWT.
### 👉🏻 Ressources:

Repository GitHub contenant l'environnement Mockoon, une collection Postman pour tester l'API et un schéma de base de données.
### 👉🏻 Délivrables:

Code du back-end implémenté en Java avec Spring.
Documentation de l'API avec Swagger.
README expliquant le lancement du projet et les fonctionnalités disponibles.
### 👉🏻 Réunion avec Marco:

Présentation du travail réalisé et des fonctionnalités implémentées.
Validation de la qualité du code et des pratiques de développement.
### 👉🏻Points clés:

Respecter les spécifications techniques fournies.
Assurer une documentation complète et claire de l'API avec Swagger.
Implémenter les fonctionnalités d'authentification et de gestion des annonces de location.
Maintenir un code propre et commenté pour faciliter la compréhension et la maintenance.
Dupliquer et partager le repository GitHub avec l'équipe pour la collaboration.
Suivre les bonnes pratiques de développement avec Spring Boot.

## Architecture du projet ChâTop

### 👉🏻 Ce projet est un back-end Spring structuré et organisé pour une meilleure lisibilité et maintenabilité. Voici un aperçu de sa structure :

#### 👉🏻 Racine du projet :

- src/: Le répertoire source contenant le code Java du projet.
- pom.xml: Fichier de configuration Maven décrivant les dépendances du projet et sa structure.

#### Dossier src/main/java:

Ce dossier contient le code Java de l'application, organisé selon la convention de structure de projet de Spring Boot.

- com/chatop: Package racine de l'application.
  - config: Contient les classes de configuration Spring (exemples : SecurityConfig.java, SwaggerConfig.java).
  - controller: Contient les contrôleurs de l'API REST (exemples : AuthController.java, RentalController.java).
  - dto: Contient les objets de transfert de données (DTO) utilisés pour les requêtes et les réponses de l'API.
  - entity: Contient les entités JPA représentant les données persistantes dans la base de données.
  - repository: Contient les interfaces de repository pour interagir avec la base de données (exemples : UserRepository.java, RentalRepository.java).
  - service: Contient les services métier de l'application (exemples : AuthService.java, RentalService.java).
  - util: Contient les classes utilitaires de l'application.

#### Dossier src/main/resources:

Ce dossier contient les fichiers de configuration de l'application, tels que les propriétés de base de données, les fichiers de logs, etc.

- application.properties: Fichier de configuration principal de l'application Spring Boot.
- database.properties: Fichier de configuration de la base de données, à créer pour spécifier les informations de connexion (exemple fourni dans la section Lancement).

## Installation et Exécution

### 👉🏻 Prérequis:

Assurez-vous d'avoir Java et Maven installés sur votre système. Vous pouvez les télécharger et les installer depuis leurs sites web officiels :

- Java: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
- Maven: https://maven.apache.org/download.cgi

### 👉🏻 Lancement du projet:

## Pour lancer le projet, suivez ces étapes :

1. Clonez le dépôt du projet dans votre répertoire de travail local.
2. Créez un fichier `database.properties` dans le dossier `src/main/resources` et y mettez les informations suivantes :

```
spring.datasource.url=jdbc:mysql//PORT URL DE LA BASE / NOM DE LA BASE
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD DE LA BASE
server.port=PORT 3001 par defaut pour correspondre à angular
security.jwt.secret-key=UN JETON CLE JWT
# 24h in millisecond
security.jwt.expiration-time=UN TEMPS D'EXPIRATION DU TOKEN
```

## Ouvrez un terminal dans le répertoire du projet cloné.

Exécutez la commande suivante pour lancer l'application :

```
mvn spring-boot:run
```

Une fois le serveur démarré, accédez à l'API à l'adresse suivante :

```
http://localhost:3001/
```

### 👉🏻 Test des routes:

Utilisez Postman ou Angular pour tester les routes de l'API.

### 👉🏻 Documentation de l'API:

La documentation de l'API est disponible via Swagger à l'adresse suivante :

```
http://localhost:3001/swagger-ui/index.html#/
```

### 👉🏻 Bonnes pratiques de développement:

Ce projet back-end a été développé en suivant les bonnes pratiques de développement avec Spring Boot :

Utilisation de modèles d'entités, de repositories, de DTO, de controllers et de services.
Configuration de la sécurité avec Spring Boot Security et JWT.
Commentaires explicatifs dans le code pour une meilleure compréhension.
Maintenance d'un code propre et bien structuré pour une facilité de lecture et de maintenance.
Routes disponibles


### 👉🏻 Voici un tableau des routes disponibles dans l'API correspondant aux routes fournies dans Postman et Mockoon :

| Méthode | Route                                   | Description                                      |
|---------|-----------------------------------------|--------------------------------------------------|
| POST    | localhost:3001/api/auth/register       | Enregistrement d'un nouvel utilisateur           |
| POST    | localhost:3001/api/auth/login          | Authentification de l'utilisateur                |
| GET     | localhost:3001/api/auth/me             | Récupération des informations de l'utilisateur  |
| GET     | localhost:3001/api/rentals             | Récupération de toutes les annonces de location |
| GET     | localhost:3001/api/rentals/1           | Récupération d'une annonce de location par ID   |
| POST    | localhost:3001/api/rentals/            | Création d'une nouvelle annonce de location      |
| PUT     | localhost:3001/api/rentals/1           | Mise à jour d'une annonce de location par ID     |
| POST    | localhost:3001/api/messages/           | Envoi d'un message                               |
| GET     | localhost:3001/api/user/1              | Récupération des informations d'un utilisateur   |

