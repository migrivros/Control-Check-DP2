package acme.features.inventor.itemQuantity;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.CalculateMoneyExchange;
import acme.entities.Item;
import acme.entities.ItemQuantity;
import acme.entities.MoneyExchangeCache;
import acme.entities.Toolkit;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorItemQuantityShowService implements AbstractShowService<Inventor,ItemQuantity>{

	@Autowired
	InventorItemQuantityRepository repository;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;
		
		final boolean result;
		int id;
		int inventorId;
		Toolkit toolkit;
		final Principal principal;

		id = request.getModel().getInteger("id");
		toolkit = this.repository.findItemQuantityById(id).getToolkit();
		principal = request.getPrincipal();
		inventorId=principal.getActiveRoleId();
		


		result = toolkit.getInventor().getId()==inventorId;
		return result;
	}

	@Override
	public ItemQuantity findOne(final Request<ItemQuantity> request) {
		assert request != null;

		ItemQuantity result;
		Item item;
		int masterId;
		final Money converted;
		Money source;
		String targetCurrency;
		final MoneyExchange exchange;
		Calendar today;
		final Calendar exchangeDate = Calendar.getInstance();

		masterId = request.getModel().getInteger("id");
		result = this.repository.findItemQuantityById(masterId);
		
		item = result.getItem();
		source = item.getRetailPrice();
		targetCurrency = this.repository.findBaseCurrency();
		today = Calendar.getInstance();
		exchangeDate.setTime(item.getExchangeDate());
		
		if (!(item.getRetailPrice().getCurrency().equals(targetCurrency))) {
			exchange = this.getConversion(source, targetCurrency);
			converted = exchange.getTarget();
			item.setExchangeDate(exchange.getDate());
			
		} else {
			converted = source;
			item.setExchangeDate(today.getTime());
		}
		item.setConvertedPrice(converted);
		this.repository.save(item);

		return result;
	}

	@Override
	public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
		assert request != null; 
		assert entity != null; 
		assert model != null; 

		request.unbind(entity, model,"quantity", "item.name", "item.type","item.code","item.description", "item.technology", "item.retailPrice","item.convertedPrice"); 
		model.setAttribute("type", entity.getItem().getType().toString());
		model.setAttribute("draftMode", entity.getToolkit().isDraftMode());
		
	}
	
	public MoneyExchange getConversion(final Money source, final String targetCurrency) {
		MoneyExchangeCache cache;
		MoneyExchange exchange;
		final Calendar date;

		date = Calendar.getInstance();

		final Optional<MoneyExchangeCache> opt = this.repository.findCacheBySourceAndTarget(source.getCurrency(), targetCurrency);
		if (opt.isPresent()) {
			cache = opt.get();
			if (Boolean.TRUE.equals(CalculateMoneyExchange.checkCache(cache)))
				exchange = CalculateMoneyExchange.calculateMoneyExchangeFromCache(source, targetCurrency, cache);
			else {
				exchange = CalculateMoneyExchange.computeMoneyExchange(source, targetCurrency);

				date.setTime(exchange.getDate());
				cache.setDate(date);
				cache.setRate(exchange.getRate());

				this.repository.save(cache);
			}
			return exchange;
		} else {
			exchange = CalculateMoneyExchange.computeMoneyExchange(source, targetCurrency);

			date.setTime(exchange.getDate());
			cache = new MoneyExchangeCache();
			cache.setDate(date);
			cache.setRate(exchange.getRate());
			cache.setSource(source.getCurrency());
			cache.setTarget(targetCurrency);

			this.repository.save(cache);

			return exchange;
		}
	}

}
