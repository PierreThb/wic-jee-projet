# Projet Album "Gràfico" JavaEE & Web Sémantique 

- Doc : [http://imss-www.upmf-grenoble.fr/~davidjer/javaee/]('http://imss-www.upmf-grenoble.fr/~davidjer/javaee/')
- Install : [http://tomee.apache.org/tomee-and-eclipse.html]('http://tomee.apache.org/tomee-and-eclipse.html')
- WS-Subject : [http://imss-www.upmf-grenoble.fr/~atenciam/WS/Projet/projet.pdf]('http://imss-www.upmf-grenoble.fr/~atenciam/WS/Projet/projet.pdf')
- Icons : https://linearicons.com/free
- Framework CSS : http://materializecss.com/
- JSF Cheatsheet : https://www.tutorialspoint.com/jsf/jsf_basic_tags.htm
- Upload File dropzone + JSF : http://stackoverflow.com/questions/38018632/use-dropzone-with-jsf

## Fonctionnalitées JavaEE

- CRUD de différents Modeles 
	 Picture
	 AppUser
	 Album
	 
- Authentification et Redirection sur la page de login si l'utilisateur n'est pas connecté
- Il est possible de partager un album avec un autre utilisateur

- Toutes les données sont persistées en BDD (Derby)

## Web Sémantique (JEE)

Nous avons défini une ontologie `/WebContent/resources/ontology/ProjetAlbumBGBR.rdf` qui va nous servir de base afin d'effectuer des tests et des requètes 


- Lorsque chaque photo est ajouté son Uri est crée dans le triple Store
- Lorsqu'un utilisateur est ajouté, nous définissons dans le triple Store une ressource de type foaf:Person

- Il est possible d'ajouter des Tags à une photo : 
	- "propriete:who" défini qui est sur la photo (Par exemple roger notre Licorne)
	- "propriete:what" défini le Sujet de la photo 
	- "propriete:where" est du type "literal" et 
	- "dc:date" correspond à la date de la photo (Doit être du format YYYY-MM-DD) 

- Un Moteur de recherche Static est implémenté, dont vous trouverez la liste `/ProjetAlbumData/list-query.sql`

Pour pouvoir tester directement le moteur de recherche, un jeu de données est mis à votre disposition (cf. Jeu de données) 

## Install & Run project : 

### Configure eclipse & Derby Embedded Database
* Download [eclipse JEE](http://www.eclipse.org/downloads/packages/) 
* Download [Apache Tomcat JEE jaxrs-1.7.4 2](https://tomee.apache.org/download/archives.html)
* Download [derby and myfaces JAR files](http://imss-www.upmf-grenoble.fr/~davidjer/javaee/)
* Unzip Eclipse and TomEE
* Copy downloaded JARs in `[TomEE_DIR/lib]`
* Create new Project > Dynamique Web Project (Windows : run Eclipse as admin)
	* Clone your existing project in this new project
* Create new Eclipse runtime server : 
	* New > Server
	* Tomcat v7.0 Server
	* Server runtime environment > add
	* Tomcat installation directory : path to the previously downloaded and dezipped tomcat directory	
* Configure web.xml :
	* Change the `directory` parameter to where you want your album to be upload (make sure you have the rights on this directory)
* Configure `src/META-INF/persistence.xml` to match your database
```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://java.sun.com/xml/ns/persistence" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
	<persistence-unit name="EssaiJPA" transaction-type="RESOURCE_LOCAL">
		<non-jta-data-source>albumDS</non-jta-data-source>
		<class>fr.uga.miashs.album.model.Album</class>
		<class>fr.uga.miashs.album.model.AppUser</class>
		<class>fr.uga.miashs.album.model.Picture</class>
	<properties>
		<property name="openjpa.jdbc.Schema" value="DATABASE_NAME"/>
		<property name='openjpa.jdbc.SynchronizeMappings' value='buildSchema(ForeignKeys=true)' />
	</properties>
	</persistence-unit>
</persistence>
```

* Right click on you server in Eclipse > import > General > file System > browse to `[TomEE_DIR/conf]` > import `logging.properties`,`system.properties`, `tomee.xml` :
* Configure `tomee.xml` to match your database (make sure you have the rights on the db directory)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<tomee>
	<Resource id="albumDS" type="DataSource">
		JdbcDriver org.apache.derby.jdbc.EmbeddedDriver
		JdbcUrl jdbc:derby:PATH/TO/DB;create=true
		JtaManaged=false
	</Resource>
</tomee>
```
### Fuseki Triple Store & Apache Jena
* Download [Apache Jena](https://jena.apache.org/download/index.cgi) 
	* Add Jena librairies to Tomcat Server lib directory
	* Create new UserLibrairies in Eclipse (JenaLibs) and add them to the projet build-path
* Download [Apache Jena Fuseki](https://jena.apache.org/download/index.cgi)
	* Download [fuseki dataset configuration](http://imss-www.upmf-grenoble.fr/~atenciam/WS/Projet/fusekiDatasetConfig.ttl)
	* add configuration file in run/configuration/
	* run server : "./fuseki"

* Unzip each 
* 
* 

## Jeu de données pour éxecuter les requêtes

Il est possible de récupérer les données suivantes afin de pouvoir tester les requêtes (Search Images)
`/ProjetAlbumData`
* Un export du triple-store fuseki : 
	EXPORT-FUSEKI-WITH-DATA.nq
* La BDD Derby : 
	/db
* Les images uploadés : 
	/uploads
* La liste des requetes : 
	list-query.sql 

