package fr.uga.miashs.album.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.webbeans.context.RequestContext;

import arq.sparql;
import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.PictureService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.service.SparqlQueryService;

@Named
@ManagedBean
@SessionScoped
public class SearchControllerTemp {
	@Inject
	private PictureService pictureService;

	@Inject
	private SparqlQueryService sparqlQueryService;

	private List<Picture> pictures;
	
	private String query;
	
	private String radioSelectedQuery;

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

	@PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.query = params.get("query");
        
        if(query != null){
            try {
            	List<String> listURI = sparqlQueryService.getAllPictures();
            	this.pictures = pictureService.listPictureFromListURI(listURI);
    		} catch (ServiceException e) {
    			e.printStackTrace();
    		}
        }
    }
	
	public String search() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String query = ec.getRequestParameterMap().get("searchForm:query");
		return "search?faces-redirect=true&query="+query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	
	public String getRadioSelectedQuery() {
		return radioSelectedQuery;
	}

	public void setRadioSelectedQuery(String radioSelectedQuery) {
		this.radioSelectedQuery = radioSelectedQuery;
	}
	
	public Map<String,Object> getMapStaticQuery() {
		return mapStaticQuery;
	}

}
