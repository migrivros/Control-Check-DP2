package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Chimpum;
import acme.entities.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorChimpumDeleteService implements AbstractDeleteService<Inventor, Chimpum> {

	
	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;

	// AbstractDeleteService<Inventor, Item> interface -------------------------


	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		final boolean result;
		
		int id;
		Item item;
		Inventor inventor;
		Principal principal;

		id = request.getModel().getInteger("id");
		item = this.repository.findOneComponentByChimpumId(id);
		inventor = item.getInventor();
		principal = request.getPrincipal();
		result = (inventor.getUserAccount().getId() == principal.getAccountId() && !item.isPublished());

		return result;
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
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "title", "budget","description","startDate","endDate", "link");
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		final int id = request.getModel().getInteger("id");
		final Item item = this.repository.findOneComponentByChimpumId(id);
		
		model.setAttribute("isPublishedItem", item.isPublished());
		request.unbind(entity, model, "title", "budget","description","startDate","endDate", "link");
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		final int id = request.getModel().getInteger("id");
		final Item item = this.repository.findOneComponentByChimpumId(id);
		item.setChimpum(null);
		this.repository.save(item);

		this.repository.delete(entity);
	}

}
