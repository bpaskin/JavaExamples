package com.ibm.example.jgroups.enterprise;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ibm.example.jgroups.model.Perfuser;

@Stateless
public class UserFacade {

	@PersistenceContext
	private EntityManager em;
		
	public List<Perfuser> findAllUsers() {
		return  em.createNamedQuery("Perfuser.findAll").getResultList();
	}
}
