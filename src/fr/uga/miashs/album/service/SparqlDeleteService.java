package fr.uga.miashs.album.service;


import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;
import fr.uga.miashs.album.model.Picture;

public class SparqlDeleteService {
	
	public static final String BASE_URL = "http://www.semanticweb.org/projetAlbum";
	public static final String NS = BASE_URL + "#";
	public static final String NS_PREFIX = "<" + NS + ">";
	public static final String RDFS = "<http://www.w3.org/2000/01/rdf-schema#>";
	public static final String FOAF = "<http://xmlns.com/foaf/0.1/>";
	public static final String DC = "<http://purl.org/dc/elements/1.1/>";
	public static final String SOURCE_TRIPLE_STORE = "http://localhost:3030/ALBUM/update";
	public static final String XSD = "<http://www.w3.org/2001/XMLSchema#>";
	
	public void deleteWhatProperty(String pictureURI, String what){	
		
		String whatURI = "<" + NS + what + ">"; //we assume that it exist
		String queryString = "<" + pictureURI + "> ns:what " + whatURI + " .";
		System.out.println("What : " + queryString);
		deleteData(queryString);
	}
	
	public void deleteWhereProperty(String pictureURI, String where){	
		
		String whereURI = "\"" + where + "\""; 
		String queryString = "<" + pictureURI + "> ns:where " + whereURI + " .";
		System.out.println("Where : " + queryString);
		deleteData(queryString);
	}

	public void deleteWhoProperty(String pictureURI, String who){	
		
		String whoURI = "<" + NS + who + ">";
		String queryString = "<" + pictureURI + "> ns:who " + whoURI + " .\n" +
								whoURI + " ns:isIn <" + pictureURI + ">";
		System.out.println("Who : " + queryString);
		deleteData(queryString);
	}
	
	public void deleteWhenProperty(String pictureURI, String when){

		String queryString = "<" + pictureURI + "> dc:date \"" + when + "\" .";
		System.out.println("When : " + queryString);
		deleteData(queryString);
	}
	
	public void deletePicture(Picture p){
		String queryString = "<" + p.getUri().toString() + ">" + " ?a ?b .";
		deleteWhere(queryString);
	}
	
	public void deleteAlbum(Album a){
		String queryString = "<" + NS + Long.toString(a.getId()) + "> ?a ?b .";
		deleteWhere(queryString);
	}
	
	public void deletePerson(AppUser user){
		String queryString = "<" + NS + Long.toString(user.getId()) + "> ?a  ?b .";
		deleteWhere(queryString);	
	}
	
	public void deleteData(String deleteDataString) {
		
		UpdateRequest request = UpdateFactory.create(
				"PREFIX rdfs: " + RDFS + "\n" +
				"PREFIX foaf: " + FOAF + "\n" +
				"PREFIX ns: " + NS_PREFIX + "\n" +
				"PREFIX dc: " + DC + "\n" +
				"PREFIX xsd: " + XSD + "\n" +
				"DELETE DATA {\n" +
					deleteDataString + "}");
		
		UpdateProcessor up = UpdateExecutionFactory.createRemote(request, SOURCE_TRIPLE_STORE );
		up.execute();
	}
	
	public void deleteWhere(String dataString){
		UpdateRequest request = UpdateFactory.create(
				"PREFIX rdfs: " + RDFS + "\n" +
				"PREFIX foaf: " + FOAF + "\n" +
				"PREFIX ns: " + NS_PREFIX + "\n" +
				"PREFIX dc: " + DC + "\n" +
				"PREFIX xsd: " + XSD + "\n" +
				"DELETE WHERE {\n" +
					dataString + "}");
		
		UpdateProcessor up = UpdateExecutionFactory.createRemote(request, SOURCE_TRIPLE_STORE );
		up.execute();
	}
}
