# Projet Album JEE gestion [Rendu 09/01/2017]

Doc : [http://imss-www.upmf-grenoble.fr/~davidjer/javaee/]('http://imss-www.upmf-grenoble.fr/~davidjer/javaee/')
Install : [http://tomee.apache.org/tomee-and-eclipse.html]('http://tomee.apache.org/tomee-and-eclipse.html')

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



## Run & Install project on eclipse : 

* Download eclipse JEE : Kepler ? 
* Server Apache Tomcat JEE jaxrs-1.7.4 2
* Configure the DB path in  Server / Apachetomcat7.0/tomee.xml : 
	```<Resource id="albumDS" type="DataSource">
		JdbcDriver org.apache.derby.jdbc.EmbeddedDriver
		JdbcUrl jdbc:derby:/home/wicm2/rosparsb/wic-jee-tps/AlbumDB;create=true
		JtaManaged=false
	</Resource>```
* If the project is not Working : Right Click on Project > BuildPath > ConfigureBuildPath
