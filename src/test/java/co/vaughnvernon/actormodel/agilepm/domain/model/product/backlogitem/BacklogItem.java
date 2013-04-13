package co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem;

import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.message.Message;

public class BacklogItem extends BaseActor {

	private String story;
	private String summary;

	public BacklogItem(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.story = anInitializer.getParameter("story");
		this.summary = anInitializer.getParameter("summary");
	}

	@Override
	public void reactTo(Message aMessage) {
		System.out.println("BLI: Summary=" + this.summary + " Story=" + this.story);
	}

	@Override
	public boolean wantsSmartMessageDispatching() {
		return false;
	}
}
