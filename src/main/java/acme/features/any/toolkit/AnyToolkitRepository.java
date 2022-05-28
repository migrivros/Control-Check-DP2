/*
 * AnyJobRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.toolkit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.ItemQuantity;
import acme.entities.MoneyExchangeCache;
import acme.entities.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyToolkitRepository extends AbstractRepository {

	@Query("select t from Toolkit t where t.id = :id")
	Toolkit findOneToolkitById(int id);

	@Query("select t from Toolkit t where t.draftMode = false")
	Collection<Toolkit> findManyToolkitsByAvailability();
	
	@Query("select iq from ItemQuantity iq where iq.toolkit.id = :masterId")
    Collection<ItemQuantity> findItemQuantitiesOfToolkit(int masterId);
	
	@Query("select iq from ItemQuantity iq where iq.toolkit.id = :masterId")
    Collection<ItemQuantity> findToolkitByItemName(int masterId);
	
	@Query("select c from MoneyExchangeCache c where c.source = :sourceCurrency and c.target=:targetCurrency")
	Optional<MoneyExchangeCache> findCacheBySourceAndTarget(String sourceCurrency, String targetCurrency);
	
	@Query("select s.systemCurrency from SystemConfiguration s")
	String findBaseCurrency();

}
