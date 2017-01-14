package fr.uga.miashs.album.service;

import org.apache.jena.update.*;

import fr.uga.miashs.album.model.Picture;

public class PictureAnnotationService extends JpaService<Long, Picture> {
	
	public void pushup() {
		
		System.out.println("on est dans le service");
		
		UpdateRequest req = UpdateFactory.create();
		
		System.out.println("on a fait une requete vide ");
		
		req.add("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		req.add("INSERT DATA {");
		req.add("<http://www.semanticweb.org/projetAlbum#Pic565416> a <http://www.semanticweb.org/projetAlbum#Picture> .}");
		System.out.println(req);
		UpdateProcessor up = UpdateExecutionFactory.createRemote(req, "http://localhost:3030/ALBUM/update");
		System.out.println("up");
		up.execute();
		
		
		System.out.println("fin d'insert");
	}

}
