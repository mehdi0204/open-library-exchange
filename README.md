# Open Library Exchange — BookSwap API

API REST de gestion de bibliothèques personnelles et d'échanges de livres entre utilisateurs.

## Stack Technique

- **Framework** : Quarkus 3.32.4
- **Langage** : Java 21
- **Base de données** : PostgreSQL 16
- **Authentification** : JWT (SmallRye JWT)
- **ORM** : Hibernate ORM with Panache
- **Tests** : JUnit 5 + REST Assured + JaCoCo

## Lancement rapide

```bash
docker compose up
```

API : http://localhost:8080
Swagger UI : http://localhost:8080/q/swagger-ui

## Lancement en développement

```bash
# 1. Générer les clés JWT (une seule fois)
java GenerateKeys.java

# 2. Démarrer PostgreSQL
docker compose up postgres

# 3. Lancer l'app
mvn quarkus:dev
```

## Comptes de test

| Username | Mot de passe | Rôle |
|----------|--------------|------|
| Otman | Password1! | USER |
| aminata | Password1! | USER |
| admin | Admin1234! | ADMIN |

## Tests

```bash
mvn test
```

Rapport JaCoCo : `target/site/jacoco/index.html`

## Architecture

```
HTTP → Resource (JAX-RS) → Service → Repository (Panache) → DB

fr.bookswap/
├── auth/       → Authentification & JWT
├── book/       → Catalogue livres & auteurs
├── library/    → Bibliothèques personnelles
├── exchange/   → Échanges & prêts
├── review/     → Avis & notes
├── admin/      → Administration
├── entity/     → Entités JPA
├── security/   → JwtService
└── exception/  → Gestion des erreurs
```
