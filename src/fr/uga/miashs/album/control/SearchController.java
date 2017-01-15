package fr.uga.miashs.album.control;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.ServiceException;

@Named
@ManagedBean
@SessionScoped
public class SearchController {

	public String radioSelectedQuery;
	
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
		mapStaticQuery.put("All Pictures with Poeple", "people");
		mapStaticQuery.put("All Pictures without Poeple", "withoutPeople");
	}
	
	public Map<String,Object> getMapStaticQuery() {
		return mapStaticQuery;
	}
	
	public List<Picture> getListPictureFromQuery() throws ServiceException {
		//List<Picture> listPictures = pictureService.listPictureFrom(album);
		//return pictureService.listPictureFromAlbum(album);
		
		return null;
	}

}
