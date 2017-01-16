package fr.uga.miashs.album.control;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.service.AlbumService;
import fr.uga.miashs.album.service.ServiceException;

@Named
@ManagedBean
@RequestScoped
public class SharedAlbumController {
	
	@Inject
	private AppUserSession appUserSession;
	
	@Inject
	private AlbumService albumService;
	
	public List<Album> getListAlbumSharedWithCurrentUser() throws ServiceException {
		return albumService.listAlbumSharedWith(appUserSession.getConnectedUser());
	}
}
