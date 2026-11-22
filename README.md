# Medecine

Application WAR basée sur le mini-framework `aina` (SpringMVC).

## Démarrage

1. Construire d'abord le framework dans `SpringMVC` : `./build-lib.sh --install`.
2. Construire l'application : `mvn clean package`.
3. Déployer `target/medecine-1.0-SNAPSHOT.war` dans Tomcat.

La route `GET /health` confirme que le routage fonctionne.

Ou, depuis le dossier du projet :

```bash
./deploy.sh
```

Le script utilise `/home/aina/tomcat` par défaut. Pour un autre Tomcat :

```bash
./deploy.sh /chemin/vers/tomcat
```

## PostgreSQL et JPA

Le projet utilise Spring Data JPA, Hibernate et le pilote PostgreSQL. Copier
`src/main/resources/database.properties.example` vers
`src/main/resources/database.properties`, puis renseigner l'URL, l'utilisateur
et le mot de passe de PostgreSQL. Ce dernier fichier est ignoré par Git pour ne
pas exposer les identifiants.

`Patient` est une entité JPA et `PatientRepository` étend `JpaRepository`.
Le contrôleur récupère ce repository Spring par `ApplicationContext` :

```java
PatientRepository repo = context.getBean(PatientRepository.class);
repo.save(patient);
```

En développement, Hibernate crée ou met à jour la table `patient` avec
`hibernate.hbm2ddl.auto=update`. Il faudra utiliser des migrations et le mode
`validate` en production.
