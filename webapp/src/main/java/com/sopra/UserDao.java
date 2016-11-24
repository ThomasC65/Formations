package com.sopra;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.User;

@Stateless
public class UserDao {
	@PersistenceContext(name = "training2")
	EntityManager em;

	public List<User> findAllUsers() {
		return em.createQuery("from User u", User.class).getResultList();
	}

	public User findUserById(Integer idUser) {
		return em.find(User.class, idUser);
	}

	public User createOrUpdateUser(User user) {
		User existing = findUserById(user.getIdUser());

		if (existing != null) {
			existing.setName(user.getName());
			existing.setLastname(user.getLastname());
			existing.setAgence(user.getAgence());

			return existing;

		} else {
			user.setIdUser(0);
			em.persist(user);

			return user;
		}
	}

	public void deleteUser(Integer idUser) {
		User user = findUserById(idUser);
		if (user != null) {
			em.remove(user);
		}
	}

}
