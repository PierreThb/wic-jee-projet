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
public class SearchController {
	@Inject
	private PictureService pictureService;

	@Inject
	private SparqlQueryService sparqlQueryService;

	private List<Picture> pictures;

	private String query;

	private String queryString;

	private static Map<String, Object> mapStaticQuery;
	static {
		mapStaticQuery = new LinkedHashMap<String, Object>();
		mapStaticQuery.put("All Pictures", "all"); // label, value
		mapStaticQuery.put("All Pictures with a Unicorn", "unicorn");
		mapStaticQuery.put("All Pictures with Roger the Unicorn", "roger");
		mapStaticQuery.put("All Pictures with Roger and Ben", "rogerAndBen");
		mapStaticQuery.put("All Pictures with People", "people");
		mapStaticQuery.put("All Pictures without People", "withoutPeople");
		mapStaticQuery.put("All Pictures of Sport", "sport");
		mapStaticQuery.put("All Pictures of Nature", "nature");
		mapStaticQuery.put("All Pictures taken last year", "lastYear");
	}

	@PostConstruct
	public void init() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		this.query = params.get("query");

		if (query != null) {
			try {
				List<String> listURI;
				switch (query) {
				case "all":
					listURI = sparqlQueryService.getAllPictures();
					break;
				case "unicorn":
					listURI = sparqlQueryService.getUnicorn();
					break;
				case "roger":
					listURI = sparqlQueryService.getRoger();
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
				case "sport":
					listURI = sparqlQueryService.getSport();
					break;
				case "nature":
					listURI = sparqlQueryService.getNature();
					break;
				case "lastYear":
					listURI = sparqlQueryService.getLastYear();
					break;
				default:
					listURI = null;
					break;
				}
				
				if(listURI != null && !listURI.isEmpty()){
					this.pictures = pictureService.listPictureFromListURI(listURI);
				}
				
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}

	public String search() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String query = ec.getRequestParameterMap().get("searchForm:query");
		return "search?faces-redirect=true&query=" + query;
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

	public Map<String, Object> getMapStaticQuery() {
		return mapStaticQuery;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

}
