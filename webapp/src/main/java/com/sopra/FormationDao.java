package com.sopra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Formation;

public class FormationDao {

	@PersistenceContext(name = "training2")
	EntityManager em;

	public List<Formation> findAllFormatons() {
		return em.createQuery("from Formation f", Formation.class).getResultList();
	}

	public Formation findFormationById(Integer idFormation) {
		return em.find(Formation.class, idFormation);
	}

	public Formation createOrUpdate(Formation formation) {
		Formation existing = findFormationById(formation.getIdFormation());

		if (existing != null) {
			existing.setNbjours(formation.getNbjours());
			existing.setFormation(formation.getFormation());
			existing.setLieuFormation(formation.getLieuFormation());
			existing.setOrganisme(formation.getOrganisme());
			existing.setDateReel(formation.getDateReel());
			existing.setDateAttendue(formation.getDateAttendue());

			return existing;

		} else {
			formation.setIdFormation(0);
			em.persist(formation);

			return formation;
		}
	}

	public void deleteFormation(Integer idFormation) {
		Formation formation = findFormationById(idFormation);
		if (formation != null) {
			em.remove(formation);
		}
	}

}
