package fr.uga.miashs.album.service;

import org.apache.jena.update.*;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;
import fr.uga.miashs.album.model.Picture;

public class SparqlUpdateService extends JpaService<Long, Picture> {
	
	public static final String BASE_URL = "http://www.semanticweb.org/projetAlbum";
	public static final String NS = BASE_URL + "#";
	public static final String NS_PREFIX = "<" + NS + ">";
	public static final String RDFS = "<http://www.w3.org/2000/01/rdf-schema#>";
	public static final String FOAF = "<http://xmlns.com/foaf/0.1/>";
	public static final String DC = "http://purl.org/dc/elements/1.1";
	public static final String SOURCE_TRIPLE_STORE = "http://localhost:3030/ALBUM/update";
	
	public void insertPicture(Picture p){
		
		String queryString = "<" + p.getUri().toString() + ">" + " a ns:Picture .\n";
		System.out.println("insertPic : " + queryString);
		insertData(queryString);
	}
	
	public void insertAlbum(Album a){
		
		String albumURI = "<" + NS + Long.toString(a.getId()) + ">";
		String queryString = albumURI + " a ns:Album .";
		
		System.out.println("insertAlb : " + queryString);
		insertData(queryString);
	}
	
	public void insertPerson(AppUser user){
		
		String userURI = "<" + NS + Long.toString(user.getId()) + ">";
		String queryString = userURI + " a foaf:Person ;\n" +
								" foaf:name " + user.getFirstname() + ".";
		
		System.out.println("insertPerson : " + queryString);
		insertData(queryString);
	}
	
	public void insertData(String insertDataString) {
		
		UpdateRequest request = UpdateFactory.create(
				"PREFIX rdfs: " + RDFS + "\n" +
				"PREFIX foaf: " + FOAF + "\n" +
				"PREFIX ns: " + NS_PREFIX + "\n" +
				"INSERT DATA {\n" +
					insertDataString + "}");
		
		System.out.println("updateRequest");
		UpdateProcessor up = UpdateExecutionFactory.createRemote(request, SOURCE_TRIPLE_STORE );
		up.execute();
	}
}
