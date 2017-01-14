package fr.uga.miashs.album.control;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;
import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.AlbumService;
import fr.uga.miashs.album.service.AppUserService;
import fr.uga.miashs.album.service.PictureService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.util.Pages;


/*
 * L'annotation @Named permet de créer un bean CDI
 * Le bean CDI va remplacer les ManagedBean de JSF à partir de JSF 2.3
 * Leur intéret est qu'ils sont utilisables en dehors du contexte JSF.
 * On peut les utiliser aussi via l'annotation @Inject
 * Il faut faire attention que l'annotation @RequestScoped vienne bien du package 
 * javax.enterprise.context et non de l'ancien package javax.faces.bean
 */
@Named
@RequestScoped
public class AppUserController implements Serializable {

	@Inject
	private AppUserService appUserService;

	@Inject
	private AlbumService albumService;
	
	@Inject
	private PictureService pictureService;
	
	@Inject
	private AppUserSession appUserSession;
	
	private AppUser user;
	
	public AppUserController() {
		user = new AppUser();
	}	
	
	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String create() {
		try {
			appUserService.create(user);
		} catch (ServiceException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage(e.getLocalizedMessage());
			facesContext.addMessage("email", facesMessage);
			return null;
		}
		
		return "index?faces-redirect=true";
		
	}
	
	public void delete() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String userId = ec.getRequestParameterMap().get("deleteHiddenForm:userId");
		System.out.println("delete user : "+userId);
		
		try {
			AppUser user = appUserService.getUserById(userId);
			List<Album> albums = albumService.listAlbumOwnedBy(user);
			
			for(Album album : albums){
				deleteAlbum(album);
			}
			
			appUserService.deleteUserById(userId);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		if(String.valueOf(appUserSession.getConnectedUser().getId()).equals(userId)){
			FacesContext context = FacesContext.getCurrentInstance();
			((HttpSession) context.getExternalContext().getSession(false)).invalidate();
			appUserSession.setConnectedUser(null);
		}
	}
	
	public void deleteAlbum(Album album) {
		System.out.println("delete album : "+album.getId());
		
		try {
			List<Picture> pictures = pictureService.listPictureFromAlbum(album);
			for(Picture picture : pictures){
				deletePicture(picture);
			}
			
			albumService.deleteAlbumById(String.valueOf(album.getId()));
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePicture(Picture picture) {
		System.out.println("delete image : "+picture.getId());
		
		try {
			Path filepath = Paths.get(picture.getLocalfile());
			Files.deleteIfExists(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
