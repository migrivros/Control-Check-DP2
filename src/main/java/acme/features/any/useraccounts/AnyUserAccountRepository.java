package acme.features.any.useraccounts;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyUserAccountRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("SELECT DISTINCT ua FROM UserAccount ua join FETCH ua.roles r where ua.enabled = 1 and Administrator not in (select type(r) from UserAccount ua2 join ua2.roles r2 where ua2.id = ua.id) and Anonymous not in (select type(r) from UserAccount ua3 join ua3.roles r3 where ua3.id = ua.id)")
	Collection<UserAccount> findManyUserAccountsByAvailability();
		
}
