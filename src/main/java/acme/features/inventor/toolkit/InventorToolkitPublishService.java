package acme.features.inventor.toolkit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Item;
import acme.entities.ItemQuantity;
import acme.entities.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.ToolkitHelper;
import acme.roles.Inventor;

@Service	
public class InventorToolkitPublishService implements AbstractUpdateService<Inventor, Toolkit>{

	
	@Autowired
	protected InventorToolkitRepository repository;
	
	
	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Toolkit toolkit = this.repository.findOneToolkitById(id);
		
		return request.isPrincipal(toolkit.getInventor()) && toolkit.isDraftMode();
	}

	@Override
	public void bind(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "title", "description", "assemblyNotes", "moreInfo");
		
	}

	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "title", "description", "assemblyNotes", "moreInfo", "draftMode");
		final Collection<ItemQuantity> prices = this.repository.findItemQuantitiesOfToolkit(entity.getId());
		
		model.setAttribute("retailPrice", ToolkitHelper.calculateTotalPrice(prices));
		
	}

	@Override
	public Toolkit findOne(final Request<Toolkit> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		return this.repository.findOneToolkitById(id);
	}

	@Override
	public void validate(final Request<Toolkit> request, final Toolkit entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("code")) {
			final Toolkit alreadyExists = this.repository.findOneToolkitByCode(entity.getCode());
			errors.state(request, alreadyExists == null || alreadyExists.getId() == entity.getId(), "code", "inventor.toolkit.form.error.duplicated");
		}
		
		final Collection<ItemQuantity> quantities = this.repository.findItemQuantitiesOfToolkit(entity.getId());
		final long types = quantities.stream().map(ItemQuantity::getItem).map(Item::getType).distinct().count();
		errors.state(request, types == 2, "*", "inventor.toolkit.form.error.invalid-items");
		
	}

	@Override
	public void update(final Request<Toolkit> request, final Toolkit entity) {
		assert request != null;
		assert entity != null;
		
		entity.setDraftMode(false);
		this.repository.save(entity);
		
	}

}
