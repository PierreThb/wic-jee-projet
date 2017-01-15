package fr.uga.miashs.album.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import arq.sparql;
import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.PictureService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.service.SparqlQueryService;
import fr.uga.miashs.album.util.Pages;

@Named
@ManagedBean
@SessionScoped
public class SearchController {
	
	@Inject
	private PictureService pictureService;
	
	@Inject
	private SparqlQueryService sparqlQueryService;

	public String radioSelectedQuery;
	
	public List<String> listURI;
	
	public String getRadioSelectedQuery() {
		return radioSelectedQuery;
	}

	public void setRadioSelectedQuery(String radioSelectedQuery) {
		this.radioSelectedQuery = radioSelectedQuery;
	}

	private static Map<String,Object> mapStaticQuery;
	static{
		mapStaticQuery = new LinkedHashMap<String,Object>();
		mapStaticQuery.put("All Pictures", "all"); // label, value
		mapStaticQuery.put("All Pictures with a Unicorn", "unicorn");
		mapStaticQuery.put("All Pictures with Roger the Unicorn", "roger");
		mapStaticQuery.put("All Pictures with Roger and Ben", "rogerAndBen");
		mapStaticQuery.put("All Pictures with People", "people");
		mapStaticQuery.put("All Pictures without People", "withoutPeople");
	}
	
	public Map<String,Object> getMapStaticQuery() {
		return mapStaticQuery;
	}
	
	public List<Picture> getListPictureFromQuery() throws ServiceException {
		if (listURI != null){
			return pictureService.listPictureFromListURI(listURI);
		}		
		return null;
	}
	

	public void getStaticQuery() {
		if(radioSelectedQuery != null){
			switch(radioSelectedQuery){
			case "all":
				listURI = sparqlQueryService.getAllPictures();
			case "unicorn":
				listURI = sparqlQueryService.getUnicorn();
			case "roger":
				listURI = sparqlQueryService.getRogerAndBen();
			case "rogerAndBen":
				listURI = sparqlQueryService.getRogerAndBen();
			case "people":
				listURI = sparqlQueryService.getPeople();
			case "withoutPeople":
				listURI = sparqlQueryService.getWithoutPeople();
			default:		
			}
		}
		
		System.out.println(listURI);
	}

}
