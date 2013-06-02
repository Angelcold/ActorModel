package co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.message.Message;

public class BacklogItem extends BaseActor {

	private String story;
	private String summary;

	public BacklogItem(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.story = anInitializer.getParameter("story");
		this.summary = anInitializer.getParameter("summary");

		ActorAgent product = anInitializer.getParameter("product");

		product.tell(new BacklogItemPlanned(product.address(), this.self().address()));
	}

	@Override
	public void reactTo(Message aMessage) {
		System.out.println(this.toString());
	}

	@Override
	public boolean wantsFilteredDelivery() {
		return false;
	}

	@Override
	public String toString() {
		return "BacklogItem [summary=" + summary + ", story=" + story + "]";
	}
}
