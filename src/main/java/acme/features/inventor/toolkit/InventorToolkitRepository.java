package acme.features.inventor.toolkit;

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
import acme.roles.Inventor;

@Repository
public interface InventorToolkitRepository extends AbstractRepository {
	
	@Query("select t from Toolkit t where t.id = :id")
	Toolkit findOneToolkitById(int id);

	@Query("select distinct iq.toolkit from ItemQuantity iq, Item i where iq.item.id=i.id and i.inventor.id = :inventorId")
	Collection<Toolkit> findManyToolkitsByInventorId(int inventorId);
	
	@Query("select iq from ItemQuantity iq where iq.toolkit.id = :masterId")
    Collection<ItemQuantity> findItemQuantitiesOfToolkit(int masterId);
	

	@Query("select i from Inventor i where i.id = :inventorId")
	Inventor findInventorById(int inventorId);
	
	@Query("select i from Item i where i.id = :itemId")
	Item findItemById(int itemId);
	
	@Query("select i from Item i where i.inventor = :inventorId")
	Collection<Item> findItemsByInventor(int inventorId);
	
	@Query("select t from Toolkit t where t.inventor.id = :inventorId")
	Collection<Toolkit> findToolkitsByInventorId(int inventorId);
	
	@Query("select i from Item i")
	Collection<Item> findAllItems();
	
	@Query("select i from Item i where i.type = :type and published=true")
	Collection<Item> findAllItemsByType(ItemType type);
	
	@Query("select t from Toolkit t where t.code = :code")
	Toolkit findOneToolkitByCode(String code);

	@Query("select s.systemCurrency from SystemConfiguration s")
	String findBaseCurrency();
	
	@Query("select c from MoneyExchangeCache c where c.source = :sourceCurrency and c.target=:targetCurrency")
	Optional<MoneyExchangeCache> findCacheBySourceAndTarget(String sourceCurrency, String targetCurrency);

}
