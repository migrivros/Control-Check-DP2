/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.moneyExchange;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.CalculateMoneyExchange;
import acme.entities.MoneyExchangeCache;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractPerformService;

@Service
public class AuthenticatedMoneyExchangePerformService implements AbstractPerformService<Authenticated, MoneyExchange> {

	@Autowired
	protected AuthenticatedMoneyExchangeRepository repository;

	// AbstractPerformService<Authenticated, ExchangeRecord> interface ---------


	@Override
	public boolean authorise(final Request<MoneyExchange> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<MoneyExchange> request, final MoneyExchange entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "source", "targetCurrency", "date", "target");
	}

	@Override
	public void unbind(final Request<MoneyExchange> request, final MoneyExchange entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "source", "targetCurrency", "date", "target");
	}

	@Override
	public MoneyExchange instantiate(final Request<MoneyExchange> request) {
		assert request != null;

		MoneyExchange result;

		result = new MoneyExchange();

		return result;
	}

	@Override
	public void validate(final Request<MoneyExchange> request, final MoneyExchange entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final String money = entity.getTargetCurrency();
		final boolean availableCurrency = this.validateAvailableCurrencyRetailPrice(money);
		errors.state(request, availableCurrency, "targetCurrency", "authenticated.money-exchange.form.error.currency-not-available");
	}

	public boolean validateAvailableCurrencyRetailPrice(final String targetCurrency) {

		final String currencies = this.repository.findAvailableCurrencies();
		final List<Object> listOfAvailableCurrencies = Arrays.asList((Object[]) currencies.split(";"));

		return listOfAvailableCurrencies.contains(targetCurrency);
	}

	@Override
	public void perform(final Request<MoneyExchange> request, final MoneyExchange entity, final Errors errors) {
		assert request != null;
		assert entity != null;

		Money source, target;
		String targetCurrency, sourceCurrency;
		Date date;
		Calendar calendar;
		MoneyExchange exchange;
		MoneyExchangeCache cache;

		source = request.getModel().getAttribute("source", Money.class);
		targetCurrency = request.getModel().getAttribute("targetCurrency", String.class);
		sourceCurrency = source.getCurrency();
		calendar = Calendar.getInstance();

		final Optional<MoneyExchangeCache> opt = this.repository.findCacheBySourceAndTarget(sourceCurrency, targetCurrency);
		if (opt.isPresent()) {
			cache = opt.get();
			if (Boolean.TRUE.equals(CalculateMoneyExchange.checkCache(cache))) {
				exchange = CalculateMoneyExchange.calculateMoneyExchangeFromCache(source, targetCurrency, cache);
				target = exchange.getTarget();
				entity.setTarget(target);
				date = exchange.getDate();
				entity.setDate(date);
			} else {
				exchange = CalculateMoneyExchange.computeMoneyExchange(source, targetCurrency);
				errors.state(request, exchange != null, "*", "authenticated.money-exchange.form.label.api-error");
				if (exchange == null) {
					entity.setTarget(null);
					entity.setDate(null);
				} else {
					target = exchange.getTarget();
					entity.setTarget(target);
					date = exchange.getDate();
					entity.setDate(date);

					calendar.setTime(date);
					cache.setDate(calendar);
					cache.setRate(exchange.getRate());
					this.repository.save(cache);
				}
			}
		} else {
			exchange = CalculateMoneyExchange.computeMoneyExchange(source, targetCurrency);
			errors.state(request, exchange != null, "*", "authenticated.money-exchange.form.label.api-error");
			if (exchange == null) {
				entity.setTarget(null);
				entity.setDate(null);
			} else {
				target = exchange.getTarget();
				entity.setTarget(target);
				date = exchange.getDate();
				entity.setDate(date);

				calendar.setTime(date);
				cache = new MoneyExchangeCache();
				cache.setDate(calendar);
				cache.setRate(exchange.getRate());
				cache.setSource(sourceCurrency);
				cache.setTarget(targetCurrency);
				this.repository.save(cache);
			}
		}
	}

	// Ancillary methods ------------------------------------------------------
	//	
	//	public Boolean checkCache(final String sourceCurrency, final String targetCurrency) {
	//		final Boolean result;
	//		MoneyExchangeCache cache;
	//		Calendar date;
	//		
	//		final Optional<MoneyExchangeCache> opt = this.repository.findCacheBySourceAndTarget(sourceCurrency, targetCurrency);
	//		if(opt.isPresent()) {
	//			cache = opt.get(); 
	//			date = Calendar.getInstance();
	//			if (date.get(Calendar.DATE) == cache.getDate().get(Calendar.DATE) && date.get(Calendar.MONTH) == cache.getDate().get(Calendar.MONTH) && date.get(Calendar.YEAR) == cache.getDate().get(Calendar.YEAR)) {
	//				result = true;
	//			} else
	//				result = false;
	//		}else result=false;
	//		
	//		return result;
	//	}
	//	
	//	
	//	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
	//		assert source != null;
	//		assert !StringHelper.isBlank(targetCurrency);
	//
	//		MoneyExchange result;
	//		MoneyExchangeCache cache;
	//		RestTemplate api;
	//		ExchangeRate record;
	//		String sourceCurrency;
	//		Double sourceAmount, targetAmount, rate;
	//		Money target;
	//		
	//		final Calendar date = Calendar.getInstance();
	//
	//		try {
	//			api = new RestTemplate();
	//
	//			sourceCurrency = source.getCurrency();
	//			sourceAmount = source.getAmount();
	//
	//			record = api.getForObject( //
	//				"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
	//				ExchangeRate.class, //
	//				sourceCurrency, //
	//				targetCurrency //
	//			);
	//
	//			assert record != null;
	//			rate = record.getRates().get(targetCurrency);
	//			targetAmount = rate * sourceAmount;
	//
	//			target = new Money();
	//			target.setAmount(targetAmount);
	//			target.setCurrency(targetCurrency);
	//			
	//			date.setTime(record.getDate());
	//
	//			result = new MoneyExchange();
	//			result.setSource(source);
	//			result.setTargetCurrency(targetCurrency);
	//			result.setDate(record.getDate());
	//			result.setTarget(target);
	//			result.setRate(rate);
	//			
	//			final Optional<MoneyExchangeCache> opt = this.repository.findCacheBySourceAndTarget(sourceCurrency, targetCurrency);
	//			if(opt.isPresent()) {
	//				cache = opt.get();
	//				cache.setDate(date);
	//				cache.setRate(rate);
	//			}else {
	//				cache = new MoneyExchangeCache();
	//				cache.setSource(sourceCurrency);
	//				cache.setTarget(targetCurrency);
	//				cache.setDate(date);
	//				cache.setRate(rate);
	//			}
	//
	//			this.repository.save(cache);
	//
	//		} catch (final Throwable oops) {
	//			result = null;
	//		}
	//
	//		return result;
	//	}
	//	
	//	
	//	public MoneyExchange calculateMoneyExchangeFromCache(final Money source, final String targetCurrency) {
	//		assert source != null;
	//		assert !StringHelper.isBlank(targetCurrency);
	//
	//		MoneyExchange result;
	//		MoneyExchangeCache cache;
	//		String sourceCurrency;
	//		Double sourceAmount, targetAmount, rate;
	//		Money target;
	//
	//		sourceCurrency = source.getCurrency();
	//		sourceAmount = source.getAmount();
	//		
	//		final Optional<MoneyExchangeCache> opt = this.repository.findCacheBySourceAndTarget(sourceCurrency, targetCurrency);
	//		if(opt.isPresent()) {
	//			cache = opt.get();
	//			
	//			assert cache != null;
	//			rate = cache.getRate();
	//			targetAmount = rate * sourceAmount;
	//			
	//			target = new Money();
	//			target.setAmount(targetAmount);
	//			target.setCurrency(targetCurrency);
	//			
	//			result = new MoneyExchange();
	//			result.setSource(source);
	//			result.setTargetCurrency(targetCurrency);
	//			result.setDate(cache.getDate().getTime());
	//			result.setTarget(target);
	//			result.setRate(rate);
	//			
	//			return result;
	//		}
	//		return null;
	//	}

}
