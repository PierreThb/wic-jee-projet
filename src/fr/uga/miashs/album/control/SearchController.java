package fr.uga.miashs.album.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.webbeans.context.RequestContext;

import arq.sparql;
import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.PictureService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.service.SparqlQueryService;

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
	
	private List<Picture> listPicture;
	
	@PostConstruct
    public void test() {
        System.out.println("SearchController");
		if(getListPicture() != null){
			System.out.println("lisPic not null");
		}
        
    }
	
	public String getRadioSelectedQuery() {
		return radioSelectedQuery;
	}
	
	public List<String> getListURI() {
		return listURI;
	}

	public void setListURI(List<String> listURI) {
		this.listURI = listURI;
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
	
	public void getListPictureFromQuery() throws ServiceException {
		System.out.println("list pic from query + " + listURI);
		if (listURI != null){
			setListPicture(pictureService.listPictureFromListURI(listURI));
		}	
		
		if(getListPicture() != null){
			System.out.println("lisPic not null");
		}
	}
	

	public void getStaticQuery() throws ServiceException {
		System.out.println(radioSelectedQuery);
		if(radioSelectedQuery != null){
			switch(radioSelectedQuery){
				case "all":
					listURI = sparqlQueryService.getAllPictures();
					break;
				case "unicorn":
					listURI = sparqlQueryService.getUnicorn();
					break;
				case "roger":
					listURI = sparqlQueryService.getRogerAndBen();
					break;
				case "rogerAndBen":
					listURI = sparqlQueryService.getRogerAndBen();
					break;
				case "people":
					listURI = sparqlQueryService.getPeople();
					break;
				case "withoutPeople":
					listURI = sparqlQueryService.getWithoutPeople();
					break;
				default:
					listURI = null;
					break;
			}
		}
		System.out.println(listURI);
		getListPictureFromQuery();
	}

	public List<Picture> getListPicture() {
		return listPicture;
	}

	public void setListPicture(List<Picture> listPicture) {
		this.listPicture = listPicture;
	}

}
