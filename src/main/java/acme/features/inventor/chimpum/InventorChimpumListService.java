
package acme.features.inventor.chimpum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorChimpumListService implements AbstractListService<Inventor, Chimpum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumRepository repository;

	// AbstractListService<Inventor, Chimpum> interface ---------------------------

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Chimpum> findMany(final Request<Chimpum> request) {
		assert request != null;

		Collection<Chimpum> result;
		
		final int inventorId = request.getPrincipal().getActiveRoleId();
		
		result = this.repository.findMyChimpums(inventorId);
		
		return result;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		final String code = entity.getCode();

		request.unbind(entity, model, "title", "startDate", "budget");
		model.setAttribute("code", code);
	}

}
