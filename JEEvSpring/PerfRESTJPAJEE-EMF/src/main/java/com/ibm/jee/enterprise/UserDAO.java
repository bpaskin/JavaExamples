package com.ibm.jee.enterprise;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.ibm.jee.jpa.model.User;

@Stateless
@LocalBean
@Transactional(value=TxType.REQUIRES_NEW)
public class UserDAO {
	
	private static EntityManagerFactory entityManagerFactory = null;
	private EntityManager em;
	
	@PostConstruct
	private void init() {
		em = getEntityManagerFactory().createEntityManager();
	  }
	
	public static EntityManagerFactory getEntityManagerFactory() {
		if (null == entityManagerFactory) {
		  entityManagerFactory = Persistence.createEntityManagerFactory("PerfRESTJPAJEE");
		}
		return entityManagerFactory;
	}
	
	public void addUser(String name) {
		User user = new User();
		user.setName(name);
		em.persist(user);
	}
	
	public void clear() {
		em.joinTransaction();
		em.createNamedQuery("User.deleteAll").setHint("javax.persistence.query.timeout", 5000).executeUpdate();
	}
	
	public List<User> listAllUsers() {
		return em.createNamedQuery("User.findAll").setHint("javax.persistence.query.timeout", 5000).getResultList();
	}
	
	public List<User> getUser(String name) {
		return em.createNamedQuery("User.findUser").setParameter("name", name).setHint("javax.persistence.query.timeout", 5000).getResultList();
	}
}
