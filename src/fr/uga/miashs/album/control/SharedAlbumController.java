package fr.uga.miashs.album.control;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;
import fr.uga.miashs.album.service.AlbumService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.service.SparqlQueryService;

@Named
@ManagedBean
@RequestScoped
public class SharedAlbumController {
	
	@Inject
	private AppUserSession appUserSession;
	
	@Inject
	private AlbumService albumService;

	@Inject
	private SparqlQueryService sparqlQueryService;
	
	private List<String> friends;
	
	@PostConstruct
	public void init() {
		AppUser user = appUserSession.getConnectedUser();
		if( user != null){
			friends = sparqlQueryService.getFriends(user.getId());
		}
	}
	
	public List<Album> getListAlbumSharedWithCurrentUser() throws ServiceException {
		return albumService.listAlbumSharedWith(appUserSession.getConnectedUser());
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
}
