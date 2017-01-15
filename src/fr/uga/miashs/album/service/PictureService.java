package fr.uga.miashs.album.service;

import java.util.List;

import javax.persistence.Query;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;
import fr.uga.miashs.album.model.Picture;

public class PictureService extends JpaService<Long,Picture> {

	public void create(Picture p) throws ServiceException {
		Album album = p.getAlbum();
		album.setOwner(getEm().merge(getEm().merge( album.getOwner())));
		p.setAlbum(getEm().merge(getEm().merge(album)));
		super.create(p);
	}
	
	public Picture getPictureById(String id) throws ServiceException{
		Picture picture = getEm().find(Picture.class, id);
		getEm().refresh(picture);
		
		return picture;
	}
	
	public List<Picture> listPictureFromAlbum(Album a){
		System.out.println(a.getId());
		Query query = getEm().createNamedQuery("Picture.findAllPicturesFromAlbum");
		query.setParameter("album", getEm().merge(a));
		return query.getResultList();
	}
	
	public List<Picture> listPicturesOwnedBy(AppUser a) throws ServiceException {
		Query query = getEm().createNamedQuery("Picture.findAllOwned");
		query.setParameter("owner", getEm().merge(a));
		List<Picture> results = query.getResultList();
		return results;
	}
	
	public void deletePictureById(String id) throws ServiceException{
		Picture picture = getEm().find(Picture.class, id);
		getEm().getTransaction().begin();
		getEm().remove(picture);
		getEm().getTransaction().commit();
	}
}
