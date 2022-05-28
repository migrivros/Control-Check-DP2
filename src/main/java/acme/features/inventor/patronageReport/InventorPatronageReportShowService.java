
package acme.features.inventor.patronageReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportShowService implements AbstractShowService<Inventor, PatronageReport> {
	
	// Internal state ---------------------------------------------------------
	
	@Autowired	
	protected InventorPatronageReportRepository repository;

	// AbstractListService<Inventor, PatronageReport> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		assert request != null;

		boolean result;
		int id;
		PatronageReport report;
		final Inventor inventor;
		Principal principal;

		id = request.getModel().getInteger("id");
		report = this.repository.findOneById(id);
		inventor = report.getPatronage().getInventor();
		principal = request.getPrincipal();
		result = inventor.getUserAccount().getId() == principal.getAccountId();

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
		
		model.setAttribute("sequenceNumber", entity.getSequenceNumber());
		request.unbind(entity, model,"sequenceNumber","creationMoment", "memorandum", "moreInfo","patronage.legalStuff", "patronage.budget");
	}



	



}

	