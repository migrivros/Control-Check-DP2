
package acme.features.inventor.patronage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPatronageShowService implements AbstractShowService<Inventor, Patronage> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorPatronageRepository repository;

	// AbstractShowService<Inventor, Patronage> interface ---------------------------


	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		boolean result;
		int id;
		Patronage patronage;
		Inventor inventor;
		Principal principal;

		id = request.getModel().getInteger("id");
		patronage = this.repository.findOnePatronageById(id);
		inventor = patronage.getInventor();
		principal = request.getPrincipal();
		result = inventor.getUserAccount().getId() == principal.getAccountId() && patronage.isPublished();

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

		request.unbind(entity, model, "status", "code", "legalStuff", "budget", "creationMoment", "startDate", "endDate", "moreInfo", "patron.company", "patron.statement", "patron.moreInfo", "patron.userAccount.username",
			"patron.userAccount.identity.name", "patron.userAccount.identity.surname", "patron.userAccount.identity.email");
	}

}
