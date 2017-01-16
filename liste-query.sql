PREFIX ns:<http://www.semanticweb.org/projetAlbum#>
PREFIX dc: <http://purl.org/dc/elements/1.1/>
PREFIX dbr: <http://dbpedia.org/resource/>
PREFIX db-owl: <http://dbpedia.org/ontology/>
PREFIX db: <http://dbpedia.org/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

# Select all pictures
SELECT ?p  WHERE {?p a ns:Picture .}

# Select picture with a Unicorn
SELECT DISTINCT ?p  
WHERE {
	?p a ns:Picture ;ns:who ?who .?who a ns:Unicorn .
}

# Select all pictures of roger
SELECT DISTINCT ?p  
WHERE {
	?p a ns:Picture ;ns:who ns:roger .
}

# Select all picturee of Ben et roger
SELECT DISTINCT ?p  
WHERE {
	?p a ns:Picture ;ns:who ns:roger ;ns:who ns:Ben .
}

# Select all pictures with a person
SELECT DISTINCT ?p  
WHERE {
	?p a ns:Picture ;ns:who ?who .?who a foaf:Person .
}


# Select all picture without a person
SELECT DISTINCT ?p  
WHERE {
	?p a ns:Picture .
	OPTIONAL {
		?p ns:who ?who .
		FILTER (?who != foaf:Person)
	} 
}

# Select all pictures about sport
SELECT DISTINCT ?p WHERE {
	{?p a ns:Picture;
		ns:what ?what. 
		?what a ns:Sport. 
	} UNION {
		?p a ns:Picture ;
		ns:what ns:Sport .
	}
}

# Select all pictures about nature
SELECT DISTINCT ?p WHERE {
	{?p a ns:Picture;
		ns:what ?what. 
		?what a ns:Nature. 
	} UNION {
		?p a ns:Picture ;
		ns:what ns:Nature .
	}
}


# Select all pictures of 2016
SELECT DISTINCT ?p  
WHERE {
	?p a ns:Picture ;
	dc:date ?d .
	bind(strdt(?d, xsd:date) as ?date)
	FILTER (year(?date) = 2016 )
}

# Select all pictures in Rhones-Alpes
SELECT DISTINCT ?p
WHERE {
  ?p a ns:Picture ;
     ns:where ?city .
  BIND(IRI(CONCAT('http://dbpedia.org/resource/',?city)) AS ?cityResource)
  SERVICE <http://dbpedia.org/sparql>
  {
    ?cityResource db-owl:region dbr:Rhône-Alpes .
  }
}
