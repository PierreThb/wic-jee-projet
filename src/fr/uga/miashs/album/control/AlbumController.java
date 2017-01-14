package fr.uga.miashs.album.control;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.apache.myfaces.util.FilenameUtils;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.Picture;
import fr.uga.miashs.album.service.AlbumService;
import fr.uga.miashs.album.service.PictureAnnotationService;
import fr.uga.miashs.album.service.PictureService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.service.SparqlService;
import fr.uga.miashs.album.util.Pages;

@Named
@ManagedBean
@RequestScoped
public class AlbumController { 

	@Inject
	private AppUserSession appUserSession;
	
	@Inject
	private AlbumService albumService;
	
	@Inject
	private PictureService pictureService;
	
	@Inject
	private PictureAnnotationService pictureAnnotationService;
	
	private Album album;
	
	private Part file;
	
	// Set the current album if 
	@PostConstruct
    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String albumId = params.get("albumId");
        
        if(albumId != null){
            try {
    			album = albumService.getAlbumById(albumId);
    		} catch (ServiceException e) {
    			
    		}
        }else{
        	album = new Album(appUserSession.getConnectedUser());
        }
    }	
	
	public Album getAlbum() {
		if (album==null) {
			album = new Album(appUserSession.getConnectedUser());
		}
		return album;
	}
	
	public String checkAlbum() {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String albumId = params.get("albumId");
        
        if(albumId == null || albumId == ""){
	    	return "list-album?faces-redirect=true";
	    } else {
	        return null; // Stay on current page.
	    }
	}
	
	public String createAlbum() {		
		try {
			albumService.create(album);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return Pages.list_album;
	}
	
	public String displayAlbum(Long id) {
		return "album?faces-redirect=true&albumId="+id;
	}
	
	public List<Album> getListAlbumOwnedByCurrentUser() throws ServiceException {
		return albumService.listAlbumOwnedBy(appUserSession.getConnectedUser());
	}
	
	public List<Picture> getListPictureFromAlbum() throws ServiceException {
		if(album.getId() != 0){
			List<Picture> listPictures = pictureService.listPictureFromAlbum(album);
			return pictureService.listPictureFromAlbum(album);
		}
		return null;
	}

	public void validerNomAlbum(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Path rootDir = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("directory"));
		Path albPath = rootDir.resolve((String) value);
		if (Files.exists(albPath)) {
			msgs.add(new FacesMessage("This album name already exists"));
		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}
	
	public Part getFile() {
        return file;
    }

    public void setFile(Part file) throws IOException {
    	try (InputStream input = file.getInputStream()) {
	        this.file = file;
	        uploadPictures();
    	}
	    catch (IOException e) {
	    }
    }

	public void uploadPictures() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    
		try (InputStream input = file.getInputStream()) {
			String disposition = file.getHeader("Content-Disposition"); 
			String fullname = disposition.replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$", "$1");
			
			String filename = FilenameUtils.getBaseName(fullname);
			System.out.println("filename : "+filename);
			String extension = FilenameUtils.getExtension(fullname);
			System.out.println("extension : "+extension);
			Path folder = Paths.get(externalContext.getInitParameter("directory"));
			System.out.println("folder : "+folder);
			Path filepath = Files.createTempFile(folder, filename + "-", "." + extension);
			System.out.println("filepath : "+filepath);
			Files.copy(input, filepath, StandardCopyOption.REPLACE_EXISTING);
			
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		    String albumId = ec.getRequestParameterMap().get("uploadForm:albumId");
	        
	        if(albumId != null){
	            try {
	    			album = albumService.getAlbumById(albumId);
	    		} catch (ServiceException e) {}
	        }
			
			Picture picture = new Picture(album,filepath.getFileName().toString(),filepath);
			try {
				System.out.println("album : "+album.getOwner().getFirstname());
				pictureService.create(picture);
				//sparqlService.insertData("coucou");
				pictureAnnotationService.pushup();
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			
		    externalContext.setResponseContentType("application/json");
		    externalContext.setResponseCharacterEncoding("UTF-8");
		    externalContext.getResponseOutputWriter().write("{filepath :"+filepath+"}");
		    facesContext.responseComplete();
	    }
	    catch (IOException e) {
	    }
	}
	
	public void deletePicture() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    String pictureId = ec.getRequestParameterMap().get("deleteHiddenForm:pictureId");
		System.out.println("delete image : "+pictureId);
		
		//TODO
	}
	
	public void displayPicture(String filename) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    
	    File file = new File(ec.getInitParameter("directory")+filename);
		String fileName = file.getName();
		String contentType = ec.getMimeType(fileName);
		int contentLength = (int) file.length();

	    ec.responseReset(); 
	    ec.setResponseContentType(contentType); 
	    ec.setResponseContentLength(contentLength); 
	    ec.setResponseHeader("Content-disposition", "inline;filename=\"" + fileName + "\""); 
	    OutputStream output = ec.getResponseOutputStream(); 

		Files.copy(file.toPath(), output);
	}
}
