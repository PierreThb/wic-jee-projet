package fr.uga.miashs.album.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
@NamedQueries({
    @NamedQuery(name="Album.findAllOwned",
                query="SELECT a FROM Album a WHERE a.owner=:owner"),
    @NamedQuery(name="Album.findAlbumSharedWith",
				query="SELECT a FROM Album a WHERE :sharedWith MEMBER OF a.sharedWith"),
})
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	private String title;
	
	private String description;
	
	@NotNull
	@ManyToOne
	private AppUser owner;
	
	@OneToMany(mappedBy="album", cascade=CascadeType.ALL)
	private Set<Picture> pictures;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<AppUser> sharedWith;

	protected Album() {
	}
	
	public Album(AppUser owner) {
		this.owner=owner;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public Set<AppUser> getSharedWith() {
		return sharedWith;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}
}
