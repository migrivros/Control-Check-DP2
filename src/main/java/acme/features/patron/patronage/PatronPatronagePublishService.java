package acme.features.patron.patronage;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;
import acme.roles.Patron;

@Service
public class PatronPatronagePublishService implements AbstractUpdateService<Patron, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronPatronageRepository repository;

	// AbstractUpdateService<Patron, Patronage> -------------------------------------

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int patronageId;
		Patronage patronage;

		patronageId = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(patronageId);
		result = (patronage != null && request.isPrincipal(patronage.getPatron()) && !patronage.isPublished());

		return result;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		Integer inventorId;
		Inventor inventor;
		
		inventorId = Integer.valueOf(request.getModel().getAttribute("inventorId").toString());
		inventor = this.repository.findInventorById(inventorId);
		
		entity.setInventor(inventor);


		request.bind(entity, errors, "legalStuff", "budget", "startDate", "endDate","moreInfo");
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "legalStuff", "budget","creationMoment", "startDate", "endDate", "moreInfo","published");
		model.setAttribute("inventors", this.repository.findInventors());
		model.setAttribute("inventId", entity.getInventor().getId());
	}

	@Override
	public Patronage findOne(final Request<Patronage> request) {
		assert request != null;
		
		Patronage result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOnePatronageById(id);

		return result;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("code")) {
			final String oldCode = this.repository.findOnePatronageById(entity.getId()).getCode();
			errors.state(request, oldCode.equals(entity.getCode()) , "code", "patron.patronage.form.error.unmodifiable-code");
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
	public void update(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;
		
		entity.setPublished(true);
		this.repository.save(entity);
	}

}
