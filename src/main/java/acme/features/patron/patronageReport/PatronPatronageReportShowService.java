package acme.features.patron.patronageReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Patron;

@Service
public class PatronPatronageReportShowService implements AbstractShowService<Patron, PatronageReport> {
	
	// Internal state ---------------------------------------------------------
	
	@Autowired	
	protected PatronPatronageReportRepository repository;

	// AbstractListService<Inventor, PatronageReport> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		assert request != null;

		boolean result;
		int id;
		PatronageReport report;
		final Patron patron;
		Principal principal;

		id = request.getModel().getInteger("id");
		report = this.repository.findOneById(id);
		patron = report.getPatronage().getPatron();
		principal = request.getPrincipal();
		result = patron.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public PatronageReport findOne(final Request<PatronageReport> request) {
		assert request != null;

		PatronageReport result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}
	
	@Override
	public void unbind(final Request<PatronageReport> request, final PatronageReport entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model,"sequenceNumber","creationMoment", "memorandum", "moreInfo","patronage.legalStuff", "patronage.budget");
	}



}

