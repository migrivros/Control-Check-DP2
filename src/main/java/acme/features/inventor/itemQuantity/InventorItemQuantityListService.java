package acme.features.inventor.itemQuantity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.ItemQuantity;
import acme.entities.ItemType;
import acme.entities.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;


@Service
public class InventorItemQuantityListService implements AbstractListService<Inventor, ItemQuantity>{

	@Autowired
	protected InventorItemQuantityRepository repository;
	
	
	@Override
	public boolean authorise(final Request<ItemQuantity> request) {
		assert request != null;
		
		final boolean result;
		int id;
		int inventorId;
		Toolkit toolkit;
		final Principal principal;

		id = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(id);
		principal = request.getPrincipal();
		inventorId=principal.getActiveRoleId();
		


		result = toolkit.getInventor().getId()==inventorId;
		return result;
	}

	@Override
	public Collection<ItemQuantity> findMany(final Request<ItemQuantity> request) {
		int toolkitId;
		ItemType type;
		
		toolkitId = request.getModel().getInteger("masterId");
		type =ItemType.valueOf((String)request.getModel().getAttribute("type")); 
		
		return this.repository.findManyItemQuantitiesByToolkitIdAndType(toolkitId, type); //Aqui tengo ya las lineas de la tabla quantities agrupadas por cada toolkit

	}

	@Override
	public void unbind(final Request<ItemQuantity> request, final Collection<ItemQuantity> entities, final Model model) {
		assert request != null;
		assert !CollectionHelper.someNull(entities);
		assert model != null;

		int masterId;
		Toolkit toolkit;
		String type;

		masterId = request.getModel().getInteger("masterId");
		toolkit = this.repository.findOneToolkitById(masterId);
		type = request.getModel().getAttribute("type").toString();
		model.setAttribute("draftMode", toolkit.isDraftMode());
		model.setAttribute("type", type);
		model.setAttribute("masterId", masterId);
	}

	@Override
	public void unbind(final Request<ItemQuantity> request, final ItemQuantity entity, final Model model) {
		assert request != null; 
		assert entity != null; 
		assert model != null; 

		request.unbind(entity, model,"quantity", "item.name", "item.type","item.code", "item.technology", "item.retailPrice","toolkit.draftMode"); 
		
	}

}