package fr.uga.miashs.album.control;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.AlbumService;
import fr.uga.miashs.album.service.PictureService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.service.SparqlUpdateService;

@Named
@ManagedBean
@RequestScoped
public class AnnotationController {

	@Inject
	private AppUserSession appUserSession;
	
	@Inject
	private AlbumService albumService;
	
	@Inject
	private PictureService pictureService;
	
	@Inject
	private SparqlUpdateService sparqlUpdateService;
	
	
	public void tagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("labelForm:pictureId");
		String lblWhere = ec.getRequestParameterMap().get("labelForm:where");
		String lblWhen = ec.getRequestParameterMap().get("labelForm:when");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			if (lblWhere != "" && lblWhere != null){
				sparqlUpdateService.insertWhereProperty(picture.getUri().toString(), lblWhere);
			}
			if (lblWhen != "" && lblWhen != null){
				sparqlUpdateService.insertWhenProperty(picture.getUri().toString(), lblWhen);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	
	}
	
	public void whoTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("whoTagHiddenForm:pictureId");
		String lblWho = ec.getRequestParameterMap().get("whoTagHiddenForm:who");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			if (lblWho != "" && lblWho != null){
				sparqlUpdateService.insertWhoProperty(picture.getUri().toString(), lblWho);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	}
	
	public void whatTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("whatTagHiddenForm:pictureId");
		String lblWhat = ec.getRequestParameterMap().get("whatTagHiddenForm:who");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			if (lblWhat != "" && lblWhat != null){
				sparqlUpdateService.insertWhoProperty(picture.getUri().toString(), lblWhat);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	}
}
