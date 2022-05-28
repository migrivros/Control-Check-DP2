
package acme.features.inventor.patronageReport;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.PatronageReport;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorPatronageReportListMineService implements AbstractListService<Inventor, PatronageReport> {
	
	// Internal state ---------------------------------------------------------
	
	@Autowired	
	protected InventorPatronageReportRepository repository;

	// AbstractListService<Inventor, PatronageReport> interface ---------------------------
	
	@Override
	public boolean authorise(final Request<PatronageReport> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<PatronageReport> findMany(final Request<PatronageReport> request) {
		assert request != null;

		Collection<PatronageReport> result;
		Principal principal;

		principal = request.getPrincipal();
		result = this.repository.findManyPatronagesReportByInventorId(principal.getActiveRoleId());

		return result;
	}

	@Override
	public void unbind(Request<PatronageReport> request, PatronageReport entity, Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		model.setAttribute("sequenceNumber", entity.getSequenceNumber());
		request.unbind(entity, model,"sequenceNumber","patronage.code");
		
	}

	

}