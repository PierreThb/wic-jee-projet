package fr.uga.miashs.album.service;

import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.*;
import org.apache.jena.update.*;

public class SparqlService {

	public static final String SOURCE_ONT = "file:/home/bgallet/rdfschema/ProjetAlbumBGBR.rdf";
	public static final String NS = SOURCE_ONT + "#";
	public static final String RDFS = "<http://www.w3.org/2000/01/rdf-schema#>";
	public static final String FOAF = "http://xmlns.com/foaf/0.1";
	public static final String DC = "http://purl.org/dc/elements/1.1";
	public static final String SOURCE_TRIPLE_STORE = "http://localhost:3030/ALBUM/update";
	public static final String BASE_URI = "<http://www.semanticweb.org/projetAlbum#>" ;
	
	
	public void insertData(String data) throws ServiceException {
		System.out.println("data sparqlService : " + data);
//		UpdateRequest request = UpdateFactory.create(
//				"PREFIX" + RDFS +
//				"PREFIX" + FOAF +
//				"PREFIX" + DC + 
//				"INSERT DATA {"+
//					BASE_URI + "#picture333 a <http://www.semanticweb.org/projetAlbum#Picture>.}");
		
		UpdateRequest request = UpdateFactory.create("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"INSERT DATA {"+
				" <http://imss.univ-grenoble-alpes.fr/ns/album#Selfie> rdfs:subClassOf <http://imss.univ-grenoble-alpes.fr/ns/album#Picture> ."+
				" <http://imss.univ-grenoble-alpes.fr/ns/album#picture3225411> a <http://imss.univ-grenoble-alpes.fr/ns/album#Selfie> .}");

		UpdateProcessor up = UpdateExecutionFactory.createRemote(request, "http://localhost:3030/ALBUM/update");
		up.execute();
	}
	
	public void updateRequest(UpdateRequest request){
		System.out.println("updateRequest");
		UpdateProcessor up = UpdateExecutionFactory.createRemote(request, "http://localhost:3030/ALBUM/update" );
		up.execute();
	}
			
	public void executeQueryString(String queryString){
		Query query = QueryFactory.create("SELECT ?s  WHERE {?s a <http://imss.univ-grenoble-alpes.fr/ns/album#Picture>.}");
		  try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:3030/ALBUM/sparql",query)) {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("s") ;       // Get a result variable by name.
		      Resource r = soln.getResource("p") ; // Get a result variable - must be a resource
		      Literal l = soln.getLiteral("o") ;   // Get a result variable - must be a literal
		      System.out.println(x+" "+r+" "+l);
		    }
		  }

	}
}