package fr.uga.miashs.album.service;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

public class SparqlQueryService {
	
	public static final String BASE_URL = "http://www.semanticweb.org/projetAlbum";
	public static final String NS = BASE_URL + "#";
	public static final String NS_PREFIX = "<" + NS + ">";
	public static final String RDFS = "<http://www.w3.org/2000/01/rdf-schema#>";
	public static final String FOAF = "<http://xmlns.com/foaf/0.1/>";
	public static final String DC = "<http://purl.org/dc/elements/1.1/>";
	public static final String SOURCE_TRIPLE_STORE = "http://localhost:3030/ALBUM/update";
	
	public void getAllPictures(){
		String queryString = "SELECT ?p  WHERE {?p a ns:Picture .}";
		getQuery(queryString);
	}
	
	public void getWhatTagPicture(String strURI) {
		String queryString = "SELECT ?p WHERE { <" + strURI + "> ns:what ?p .}";
		System.out.println(queryString);
		getQuery(queryString);
	}

	public void getWhoTagPicture(String strURI) {
		String queryString = "SELECT ?p WHERE { <" + strURI + "> ns:who ?p .}";
		System.out.println(queryString);
		getQuery(queryString);
	}
	
	public void getWhenTagPicture(String strURI) {
		String queryString = "SELECT ?p WHERE { <" + strURI + "> ns:when ?p .}";
		getQuery(queryString);
	}
	
	public void getWhereTagPicture(String strURI) {
		String queryString = "SELECT ?p WHERE { <" + strURI + "> ns:where ?p .}";
		getQuery(queryString);
	}
	
	public void getQuery(String queryString) {
		Query query = QueryFactory.create(
				"PREFIX rdfs: " + RDFS + "\n" +
				"PREFIX foaf: " + FOAF + "\n" +
				"PREFIX ns: " + NS_PREFIX + "\n" +
				"PREFIX dc: " + DC + "\n" +
				queryString);
		
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
		    System.out.println(results);
		  }
	}
	
}
