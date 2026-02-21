# 🛒 Product Manager — REST API de Gestion de Produits

**Projet d'apprentissage complet | Spring Boot · Docker · CI/CD**

---

## Présentation

**Product Manager** est un projet **personnel** conçu dans le but d'apprendre et de maîtriser l'ensemble des outils du développement moderne de A à Z. Il s'agit d'une API REST de gestion de produits développée avec **Spring Boot**, pensée comme un terrain d'entraînement complet couvrant : l'architecture en couches, la validation des données, la conteneurisation avec Docker, la mise en place d'un pipeline CI/CD, le monitoring et le déploiement en production sur **Render**.

> 💡 **Ce projet a pour objectif d'appliquer tous les outils du développement complet (DevOps, CI/CD, monitoring, déploiement) sur une base simple et concrète, afin de monter en compétences de manière progressive et structurée.**

---

## Fonctionnalités

- Création, lecture, modification et suppression de produits (CRUD complet)
- Validation des données entrantes
- Gestion des erreurs centralisée
- Pagination et tri des résultats
- Optimisation des requêtes JPA
- Conteneurisation avec Docker
- Pipeline CI/CD automatisé
- Monitoring avec Actuator, Prometheus et Grafana

---

## Stack technique

| Composant | Technologie |
|-----------|-------------|
| Back-end | Java 17, Spring Boot 4.0.3 |
| Build | Maven |
| Base de données | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Validation | Spring Validation |
| Simplification code | Lombok |
| Conteneurisation | Docker |
| CI/CD | GitHub Actions |
| Monitoring | Spring Boot Actuator, Prometheus, Grafana |
| Déploiement | Render |

---

## Prérequis

- Java >= 17
- Maven >= 3.8
- PostgreSQL
- Docker (optionnel)

---

## Installation

### 1. Cloner le dépôt
```bash
git clone https://github.com/valentindongmo/springboot-product-manager.git
cd springboot-product-manager
```

### 2. Configurer la base de données

Créer la base de données PostgreSQL :
```sql
CREATE DATABASE product_manager_db;
```

### 3. Configurer l'environnement

Modifier le fichier `src/main/resources/application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/product_manager_db
spring.datasource.username=postgres
spring.datasource.password=postgres
server.port=8090
spring.jpa.hibernate.ddl-auto=update
```

### 4. Lancer l'application
```bash
mvn spring-boot:run
```

L'API est accessible sur : [http://localhost:8090](http://localhost:8090)

---

## Endpoints API

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/api/products` | Liste tous les produits |
| `GET` | `/api/products/{id}` | Détail d'un produit |
| `POST` | `/api/products` | Créer un produit |
| `PUT` | `/api/products/{id}` | Modifier un produit |
| `DELETE` | `/api/products/{id}` | Supprimer un produit |

---

## Docker
```bash
# Builder l'image
docker build -t product-manager .

# Lancer le conteneur
docker run -p 8090:8090 product-manager
```

---

## CI/CD

Ce projet utilise **GitHub Actions** pour automatiser :
- La compilation et les tests à chaque push
- La construction de l'image Docker
- Le déploiement automatique

---

## Monitoring

Ce projet intègre un système de monitoring complet :
- **Spring Boot Actuator** — exposition des métriques de l'application
- **Prometheus** — collecte et stockage des métriques
- **Grafana** — visualisation des métriques via des dashboards

Accès Actuator : [http://localhost:8090/actuator](http://localhost:8090/actuator)

---

## Auteur

Projet réalisé par **Dongmo Robinson Valentin**  
🐙 [github.com/valentindongmo](https://github.com/valentindongmo)

---

*Projet personnel · Spring Boot · Docker · CI/CD · Monitoring · Render · Java 17*
