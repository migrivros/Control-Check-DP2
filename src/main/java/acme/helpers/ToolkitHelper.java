package acme.helpers;

import java.util.Collection;

import org.springframework.web.client.RestTemplate;

import acme.components.ExchangeRate;
import acme.entities.ItemQuantity;
import acme.framework.datatypes.Money;

public class ToolkitHelper {

	private ToolkitHelper() {};
	
	public static Money calculateTotalPrice(final Collection<ItemQuantity> quantities) {
		if(quantities.isEmpty()) return null;
		final Money money = new Money();
		money.setCurrency("EUR"); //Default Currency
		Double sum=0.;
		for(final ItemQuantity quantity: quantities) {
			if(money.getCurrency() == null) money.setCurrency(quantity.getItem().getRetailPrice().getCurrency());
			
			if(!money.getCurrency().equals(quantity.getItem().getRetailPrice().getCurrency())) {
				final Money ex = ToolkitHelper.exchange(quantity.getItem().getRetailPrice(), money.getCurrency());
				sum += ex.getAmount() * quantity.getQuantity();
			}else {
				sum += quantity.getItem().getRetailPrice().getAmount() * quantity.getQuantity();
			}
			
		}
		money.setAmount(sum);
		return money;
	}
	
	public static Money exchange(final Money source, final String to) {
		
		final RestTemplate api = new RestTemplate();
		
		final String from = source.getCurrency();
		
		final ExchangeRate response = api.getForObject("https://api.exchangerate.host/latest?base={0}&symbols={1}", ExchangeRate.class, from, to);
		
		assert response != null;
		
		final Money money = new Money();
		
		money.setCurrency(to);
		money.setAmount(source.getAmount()*response.getRates().get(to));
		
		return money;
		
	}

}
