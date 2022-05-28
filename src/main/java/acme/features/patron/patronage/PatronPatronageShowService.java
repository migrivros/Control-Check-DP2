package acme.features.patron.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Patron;

@Service
public class PatronPatronageShowService implements AbstractShowService<Patron, Patronage> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected PatronPatronageRepository repository;

	// AbstractShowService<Patron, Patronage> interface ---------------------------


	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int id;
		Patronage patronage;
		Patron patron;
		Principal principal;

		id = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(id);
		patron = patronage.getPatron();
		principal = request.getPrincipal();
		result = patron.getUserAccount().getId() == principal.getAccountId();

		return result;
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
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("inventors", this.repository.findInventors());
		model.setAttribute("inventId", entity.getInventor().getId());
		
		if(entity.isPublished()) {
			model.setAttribute("status", entity.getStatus());
		}
		
		request.unbind(entity, model,"status","code", "legalStuff", "budget", "published", "creationMoment", 
			"startDate", "endDate", "moreInfo", "inventor.userAccount.username", "inventor.userAccount.identity.name",
			 "inventor.userAccount.identity.surname",  "inventor.userAccount.identity.email","patron.company", "patron.statement",
			"patron.moreInfo", "patron.userAccount.username", "patron.userAccount.identity.name",
			"patron.userAccount.identity.surname", "patron.userAccount.identity.email");
		
	}
}
