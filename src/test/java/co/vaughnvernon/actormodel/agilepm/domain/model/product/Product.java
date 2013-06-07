package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItem;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItemPlanned;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.sprint.Sprint;
import co.vaughnvernon.actormodel.message.StringMessage;

public class Product extends BaseActor {

    private String description;
    private String name;

	public Product(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.description = anInitializer.getParameter("description");
		this.name = anInitializer.getParameter("name");
	}

	public void when(BacklogItemPlanned anEvent) {
		System.out.println("Product when(BacklogItemPlanned)");
	}

	public void when(StringMessage aMessage) {
		System.out.println("Product received: " + aMessage);
	}

	public void when(PlanBacklogItem aCommand) {
		System.out.println("Product when(PlanBacklogItem)");

		ActorInitializer initializer = new ActorInitializer(this.registry());

		initializer.putParameter("product", this.self());
		initializer.putParameter("summary", aCommand.summary());
		initializer.putParameter("story", aCommand.story());

		ActorAgent backlogItem = this.registry().actorFor(BacklogItem.class, initializer);

		backlogItem.tell(new StringMessage("Echo"));
	}

	public void when(ScheduleSprint aCommand) {
		System.out.println("Product when(ScheduleSprint)");

		ActorInitializer initializer = new ActorInitializer(this.registry());

		initializer.putParameter("product", this.self());
		initializer.putParameter("name", aCommand.name());
		initializer.putParameter("goals", aCommand.goals());
		initializer.putParameter("begins", aCommand.begins());
		initializer.putParameter("ends", aCommand.ends());

		ActorAgent sprint = this.registry().actorFor(Sprint.class, initializer);

		sprint.tell(new StringMessage("Echo"));

		System.out.println("Product: " + this.name() + " described as: " + this.description() + " told Sprint to echo.");
	}

	public void when(SprintScheduled anEvent) {
		System.out.println("Product when(SprintScheduled)");
	}

	@Override
	public boolean wantsFilteredDelivery() {
		return true;
	}

	private String description() {
		return this.description;
	}

	private String name() {
		return this.name;
	}
}
