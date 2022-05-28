package acme.features.authenticated.moneyExchange;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import acme.entities.MoneyExchangeCache;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedMoneyExchangeRepository extends AbstractRepository {
	
	@Query("select c from MoneyExchangeCache c where c.source = :sourceCurrency and c.target=:targetCurrency")
	Optional<MoneyExchangeCache> findCacheBySourceAndTarget(String sourceCurrency, String targetCurrency);
	
	@Query("select s.acceptedCurrencies from SystemConfiguration s")
	String findAvailableCurrencies();

}
