
package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Chimpum;
import acme.entities.Item;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorChimpumRepository extends AbstractRepository {

	@Query("select distinct c from Chimpum c, Item i where c.id  = i.chimpum.id and i.inventor.id  = :inventorId")
	Collection<Chimpum> findMyChimpums(int inventorId);
	
	@Query("select c from Chimpum c where c.id = :id")
	Chimpum findOneChimpumById(int id);
	
	@Query("select i from Item i where i.type = 1 and i.chimpum.id = :id")
	Item findOneComponentByChimpumId(int id);
	
	@Query("select c from Chimpum c where c.pattern = :pattern")
	Chimpum findChimpumByPattern(String pattern);
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	
	@Query("select s.acceptedCurrencies from SystemConfiguration s")
	String findAvailableCurrencies();
	
	/*
	@Query("select s.systemCurrency from SystemConfiguration s")
	String findBaseCurrency();

	@Query("select c from MoneyExchangeCache c where c.source = :sourceCurrency and c.target=:targetCurrency")
	Optional<MoneyExchangeCache> findCacheBySourceAndTarget(String sourceCurrency, String targetCurrency);
	*/

}
