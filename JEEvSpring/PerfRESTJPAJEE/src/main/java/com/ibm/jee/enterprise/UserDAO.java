package com.ibm.jee.enterprise;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ibm.jee.jpa.model.User;

@Stateless
public class UserDAO {
	
	@PersistenceContext(unitName="PerfRESTJPAJEE")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addUser(String name) {
		User user = new User();
		user.setName(name);
		em.persist(user);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void clear() {
		em.createNamedQuery("User.deleteAll").setHint("javax.persistence.query.timeout", 5000).executeUpdate();
	}
	
	public List<User> listAllUsers() {
		return em.createNamedQuery("User.findAll").setHint("javax.persistence.query.timeout", 5000).getResultList();
	}
	
	public List<User> getUser(String name) {
		return em.createNamedQuery("User.findUser").setParameter("name", name).setHint("javax.persistence.query.timeout", 5000).getResultList();
	}
}
