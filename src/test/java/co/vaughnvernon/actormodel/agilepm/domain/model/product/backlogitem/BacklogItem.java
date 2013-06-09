package co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.actor.Query;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.sprint.Sprint;

public class BacklogItem extends BaseActor {

	private Address product;
	private Address sprint;
	private String story;
	private String summary;

	public BacklogItem(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.story = anInitializer.getParameter("story");
		this.summary = anInitializer.getParameter("summary");

		ActorAgent product = anInitializer.getParameter("product");

		this.product = product.address();

		product.tell(new BacklogItemPlanned(product.address(), this.self().address()));
	}

	@Override
	public boolean matches(Query aQuery) {
		if (this.summary.equals(aQuery.getParameter("summary"))) {
			return true;
		}

		return false;
	}

	@Override
	public boolean wantsFilteredDelivery() {
		return true;
	}

	public void when(CommitTo aCommand) {
		this.sprint = aCommand.sprint();

		ActorAgent sprint = this.registry().actorRegisteredAs(Sprint.class, this.sprint);

		sprint.tell(new BacklogItemCommitted(this.product, this.address(), this.sprint));
	}

	@Override
	public String toString() {
		return "BacklogItem [summary=" + summary + ", story=" + story
				+ ", sprint=" + (sprint == null ? "not committed" : sprint.address()) +"]";
	}
}
