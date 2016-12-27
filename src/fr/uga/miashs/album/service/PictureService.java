package fr.uga.miashs.album.service;

import javax.persistence.Query;

import fr.uga.miashs.album.model.Picture;

public class PictureService extends JpaService<Long,Picture> {

	public void create(Picture p) throws ServiceException {
		super.create(p);
	}
}
