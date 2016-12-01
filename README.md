# Projet Album JEE gestion [Rendu 09/01/2017]

* BDD classique (ici Derby)

Technos : 
* JSF pour les vues
* JPA pour le mapping relationnel de données (~Doctrine Symfony) 
* REST pour la navigation | JAXRS pour la négociation de contenu


- Applications avec Utilisateurs : 
	- Authentification (Mail | mdp)

- Création d'albums : 
	- Photos (CRUD)
	- Possibilité de partage (avec d'autres utilisateurs)

## Web Sémantique (JEE)

* Triple Store : Base de Données RDF 

- Anotation sur une photo : 
	- Personne(s) présente(s)
	- Quoi ? Sujet
	- Situation (Ou ?) (Tag Web Sémantique)
	- Quand ? (date | évènement (ex : coupe Icare)

Ex : photo {URI} avec la propriété {Qui} plus valeur {Mister Bean}
Par exemple faire des groupes de personnes, propriété {Ami}

Quoi :  Ressources DBPedia 
Quand : Ressources dates/évènements 

## Requètes de recherche (SPARQL) 

Rechercher les photos de la région rhones-alpes qui contient des montagnes 
- ex : La croix de chamrousse, objet DBPedia, fait partie de RhonesAlpes 

