package fr.uga.miashs.album.control;

import java.util.List;

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
import fr.uga.miashs.album.service.SparqlQueryService;
import fr.uga.miashs.album.service.SparqlUpdateService;

@Named
@ManagedBean
@RequestScoped
public class AnnotationController {
	
	@Inject
	private PictureService pictureService;
	
	@Inject
	private SparqlUpdateService sparqlUpdateService;
	
	@Inject 
	private SparqlQueryService sparqlQueryService;
	
	private Picture picture;
	
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
				sparqlQueryService.getWhoTagPicture(picture.getUri().toString());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	}
	
	public void whatTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("whatTagHiddenForm:pictureId");
		String lblWhat = ec.getRequestParameterMap().get("whatTagHiddenForm:what");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			if (lblWhat != "" && lblWhat != null){
				sparqlUpdateService.insertWhatProperty(picture.getUri().toString(), lblWhat);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	}
	
	public void whereTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("whereTagHiddenForm:pictureId");
		String lblWhere = ec.getRequestParameterMap().get("whereTagHiddenForm:where");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			if (lblWhere != "" && lblWhere != null){
				sparqlUpdateService.insertWhereProperty(picture.getUri().toString(), lblWhere);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}	
	}
	
	public void getWhoTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("labelForm:pictureId");
		System.out.println("aseit");
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			sparqlQueryService.getWhoTagPicture(picture.getUri().toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void getWhatTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("labelForm:pictureId");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			sparqlQueryService.getWhatTagPicture(picture.getUri().toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void getWhereTagPicture(){
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pictureId = ec.getRequestParameterMap().get("labelForm:pictureId");
		
		try {
			Picture picture = pictureService.getPictureById(pictureId);
			sparqlQueryService.getWhereTagPicture(picture.getUri().toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
