
package acme.features.inventor.chimpum;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Chimpum;
import acme.entities.Item;
import acme.entities.ItemType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumCreateService implements AbstractCreateService<Inventor, Chimpum> {

	@Autowired
	protected InventorChimpumRepository	repository;

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		boolean result;
		int id;
		Item item;
		Inventor inventor;
		Principal principal;

		id = request.getModel().getInteger("itemId");
		item = this.repository.findItemById(id);
		inventor = item.getInventor();
		principal = request.getPrincipal();
		result = (inventor.getUserAccount().getId() == principal.getAccountId() && !item.isPublished() && item.getType() == ItemType.COMPONENT);

		return result;
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		assert request != null;
		
		final Chimpum result = new Chimpum();
		
		final Date moment = new Date(System.currentTimeMillis() - 1);

 		result.setCreationMoment(moment);
        result.setPattern("");
        result.setTitle("");
        result.setDescription("");
		
		return result;
	}

	
	public boolean validateAvailableCurrencyBudget(final Money retailPrice) {
		
		final String currencies = this.repository.findAvailableCurrencies();
		final List<Object> listOfAvailableCurrencies = Arrays.asList((Object[])currencies.split(";"));

		
		return listOfAvailableCurrencies.contains(retailPrice.getCurrency());		
	}
	

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "pattern", "title", "budget","description","startDate","endDate", "link");

	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("pattern")) {
			Chimpum existing;

			existing = this.repository.findChimpumByPattern(entity.getPattern());
			errors.state(request, existing == null, "pattern", "inventor.chimpum.form.error.duplicated");
		}
		
		if(entity.getStartDate()!=null) {
			if (!errors.hasErrors("startDate")) {
				errors.state(request, entity.getStartDate().after(entity.getCreationMoment()), "startDate", "inventor.chimpum.form.error.past-start-date");
			}
			if(!errors.hasErrors("startDate")) {
				final Date oneMonthAfterCreationDate = DateUtils.addMonths(entity.getCreationMoment(), 1);
				errors.state(request,entity.getStartDate().after(oneMonthAfterCreationDate), "startDate", "inventor.chimpum.form.error.too-close");
			}
			if(!errors.hasErrors("endDate")) {	
				errors.state(request, entity.getEndDate().after(entity.getStartDate()), "endDate", "inventor.chimpum.form.error.end-date-previous-to-start-date");
			}
			if(!errors.hasErrors("endDate")) {
				final Date oneWeekAfterStartDate=DateUtils.addDays(entity.getStartDate(), 7);
				errors.state(request,entity.getEndDate().after(oneWeekAfterStartDate), "endDate", "inventor.chimpum.form.error.insufficient-duration");
			}
		}
		
		if(!errors.hasErrors("endDate")) {
			errors.state(request, entity.getEndDate().after(entity.getCreationMoment()), "endDate", "inventor.chimpum.form.error.past-end-date");
		}
		
		
		if(!errors.hasErrors("budget")){
			final Money budget = entity.getBudget();
			final boolean availableCurrency = this.validateAvailableCurrencyBudget(budget);
			errors.state(request, availableCurrency, "budget", "inventor.chimpum.form.error.retail-price-currency-not-available");
			errors.state(request, entity.getBudget().getAmount() > 0.0, "budget", "inventor.chimpum.form.error.minimum-budget");
		}
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		model.setAttribute("itemId", request.getModel().getAttribute("itemId"));

		request.unbind(entity, model, "pattern", "title", "budget","description","startDate","endDate", "link");
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;

		final Integer itemId = Integer.valueOf(request.getModel().getAttribute("itemId").toString()); 
		final Item item = this.repository.findItemById(itemId);
		
		final Date moment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(moment);
		
		this.repository.save(entity);
		
		item.setChimpum(entity);
		this.repository.save(item);
	}

}
