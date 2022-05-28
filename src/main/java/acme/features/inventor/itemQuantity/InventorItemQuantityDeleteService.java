package acme.features.inventor.itemQuantity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.ItemQuantity;
import acme.entities.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorItemQuantityDeleteService implements AbstractDeleteService<Inventor, ItemQuantity>{

	@Autowired
	InventorItemQuantityRepository repository;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;

		boolean checkPublished = true;
		final int id;
		Toolkit toolkit;
		
		id = request.getModel().getInteger("id");
		toolkit = this.repository.findItemQuantityById(id).getToolkit();
		checkPublished = (toolkit != null && toolkit.isDraftMode()) && request.isPrincipal(toolkit.getInventor());
		
		return checkPublished;
	}

	@Override
	public void bind(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "quantity");
	}

	@Override
	public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "quantity", "item.type", "item.name", "item.technology", "item.description", "item.retailPrice", "item.moreInfo");
		model.setAttribute("draftMode", entity.getToolkit().isDraftMode());
		model.setAttribute("itemId", entity.getItem().getId());
		
	}

	@Override
	public ItemQuantity findOne(final Request<ItemQuantity> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");

		return this.repository.findItemQuantityById(id);

	}

	@Override
	public void validate(final Request<ItemQuantity> request, final ItemQuantity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<ItemQuantity> request, final ItemQuantity entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
	}

}
