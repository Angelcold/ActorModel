package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItem;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItemPlanned;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.sprint.Sprint;

public class Product extends BaseActor {

    private String description;
    private String name;

	public Product(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.description = anInitializer.getParameter("description");
		this.name = anInitializer.getParameter("name");
	}

	public void when(BacklogItemPlanned anEvent) {

	}

	public void when(PlanBacklogItem aCommand) {
		ActorInitializer initializer = new ActorInitializer(this.registry());

		initializer.putParameter("product", this.self());
		initializer.putParameter("summary", aCommand.summary());
		initializer.putParameter("story", aCommand.story());

		this.registry().actorFor(BacklogItem.class, initializer);
	}

	public void when(ScheduleSprint aCommand) {
		ActorInitializer initializer = new ActorInitializer(this.registry());

		initializer.putParameter("product", this.self());
		initializer.putParameter("name", aCommand.name());
		initializer.putParameter("goals", aCommand.goals());
		initializer.putParameter("begins", aCommand.begins());
		initializer.putParameter("ends", aCommand.ends());

		this.registry().actorFor(Sprint.class, initializer);
	}

	public void when(SprintScheduled anEvent) {
	}

	@Override
	public boolean wantsFilteredDelivery() {
		return true;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", description=" + description + "]";
	}

	@SuppressWarnings("unused")
	private String description() {
		return this.description;
	}

	@SuppressWarnings("unused")
	private String name() {
		return this.name;
	}
}
