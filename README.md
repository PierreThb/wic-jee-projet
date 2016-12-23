# Projet Album JEE gestion [Rendu 09/01/2017]

-Doc : [http://imss-www.upmf-grenoble.fr/~davidjer/javaee/]('http://imss-www.upmf-grenoble.fr/~davidjer/javaee/')
-Install : [http://tomee.apache.org/tomee-and-eclipse.html]('http://tomee.apache.org/tomee-and-eclipse.html')
-Icons : https://linearicons.com/free
-Framework CSS : http://materializecss.com/
-JSF Cheatsheet : https://www.tutorialspoint.com/jsf/jsf_basic_tags.htm

Technos : 
* JSF pour les vues
* JPA pour le mapping relationnel de données (~Doctrine Symfony) 
* REST pour la navigation | JAXRS pour la négociation de contenu


- Applications avec Utilisateurs : 
	- Authentification (Mail | mdp)
	
	Tout le monde : 
		- créer un compte
		
	Admin :
		- lister les comptes
		- supprimer un compte (tout supprimer) 

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

## Run & Install project (Derby embedded) : 
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

## Run & Install project (MySQL 4.x) : 

* Download [eclipse JEE Kepler RS2](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/keplerr) 
* Download [Apache Tomcat JEE jaxrs-1.7.4 2](https://tomee.apache.org/downloads.html)
* Download [MySQL JDBC Driver](https://dev.mysql.com/downloads/connector/j/)
* Copy `mysql-connector-java-5.1.40-bin.jar` to `[TomEE_install_dir]/lib`
* Create new Project > Dynamique Web Project
	* Clone your existing project in this new project
* Create new Eclipse runtime server : 
	* New > Server
	* Tomcat v7.0 Server
	* Server runtime environment > add
	* Tomcat installation directory : path to the previously downloaded and dezipped tomcat directory

* Configure web.xml :
	* Change the `directory` parameter to where you want your album to be upload
	* And add the following if not already there :
```xml 
<resource-env-ref>
    <resource-env-ref-name>jdbc/DATABASE_NAME</resource-env-ref-name>
    <resource-env-ref-type>javax.sql.DataSource</resource-env-ref-type>
</resource-env-ref>
``` 

* Configure `WebContent/META-INF/context.xml` to connect with your database (MySQL)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource
        name="jdbc/DATABASE_NAME" type="javax.sql.DataSource"
        maxActive="100" maxIdle="30" maxWait="10000" 
        url="jdbc:mysql://localhost:3306/DATABASE_NAME"
        driverClassName="com.mysql.jdbc.Driver"
        username="root" password=""
    />
</Context>
```

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
### Known issues quick fix :	
* If the project is not runing, check for unbound librairies: Right Click on Project > BuildPath > ConfigureBuildPath and add unbound librairies
* If the MySQL table creation fail, check your version of MySQL and OpenJPA (http://stackoverflow.com/a/6597724)


## Triple Store Installation 

* Apache Jena Fuseki [2.4.1](https://jena.apache.org/download/) 
* Installer les packages apache jena pour eclipse 

## Documentation de déploiement 

## Info méthode update data 

```
UpdateExecutionFactory
UpdateRequest request = 
	UpdateFactory create(
	"PREFIX dc:<http://purl.org/dc/elements/1.1>" +
	" INSERT DATA {" + 
	" <http:///ex/boo> dc:title "anirset"; " + 
	"dc:creator /"authoeur" 


UpdateProcessor up= UpdateExecutionFactory.createRemote(request, "http://localhost:3030");
up.execute();
```
