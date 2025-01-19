
# SCALIX

Lilian FERRETTI, Elias MORIO

Utilisation IA : Oui

## Conception

### Appel API

La classe TMDBClient se concentre sur la récupération des données de l'API themoviedb.org. Elle se compose d'une méthode 
pour chaque requête utilisée dans l'application. Elle utilise un système de cache au niveau fichier pour éviter
de requéter l'API à chaque fois et gagner en temps d'exécution.

### Gestion du cache

L'interface `Cache` définit les méthodes génériques pour stocker et récupérer des données en cache. Deux implémentations principales de cette interface sont fournies :

- **`FileCache`** : Cette implémentation sauvegarde les données dans des fichiers JSON, stockés dans le répertoire défini 
- par la propriété `cache.base-dir` du fichier de configuration (`resources/application.properties`). Elle est utile pour 
- un stockage persistant entre les exécutions du programme.
- **`MemoryCache`** : Cette implémentation utilise une `Map` en mémoire pour stocker temporairement les données. Elle 
- est rapide mais limitée à la durée de vie du processus.

Pour faciliter l'utilisation du cache, nous avons créé une classe `CacheManager`. Celle-ci prend en paramètre une 
instance de `Cache` et simplifie les interactions en permettant de vérifier la présence des données dans le cache avant 
d'exécuter une logique donnée. Grâce à l'utilisation idiomatique de Scala, `CacheManager` tire parti de la possibilité 
de passer des fonctions en paramètre, rendant le code plus concis et élégant.

### Méthodes

Les méthodes `findActorId`, `findActorMovies`, `findMovieDirector` et `collaboration` sont définies dans la classe `ScalixService`.  
Chaque méthode implémente un cache au niveau mémoire en utilisant la classe `CacheManager`. Cela permet de stocker les retours de fonction en mémoire pour éviter de refaire des requêtes inutiles à l'API.

- **`findActorId`** : Recherche l'identifiant d'un acteur à partir de son prénom et de son nom, avec un cache pour optimiser les recherches répétées.
- **`findActorMovies`** : Récupère la liste des films d'un acteur donné via son identifiant, stockée en cache pour une future réutilisation.
- **`findMovieDirector`** : Retourne le réalisateur d'un film donné via son identifiant, avec gestion des résultats via un cache.
- **`collaboration`** : Identifie les collaborations entre deux acteurs, en retournant les films et réalisateurs communs. Cette méthode s'appuie sur les résultats des autres méthodes tout en exploitant les caches associés.

Ces implémentations mettent en avant une utilisation efficace des données en mémoire et réduisent le nombre de requêtes HTTP, garantissant ainsi des performances améliorées.

### Lancer l'application

Il faut renseigner la clé d'API dans le fichier `resources/application.properties` pour pouvoir utiliser l'API de 
themoviedb.org puis lancer la classe `ScalixApp` pour démarrer l'application.
