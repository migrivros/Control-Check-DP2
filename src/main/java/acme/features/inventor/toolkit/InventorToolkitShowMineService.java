
package acme.features.inventor.toolkit;

import java.util.Calendar;
import java.util.Collection;
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
public class InventorToolkitShowMineService implements AbstractShowService<Inventor, Toolkit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorToolkitRepository repository;

	// AbstractShowService<Inventor, Toolkit> interface ---------------------------


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;

		final boolean result;
		int id;
		int inventorId;
		Toolkit toolkit;
		final Principal principal;

		id = request.getModel().getInteger("id");
		toolkit = this.repository.findOneToolkitById(id);
		principal = request.getPrincipal();
		inventorId=principal.getActiveRoleId();
		


		result = toolkit.getInventor().getId()==inventorId;
		return result;
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		assert request != null;

		Toolkit result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneToolkitById(id);
		final Money totalPrice = this.calculateTotalPrice(result);
		result.setTotalPrice(totalPrice);
		this.repository.save(result);

		return result;
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "title", "description", "assemblyNotes", "totalPrice", "moreInfo", "draftMode");
	}

	private Money calculateTotalPrice(final Toolkit t) {
		final Calendar exchangeDate = Calendar.getInstance();
		final Calendar today = Calendar.getInstance();
		MoneyExchange exchange;
		Money converted;
		final String systemCurrency = this.repository.findBaseCurrency();
		final Money result = new Money();
		result.setCurrency(systemCurrency); //Default Currency
		Double sum = 0.;

		final Collection<ItemQuantity> quantities = this.repository.findItemQuantitiesOfToolkit(t.getId());
		for (final ItemQuantity quantity : quantities) {
			final Item item = quantity.getItem();
			final Money money = item.getRetailPrice();
			if (systemCurrency.equals(money.getCurrency()))
				sum += money.getAmount() * quantity.getQuantity();
			else {
				exchangeDate.setTime(item.getExchangeDate());
				if (exchangeDate.get(Calendar.DATE) == today.get(Calendar.DATE) && exchangeDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) && exchangeDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
					converted = item.getConvertedPrice();
					sum += converted.getAmount() * quantity.getQuantity();
				} else {
					exchange = this.getConversion(money, systemCurrency);
					converted = exchange.getTarget();
					item.setConvertedPrice(converted);
					item.setExchangeDate(exchange.getDate());
					this.repository.save(item);
					sum += converted.getAmount() * quantity.getQuantity();
				}
			}
		}
		result.setAmount(sum);
		return result;
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
