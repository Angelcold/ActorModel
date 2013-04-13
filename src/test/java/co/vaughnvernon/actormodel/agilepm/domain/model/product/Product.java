package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItem;
import co.vaughnvernon.actormodel.message.StringMessage;

public class Product extends BaseActor {

	private String description;
	private String name;

	public Product(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.description = anInitializer.getParameter("description");
		this.name = anInitializer.getParameter("name");
	}

	public void when(PlanBacklogItem aCommand) {
		System.out.println("Product when(PlanBacklogItemCommand)");

		ActorInitializer initializer = new ActorInitializer(this.registry());

		initializer.putParameter("product", this);
		initializer.putParameter("summary", aCommand.summary());
		initializer.putParameter("story", aCommand.story());

		ActorAgent backlogItem = this.registry().actorFor(BacklogItem.class, initializer);

		backlogItem.tell(new StringMessage("Echo"));

		System.out.println("Product: " + this.name() + " described as: " + this.description() + " told BacklogItem to echo.");
	}

	@Override
	public boolean wantsSmartMessageDispatching() {
		return true;
	}

	private String description() {
		return this.description;
	}

	private String name() {
		return this.name;
	}
}
