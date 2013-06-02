package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import java.util.Date;

import co.vaughnvernon.actormodel.message.Command;

public class PlanBacklogItem implements Command {

	private Date occurredOn;
	private String story;
	private String summary;

	public PlanBacklogItem(String aSummary, String aStory) {
		super();

		this.occurredOn = new Date();
		this.story = aStory;
		this.summary = aSummary;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}

	public String story() {
		return this.story;
	}

	public String summary() {
		return this.summary;
	}
}
