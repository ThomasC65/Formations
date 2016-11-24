package request;

import java.util.List;

import entities.User;
import jpa.EmFactory;

public class UserQuery {

	public User getOrInsertUserInDb(User user) {

		return EmFactory.transaction(em -> {
			List<User> listUser = em
					.createQuery(
							"SELECT u from User u WHERE u.name=:name AND u.lastname=:lastname AND u.agence=:agence",
							User.class)
					.setParameter("name", user.getName()).setParameter("lastname", user.getLastname())
					.setParameter("agence", user.getAgence()).getResultList();

			if (listUser.isEmpty()) {
				em.persist(user);
				return user;
			} else {
				return listUser.get(0);
			}
		});
	}
}
