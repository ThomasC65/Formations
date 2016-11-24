package com.sopra;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.User;

@Stateless
public class TestDao {
	@PersistenceContext(unitName = "training2")
	EntityManager em;

	public List<User> test() {
		return em.createQuery("from User u", User.class).getResultList();
	}
}
