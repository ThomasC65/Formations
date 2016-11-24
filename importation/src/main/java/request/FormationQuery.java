package request;

import java.util.List;

import entities.Formation;
import jpa.EmFactory;

public class FormationQuery {

	public Formation getOrInsertFormationInDb(Formation formation) {

		return EmFactory.transaction(em -> {
			List<Formation> listFormation = em
					.createQuery(
							"SELECT f from Formation f WHERE f.nbjours=:nbjours AND f.formation=:formation AND f.lieuFormation=:lieuFormation AND f.organisme=:organisme AND f.dateReel=:dateReel AND f.dateAttendue=:dateAttendue",
							Formation.class)
					.setParameter("nbjours", formation.getNbjours()).setParameter("formation", formation.getFormation())
					.setParameter("lieuFormation", formation.getLieuFormation())
					.setParameter("organisme", formation.getOrganisme())
					.setParameter("dateReel", formation.getDateReel())
					.setParameter("dateAttendue", formation.getDateAttendue()).getResultList();

			if (listFormation.isEmpty()) {
				em.persist(formation);
				return formation;
			} else {

				return listFormation.get(0);

			}

		});
	}

}
