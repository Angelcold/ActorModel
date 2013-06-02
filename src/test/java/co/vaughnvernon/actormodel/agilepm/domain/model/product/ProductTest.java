package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import java.util.Date;
import java.util.List;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.actor.ActorTestCase;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItem;
import co.vaughnvernon.actormodel.future.Future;
import co.vaughnvernon.actormodel.future.FutureInterest;
import co.vaughnvernon.actormodel.stage.KeyValueStoreActorRegistry;
import co.vaughnvernon.actormodel.stage.address.UUIDActorAddressFactory;
import co.vaughnvernon.actormodel.stage.mailbox.local.LocalMailboxActorAgentFactory;
import co.vaughnvernon.actormodel.stage.mailbox.local.LocalMailboxFactory;
import co.vaughnvernon.actormodel.stage.persistence.hashmap.HashMapKeyValueStoreFactory;

public class ProductTest extends ActorTestCase {

	private ActorRegistry actorRegistry;

	public ProductTest() {
		super();

		this.actorRegistry =
				KeyValueStoreActorRegistry
					.instance(
							new UUIDActorAddressFactory(),
							new LocalMailboxActorAgentFactory(),
							new LocalMailboxFactory(),
							new HashMapKeyValueStoreFactory());
	}

	public void testProductPlanBacklogItem() throws Exception {
		ActorInitializer initializer = new ActorInitializer(this.actorRegistry);
		initializer.putParameter("name", "Test Product");
		initializer.putParameter("description", "A decsription.");

		ActorAgent product = this.actorRegistry.actorFor(Product.class, initializer);

		product.tell(new PlanBacklogItem(
				"A summary of the backlog item.",
				"A story about the backlog item."));

		this.stayAlive(500L);
	}

	public void testProductScheduleSprint() throws Exception {
		ActorInitializer initializer = new ActorInitializer(this.actorRegistry);
		initializer.putParameter("name", "Test Product");
		initializer.putParameter("description", "A decsription.");

		ActorAgent product = this.actorRegistry.actorFor(Product.class, initializer);

		product.tell(new ScheduleSprint(
				"Sprint 1",
				"The initial sprint.",
				this.daysFromNow(1),
				this.daysFromNow(15)));

		this.stayAlive(500L);
	}

	public void testCommitBacklogItemToSprint() throws Exception {
		ActorInitializer initializer = new ActorInitializer(this.actorRegistry);
		initializer.putParameter("name", "Test Product");
		initializer.putParameter("description", "A decsription.");

		final ActorAgent product = this.actorRegistry.actorFor(Product.class, initializer);

		product.tell(new PlanBacklogItem(
				"A summary of the backlog item.",
				"A story about the backlog item."));

		product.tell(new ScheduleSprint(
				"Sprint 1",
				"The initial sprint.",
				this.daysFromNow(1),
				this.daysFromNow(15)));

		product.ask(new GetSprintNamed("Sprint 1"), new FutureInterest() {
			@Override
			public void completedWith(Future aFuture) {
				final ActorAgent sprint = aFuture.value();

				product.ask(new GetBacklogItems(), new FutureInterest() {
					@Override
					public void completedWith(Future aFuture) {
						List<Address> backlogItems = aFuture.value();

						ActorAgent backlogItem = actorRegistry.actorRegisteredAs(BacklogItem.class, backlogItems.get(0));

						backlogItem.tell(new CommitTo(sprint.address()));
					}
					@Override
					public void timedOut(Future aFuture) {
						System.out.println("Timed out GetBacklogItems");
					}
				});
			}
			@Override
			public void timedOut(Future aFuture) {
				System.out.println("Timed out GetSprintNamed");
			}
		});

		this.stayAlive(500L);
	}

	private Date daysFromNow(long aNumberOfDays) {
		Date now = new Date();

		return new Date(now.getTime() + (aNumberOfDays * 24L * 60L * 60L * 1000L));
	}
}
