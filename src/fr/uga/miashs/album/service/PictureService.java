package fr.uga.miashs.album.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.Query;

import org.hsqldb.lib.Iterator;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.Picture;

public class PictureService extends JpaService<Long,Picture> {
	
	private SparqlUpdateService sparqlUpdateService = new SparqlUpdateService();
	
	public void create(Picture p) throws ServiceException {
		Album album = p.getAlbum();
		album.setOwner(getEm().merge(getEm().merge( album.getOwner())));
		p.setAlbum(getEm().merge(getEm().merge(album)));
		super.create(p);
		
		sparqlUpdateService.insertPicture(p);
	}
	
	public Picture getPictureById(String id) throws ServiceException{
		Picture picture = getEm().find(Picture.class, id);
		getEm().refresh(picture);
		
		return picture;
	}
	
	public Picture getPictureByURI(String uri) throws ServiceException{
		Picture picture = getEm().find(Picture.class, uri);
		getEm().refresh(picture);
		return picture;
	}
	
	public List<Picture> listPictureFromListURI(List<String> listURI) throws NoSuchElementException, ServiceException{
		List<Picture> listPicture = new ArrayList<Picture>();
		
		for (Iterator it = (Iterator) listURI.iterator(); it.hasNext() ;)
		{
			Picture p = getPictureByURI(it.next().toString());
			listPicture.add(p);
		}	
		return listPicture;
	}
	
	
	public List<Picture> listPictureFromAlbum(Album a){
		System.out.println(a.getId());
		Query query = getEm().createNamedQuery("Picture.findAllPicturesFromAlbum");
		query.setParameter("album", getEm().merge(a));
		return query.getResultList();
	}
	
	public void deletePictureById(String id) throws ServiceException{
		Picture picture = getEm().find(Picture.class, id);
		getEm().getTransaction().begin();
		getEm().remove(picture);
		getEm().getTransaction().commit();
	}
}
