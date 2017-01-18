package fr.uga.miashs.album.service;

import java.util.ArrayList;
import java.util.List;

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
	public static final String XSD = "<http://www.w3.org/2001/XMLSchema#>"; 
	
	public List<String> getAllPictures(){
		String queryString = "SELECT ?p  WHERE {?p a ns:Picture .}";
		System.out.println(queryString);
		return getQuery(queryString);
	}
	
	public List<String> getWhatTagPicture(String strURI) {
		String queryString = "SELECT ?p WHERE { <" + strURI + "> ns:what ?p .}";
		System.out.println(queryString);
		return getQuery(queryString);
	}

	public List<String> getWhoTagPicture(String strURI) {
		String queryString = "SELECT ?p WHERE { <" + strURI + "> ns:who ?p .}";
		System.out.println(queryString);
		return getQuery(queryString);
	}
	
	public List<String> getWhenTagPicture(String strURI) {
		String queryString = "SELECT ?l WHERE { <" + strURI + "> dc:date ?l .}";
		return getQuery(queryString);
	}
	
	public List<String> getWhereTagPicture(String strURI) {
		String queryString = "SELECT ?l WHERE { <" + strURI + "> ns:where ?l .}";
		return getQuery(queryString);
	}
	
	public List<String> getQuery(String queryString) {
		Query query = QueryFactory.create(
				"PREFIX rdfs: " + RDFS + "\n" +
				"PREFIX foaf: " + FOAF + "\n" +
				"PREFIX ns: " + NS_PREFIX + "\n" +
				"PREFIX dc: " + DC + "\n" +
				"PREFIX xsd: " + XSD + "\n" +
				queryString);
		
		List<String> resultList = new ArrayList<String>();
		
		  try (QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:3030/ALBUM/sparql",query)) {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("s") ;       // Get a result variable by name.
		      Resource r = soln.getResource("p") ; // Get a result variable - must be a resource
		      Literal l = soln.getLiteral("l") ;   // Get a result variable - must be a literal
		      System.out.println(x+" "+r+" "+l);
		      
		      if (r != null){
		    	  resultList.add(r.toString());
		      }
		      if (l != null){
		    	  resultList.add(l.toString());
		      }
		    }
		    System.out.println(resultList);
		  }
		  
		  return resultList;
	}
	
/*
 *  List of the Static Queries 
 */

	public List<String> getUnicorn() {
		String queryString = "SELECT DISTINCT ?p  WHERE {?p a ns:Picture ;"
								+ "ns:who ?who ."
								+ "?who a ns:Unicorn .}";
		System.out.println(queryString);
		
		return getQuery(queryString);
	}

	public List<String> getRoger() {
		String queryString = "SELECT DISTINCT ?p  WHERE {?p a ns:Picture ;"
								+ "ns:who ns:roger .}";
		System.out.println(queryString);

		return getQuery(queryString);
	}
	
	public List<String> getRogerAndBen() {	
		String queryString = "SELECT DISTINCT ?p  WHERE {?p a ns:Picture ;"
								+ "ns:who ns:roger ;"
								+ "ns:who ns:Ben .}";
		System.out.println(queryString);
	
		return getQuery(queryString);
	}

	public List<String> getPeople() {
		String queryString = "SELECT DISTINCT ?p  WHERE {?p a ns:Picture ;"
								+ "ns:who ?who ."
								+ "?who a foaf:Person .}";
		System.out.println(queryString);

		return getQuery(queryString);
	}

	public List<String> getWithoutPeople() {
		String queryString = 
				"SELECT DISTINCT ?p  WHERE {?p a ns:Picture ."
				+ "OPTIONAL {"
					+ "?p ns:who ?who ."
					+ "FILTER (?who != foaf:Person)"
					+ "} "
				+ "}";
		System.out.println(queryString);
		
		return getQuery(queryString);
	}

	public List<String> getSport() {
		String queryString = 
				"SELECT DISTINCT ?p WHERE {\n" +
				"	{?p a ns:Picture;\n" +
				"		ns:what ?what. \n" +
				"		?what a ns:Sport. \n" +
				"	} UNION {\n" +
				"		?p a ns:Picture ;\n" +
				"		ns:what ns:Sport .\n" +
				"	}\n" +
				"}\n";
							
		System.out.println(queryString);
		
		return getQuery(queryString);
	}

	public List<String> getNature() {
		String queryString = 
				"SELECT DISTINCT ?p WHERE {\n" +
				"	{?p a ns:Picture;\n" +
				"		ns:what ?what. \n" +
				"		?what a ns:Nature. \n" +
				"	} UNION {\n" +
				"		?p a ns:Picture ;\n" +
				"		ns:what ns:Nature .\n" +
				"	}\n" +
				"}\n";
		System.out.println(queryString);
		
		return getQuery(queryString);
	}

	public List<String> getLastYear() {
		String queryString = 
				"SELECT DISTINCT ?p  WHERE {"
				+ "?p a ns:Picture ;"
					+ "dc:date ?d ."
					+ "bind(strdt(?d, xsd:date) as ?date)"
					+ "FILTER (year(?date) = 2016 )"
				+ "}";
		System.out.println(queryString);
		return getQuery(queryString);
	}

	public List<String> getRhoneAlpes() {
		String queryString = 
				"PREFIX ns:<http://www.semanticweb.org/projetAlbum#>\n" +
				"PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
				"PREFIX dbr: <http://dbpedia.org/resource/>\n" +
				"PREFIX db-owl: <http://dbpedia.org/ontology/>\n" +
				"PREFIX db: <http://dbpedia.org/>\n" +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
				"SELECT DISTINCT ?p\n" +
				"WHERE {\n" +
				"  ?p a ns:Picture ;\n" +
				"     ns:where ?city .\n" +
				"  BIND(IRI(CONCAT('http://dbpedia.org/resource/',?city)) AS ?cityResource)\n" +
				"  SERVICE <http://dbpedia.org/sparql>\n" +
				"  {\n" +
				"    ?cityResource db-owl:region dbr:Rhône-Alpes .\n" +
				"  }\n" +
				"}\n";
		System.out.println(queryString);
		return getQuery(queryString);
	}

	public List<String> getStringAsResourceOrLitteral(String queryString) {
		queryString = 
				"SELECT DISTINCT ?p WHERE {" +
				"   {?p a ns:Picture ;" +
				"       ?b \"" + queryString + "\" . }" +
				"   UNION {" +
				"    ?p a ns:Picture ;" +
				"       ?b ns:" + queryString + " . }" +
				"}";
		
		return getQuery(queryString);
	}
	
	//At least we discover http://www.buildmystring.com/
	
}
