package acme.features.patron.patronage;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Patronage;
import acme.entities.PatronageStatus;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.roles.Patron;

@Service
public class PatronPatronageCreateService implements AbstractCreateService<Patron, Patronage>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronPatronageRepository repository;

	// AbstractCreateService<Patron, Patronage> interface -------------------------
			
	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date currentMoment;
		Integer inventorId;		
		
		currentMoment = new Date(System.currentTimeMillis() - 1);
		entity.setCreationMoment(currentMoment);
		entity.setStatus(PatronageStatus.PROPOSED);
		entity.setPublished(false);
		
		inventorId = Integer.valueOf(request.getModel().getAttribute("inventorId").toString());
		entity.setInventor(this.repository.findInventorById(inventorId));

		request.bind(entity, errors, "code", "legalStuff", "budget", "startDate", "endDate","moreInfo");
		
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "status" ,"code", "legalStuff", "budget", "startDate","endDate","moreInfo","published");
		model.setAttribute("inventors", this.repository.findInventors());
		
	}

	@Override
	public Patronage instantiate(final Request<Patronage> request) {
		
		assert request != null;

		final Patronage result = new Patronage();
		Date moment;
		Date startDate;
		Date endDate;
		int principalId;
		final Money budget;

		principalId = request.getPrincipal().getActiveRoleId();
		final Patron patron = this.repository.findOnePatronById(principalId);

		moment = new Date(System.currentTimeMillis() - 1);
		
		final Calendar cal = Calendar.getInstance();
		cal.setTime(moment);
		cal.add(Calendar.MONTH, 2);
		startDate = cal.getTime();
		
		cal.add(Calendar.MONTH, 2);
		endDate = cal.getTime();
		
		budget = new Money();
		budget.setAmount(1.0);
		budget.setCurrency("EUR");
		
		

		result.setPatron(patron);
		result.setCreationMoment(moment);
		result.setStatus(PatronageStatus.PROPOSED);
		result.setCode("");
		result.setLegalStuff("");
		result.setBudget(budget);
		result.setStartDate(startDate);
		result.setEndDate(endDate);
		result.setMoreInfo("");

		return result;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {		
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			Patronage existing;

			existing = this.repository.findOnePatronageByCode(entity.getCode());
			errors.state(request, existing == null, "code", "patron.patronage.form.error.duplicated");
		}
		
		
		
		if (!errors.hasErrors("startDate")) {
			errors.state(request, entity.getStartDate().after(entity.getCreationMoment()), "startDate", "patron.patronage.form.error.past-start-date");
		}
		if(!errors.hasErrors("startDate")) {
			final Date oneMonthAfterCreationDate = DateUtils.addMonths(entity.getCreationMoment(), 1);
			errors.state(request,entity.getStartDate().after(oneMonthAfterCreationDate), "startDate", "patron.patronage.form.error.too-close");
		}
		
		
		if(!errors.hasErrors("endDate")) {
			errors.state(request, entity.getEndDate().after(entity.getCreationMoment()), "endDate", "patron.patronage.form.error.past-end-date");
		}
		if(!errors.hasErrors("endDate")) {	
			errors.state(request, entity.getEndDate().after(entity.getStartDate()), "endDate", "patron.patronage.form.error.end-date-previous-to-start-date");
		}
		if(!errors.hasErrors("endDate")) {
			final Date oneMonthAfterStartDate=DateUtils.addMonths(entity.getStartDate(), 1);
			errors.state(request,entity.getEndDate().after(oneMonthAfterStartDate), "endDate", "patron.patronage.form.error.insufficient-duration");
		}
		
		
		if (!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget().getAmount() >= 1, "budget", "patron.patronage.form.error.minimum-budget");
		}
		
		if(!errors.hasErrors("legalStuff")) {
			//Completar con el detector de spam
		}
		
	}

	@Override
	public void create(final Request<Patronage> request, final Patronage entity) {
		
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
