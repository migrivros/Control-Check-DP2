package acme.features.inventor.itemQuantity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.ItemQuantity;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorItemQuantityController extends AbstractController<Inventor,ItemQuantity>{

    @Autowired
    protected InventorItemQuantityListService listService;
    
    @Autowired
    protected InventorItemQuantityCreateService createService;
    
    @Autowired
    protected InventorItemQuantityShowService showService;
    
    @Autowired
    protected InventorItemQuantityUpdateService updateService;
    
    @Autowired
    protected InventorItemQuantityDeleteService deleteService;

    @PostConstruct
    protected void initialise() {
        super.addCommand("list","list", this.listService);
        super.addCommand("create", this.createService);
        super.addCommand("update", this.updateService);
        super.addCommand("show", this.showService);
        super.addCommand("delete", this.deleteService);
    }

}
