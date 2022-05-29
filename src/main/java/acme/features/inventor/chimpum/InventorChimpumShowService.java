
package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Chimpum;
import acme.entities.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorChimpumShowService implements AbstractShowService<Inventor, Chimpum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;
	

	// AbstractShowService<Inventor, Chimpum> interface ---------------------------


	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;

		return true;
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;

		Chimpum result;
		int id;
	
		id = request.getModel().getInteger("id");
		result = this.repository.findOneChimpumById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final int id = request.getModel().getInteger("id");
		final Item item = this.repository.findOneComponentByChimpumId(id);

		request.unbind(entity, model, "title", "code", "description", "budget", "startDate", "endDate", "link");
		model.setAttribute("ArtefactName", item.getName());
		model.setAttribute("ArtefactCode", item.getCode());
		model.setAttribute("ArtefactTechnology", item.getTechnology());
		model.setAttribute("ArtefactDescription", item.getDescription());
		model.setAttribute("ArtefactRetailPrice", item.getRetailPrice());
		model.setAttribute("ArtefactMoreInfo", item.getMoreInfo());
		
	}

}
