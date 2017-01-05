package fr.uga.miashs.album.service;

import javax.persistence.Query;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.Picture;

public class PictureService extends JpaService<Long,Picture> {

	public void create(Picture p) throws ServiceException {
		Album album = p.getAlbum();
		album.setOwner(getEm().merge(getEm().merge( album.getOwner())));
		p.setAlbum(getEm().merge(getEm().merge(album)));
		super.create(p);
	}
}
