
package acme.features.inventor.chimpum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Chimpum;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorChimpumController extends AbstractController<Inventor, Chimpum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorChimpumListService	listService;

	@Autowired
	protected InventorChimpumShowService	showService;
	
	
	@Autowired
	protected InventorChimpumCreateService createService;

	/*
	@Autowired
	protected InventorItemCreateService createService;
	
	@Autowired
	protected InventorItemDeleteService	deleteService;
	
	@Autowired
	protected InventorItemPublishService publishService;
	*/
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("list","list",this.listService);
		super.addCommand("show","show",this.showService);
		super.addCommand("create","create",this.createService);
		//super.addCommand("update",this.updateService);
		//super.addCommand("delete",this.deleteService);
	}

}
