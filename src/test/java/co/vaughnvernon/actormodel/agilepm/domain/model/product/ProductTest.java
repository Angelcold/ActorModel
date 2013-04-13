package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.actor.ActorTestCase;
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

	@Override
	protected void tearDown() throws Exception {
		System.out.println("-----------------------");

		super.tearDown();
	}
}
