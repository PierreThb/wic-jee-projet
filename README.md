# Projet Album "Gràfico" JavaEE & Web Sémantique 

- Doc : [http://imss-www.upmf-grenoble.fr/~davidjer/javaee/]('http://imss-www.upmf-grenoble.fr/~davidjer/javaee/')
- Install : [http://tomee.apache.org/tomee-and-eclipse.html]('http://tomee.apache.org/tomee-and-eclipse.html')
- WS-Subject : [http://imss-www.upmf-grenoble.fr/~atenciam/WS/Projet/projet.pdf]('http://imss-www.upmf-grenoble.fr/~atenciam/WS/Projet/projet.pdf')
- Icons : https://linearicons.com/free
- Framework CSS : http://materializecss.com/
- JSF Cheatsheet : https://www.tutorialspoint.com/jsf/jsf_basic_tags.htm
- Upload File dropzone + JSF : http://stackoverflow.com/questions/38018632/use-dropzone-with-jsf

## Java EE
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
## Configurations

web.xml PC portable Benoit :
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
  <display-name>ProjetAlbum</display-name>
  <servlet>
    <servlet-name>Faces Servlet</servlet-name>
    <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>Faces Servlet</servlet-name>
    <url-pattern>*.xhtml</url-pattern>
  </servlet-mapping>
  <context-param>
    <param-name>javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL</param-name>
    <param-value>true</param-value>
  </context-param>
  <context-param>
    <param-name>javax.faces.PROJECT_STAGE</param-name>
    <param-value>Development</param-value>
  </context-param>
  <context-param>
    <param-name>directory</param-name>
    <param-value>C:\\Users\\ben\\Documents\\Dev\\Fac\\utils\\uploads</param-value>
  </context-param>
</web-app>
```


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
