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
	
	public List<Picture> listPictureFromAlbum(Album a){
		System.out.println(a.getId());
		Query query = getEm().createNamedQuery("Picture.findAllPicturesFromAlbum");
		query.setParameter("album", getEm().merge(a));
		return query.getResultList();
	}
}
