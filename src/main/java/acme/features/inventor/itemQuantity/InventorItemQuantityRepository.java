package acme.features.inventor.itemQuantity;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Item;
import acme.entities.ItemQuantity;
import acme.entities.ItemType;
import acme.entities.MoneyExchangeCache;
import acme.entities.Toolkit;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorItemQuantityRepository extends AbstractRepository{

	@Query("select i from ItemQuantity i where i.toolkit.id = :toolkitId and i.item.type=:type")
	Collection<ItemQuantity> findManyItemQuantitiesByToolkitIdAndType(int toolkitId, ItemType type);
	
	@Query("select i from Item i where i.published = true and i.type = :type and i not in (select iq.item from ItemQuantity iq where iq.toolkit.id = :toolkitId)")
	Collection<Item> findManyItemsNotInToolkit(int toolkitId, ItemType type);
	
	@Query("select i from ItemQuantity i where i.id = :quantityId")
	ItemQuantity findItemQuantityById(int quantityId);

	@Query("select i from Item i where i.id = :id")
	Item findOneItemById(int id);
	
	@Query("select t from Toolkit t where t.id = :id")
	Toolkit findOneToolkitById(int id);
	
	@Query("select s.acceptedCurrencies from SystemConfiguration s")
	String findAvailableCurrencies();
	
	@Query("select s.systemCurrency from SystemConfiguration s")
	String findBaseCurrency();

	@Query("select c from MoneyExchangeCache c where c.source = :sourceCurrency and c.target=:targetCurrency")
	Optional<MoneyExchangeCache> findCacheBySourceAndTarget(String sourceCurrency, String targetCurrency);
}
