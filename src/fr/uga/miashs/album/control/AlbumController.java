package fr.uga.miashs.album.control;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.service.AlbumService;
import fr.uga.miashs.album.service.ServiceException;
import fr.uga.miashs.album.util.Pages;

@Named
@RequestScoped
public class AlbumController {

	@Inject
	private AppUserSession appUserSession;
	
	@Inject
	private AlbumService albumService;

	
	private Album album;
	
	private String nomAlbum;
	
	private Part zip;
	
	public Part getZip() {
		return zip;
	}

	public void setZip(Part zip) {
		this.zip = zip;
	}
	
	
	public Album getAlbum() {
		if (album==null) {
			album = new Album(appUserSession.getConnectedUser());
		}
		return album;
	}
	
	
	public String createAlbum() {
		try {
			albumService.create(album);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Pages.list_album;
	}
	
	public List<Album> getListAlbumOwnedByCurrentUser() {
		try {
			return albumService.listAlbumOwnedBy(appUserSession.getConnectedUser());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/* add the zip files */
	
	public String getNomAlbum() {
		return nomAlbum;
	}

	public void setNomAlbum(String nomAlbum) {
		this.nomAlbum = nomAlbum;
	}

	public void validerNomAlbum(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Path rootDir = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("directory"));
		Path albPath = rootDir.resolve((String) value);
		if (Files.exists(albPath)) {
			msgs.add(new FacesMessage("Un album avec le même nom existe déjà"));
		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}

	public void validerZip(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part) value;
		if (file.getSize() > 1024 * 1024 * 100) {
			msgs.add(new FacesMessage("file too big"));
		}
		if (!"application/zip".equals(file.getContentType())) {
			msgs.add(new FacesMessage("not a Zip file"));
		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}

	public String creerAlbum() {
		Path rootDir = Paths.get(FacesContext.getCurrentInstance().getExternalContext().getInitParameter("directory"));
		Path albPath = rootDir.resolve(nomAlbum);
		try {
			Files.createDirectories(albPath);
			saveFiles(new ZipInputStream(zip.getInputStream()), albPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "faces/albums.xhtml";

	}

	private void saveFiles(ZipInputStream zis, Path whereDir) {
		ZipEntry ent = null;
		try {
			while ((ent = zis.getNextEntry()) != null) {
				// si repertoire, on ne fait rien
				if (ent.getName().endsWith("/"))
					continue;
				int idx = ent.getName().lastIndexOf('/');
				String filename = ent.getName();
				if (idx > -1) {
					filename = ent.getName().substring(idx + 1);
				}
				// si fichier caché, on ne fait rien non plus
				if (filename.charAt(0) == '.')
					continue;
				try {

					Path path = whereDir.resolve(filename);
					// Files.createFile(path);
					OutputStream bos = new BufferedOutputStream(Files.newOutputStream(path));
					byte[] b = new byte[4094];
					int read = -1;
					while ((read = zis.read(b)) != -1) {
						bos.write(b, 0, read);
					}
					bos.close();
					zis.closeEntry();
					// determin le type mime en fonction de l'extension du
					// fichier (en minuscule)
					String mime = FacesContext.getCurrentInstance().getExternalContext()
							.getMimeType(path.toString().toLowerCase());
					// si le type mime du fichier n'est pas une image, on la
					// supprime.
					if (mime == null || !mime.startsWith("image/"))
						Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
