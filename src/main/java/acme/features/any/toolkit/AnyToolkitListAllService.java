/*
 * AnyJobListService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.toolkit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Item;
import acme.entities.ItemQuantity;
import acme.entities.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyToolkitListAllService implements AbstractListService<Any, Toolkit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyToolkitRepository repository;

	// AbstractListService<Any, Toolkit>  interface -------------------------


	@Override
	public boolean authorise(final Request<Toolkit> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Collection<Toolkit> findMany(final Request<Toolkit> request) {
		assert request != null;

		Collection<Toolkit> result;

		result = this.repository.findManyToolkitsByAvailability();

		return result;
	}
	
	@Override
	public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		final StringBuilder payload=new StringBuilder();
		final Collection<ItemQuantity> quantities = this.repository.findItemQuantitiesOfToolkit(entity.getId());
		request.unbind(entity, model, "code", "title", "description");
	    for(final ItemQuantity iq: quantities) {
	    	final Item i= iq.getItem();
	    	payload.append(String.format(
                "%s;",
                i.getName()));

	    }
	    model.setAttribute("payload", payload.toString());
	
	}

}
