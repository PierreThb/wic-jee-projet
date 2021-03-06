package fr.uga.miashs.album.service;

import java.util.List;

import javax.persistence.Query;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;
import fr.uga.miashs.album.model.Picture;


public class AlbumService extends JpaService<Long,Album> {
	
	private SparqlUpdateService sparqlUpdateService = new SparqlUpdateService();
	
	private SparqlDeleteService sparqlDeleteService = new SparqlDeleteService();
	
	public Album getAlbumById(String id) throws ServiceException{
		Album album = getEm().find(Album.class, id);
		getEm().refresh(album);
		
		return album;
	}
	
	public void create(Album a) throws ServiceException {
		a.setOwner(getEm().merge(getEm().merge( a.getOwner())));
		super.create(a);
		
		sparqlUpdateService.insertAlbum(a);
	}
	
	public void share(String albumId, String userId) throws ServiceException {
		Album a = getEm().find(Album.class, albumId);
		AppUser u = getEm().find(AppUser.class, userId);
		a.getSharedWith().add(u);
		getEm().getTransaction().begin();
		getEm().merge(a);
		getEm().getTransaction().commit();
		
		sparqlUpdateService.shareAlbum(a, userId);
	}
	
	public List<Album> listAlbumOwnedBy(AppUser a) throws ServiceException {
		Query query = getEm().createNamedQuery("Album.findAllOwned");
		query.setParameter("owner", getEm().merge(a));
		return query.getResultList();
	}
	
	public List<Album> listAlbumSharedWith(AppUser a) throws ServiceException {
		Query query = getEm().createNamedQuery("Album.findAlbumSharedWith");
		query.setParameter("sharedWith", getEm().merge(a));
		return query.getResultList();
	}

	
	public void deleteAlbumById(String id) throws ServiceException{
		Album album = getEm().find(Album.class, id);
		getEm().getTransaction().begin();
		getEm().remove(album);
		getEm().getTransaction().commit();
		
		// sparqlDeleteService.deleteAlbum(album);
	}
}
