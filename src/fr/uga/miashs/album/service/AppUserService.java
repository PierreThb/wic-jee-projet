package fr.uga.miashs.album.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import fr.uga.miashs.album.model.Album;
import fr.uga.miashs.album.model.AppUser;

@Named
@ApplicationScoped
public class AppUserService extends JpaService<Long,AppUser> {

	private SparqlUpdateService sparqlUpdateService = new SparqlUpdateService();
	
	@Override
	public void create(AppUser v) throws ServiceException {
		try {
			super.create(v);
			
		}
		catch (RollbackException e) {
			if (e.getCause() instanceof EntityExistsException) {
				throw new ServiceException("Un utilisateur avec l'email "+v.getEmail()+" existe déjà",e);
			}
			else {
				new ServiceException(e);
			}
		}
		sparqlUpdateService.insertPerson(v);
	}

	public AppUser login(String email, String password) throws ServiceException {
		Query query = getEm().createNamedQuery("AppUser.login");
		query.setParameter("email", email);
		query.setParameter("password", password);
		try {
			return (AppUser) query.getSingleResult();
		}
		catch (NoResultException e) {
			throw new ServiceException("Utilisateur Inconnu",e);
		}
	}
	
	public List<AppUser> listUsers() {
		 Query query = getEm().createNamedQuery("AppUser.findAll");
		 return query.getResultList();
	}
	
	public AppUser getUserById(String id) throws ServiceException{
		AppUser user = getEm().find(AppUser.class, id);
		getEm().refresh(user);
		
		return user;
	}
	
	public void deleteUserById(String id) throws ServiceException{
		AppUser user = getEm().find(AppUser.class, id);
		getEm().getTransaction().begin();
		getEm().remove(user);
		getEm().getTransaction().commit();
	}
}
