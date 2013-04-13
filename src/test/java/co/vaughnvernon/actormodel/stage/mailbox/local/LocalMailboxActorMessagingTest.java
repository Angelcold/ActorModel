//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package co.vaughnvernon.actormodel.stage.mailbox.local;

import java.util.Date;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.actor.ActorTestCase;
import co.vaughnvernon.actormodel.actor.TestActor;
import co.vaughnvernon.actormodel.actor.TestAggregateActor;
import co.vaughnvernon.actormodel.future.Future;
import co.vaughnvernon.actormodel.future.FutureInterest;
import co.vaughnvernon.actormodel.message.TestCommand;
import co.vaughnvernon.actormodel.message.TestEvent;
import co.vaughnvernon.actormodel.message.TestMessage;
import co.vaughnvernon.actormodel.message.TestRawThroughputCommand;
import co.vaughnvernon.actormodel.stage.KeyValueStoreActorRegistry;
import co.vaughnvernon.actormodel.stage.address.UUIDActorAddressFactory;
import co.vaughnvernon.actormodel.stage.persistence.hashmap.HashMapKeyValueStoreFactory;

public class LocalMailboxActorMessagingTest extends ActorTestCase {

	private ActorRegistry actorRegistry;

	private boolean completed;
	private int askCounter;
	private int answerCounter;

	public LocalMailboxActorMessagingTest() {
		super();

		this.actorRegistry =
				KeyValueStoreActorRegistry
					.instance(
							new UUIDActorAddressFactory(),
							new LocalMailboxActorAgentFactory(),
							new LocalMailboxFactory(),
							new HashMapKeyValueStoreFactory());
	}

	public void testExecuteAsk() throws Exception {
		System.out.println("testExecuteAsk()");

		ActorAgent agent = actorRegistry.actorFor(TestActor.class);

		assertNotNull(agent);

		agent.ask(new TestMessage(), new FutureInterest() {
			@Override
			public void completedWith(Future aFuture) {
				completed = true;
				System.out.println("Future completed with: " + aFuture.value());
			}
			@Override
			public void timedOut(Future aFuture) {
				completed = false;
				System.out.println("Timed out");
			}
		});

		this.stayAlive(500L);

		assertTrue(completed);
	}

	public void testExecuteHowManyAsksIn5Seconds() throws Exception {
		System.out.println("testExecuteHowManyAsksIn5Seconds()");

		this.warmUp();

		ActorAgent agent = actorRegistry.actorFor(TestActor.class);

		assertNotNull(agent);

		long beforeReceive = new Date().getTime();

		FutureCountThread futureCountThread = new FutureCountThread(agent);
		futureCountThread.start();

		this.stayAlive(5000L);

		futureCountThread.close();

		this.stayAlive(1000L);

		long totalTime = new Date().getTime() - beforeReceive;

		System.out.println("Total milliseconds: " + totalTime);
		System.out.println("Ask count: " + futureCountThread.askCount());
		System.out.println("Answer count: " + futureCountThread.answerCount());

		assertEquals(futureCountThread.askCount(), futureCountThread.answerCount());
	}

	public void testExecute15Actors() throws Exception {
		System.out.println("testExecute15Actors()");

		this.warmUp();

		final int maxActors = 15;
		final int millis = 1000;

		ActorAgent[] agents = new ActorAgent[maxActors];
		FutureCountThread[] threads = new FutureCountThread[maxActors];

		for (int i = 0; i < maxActors; ++i) {
			agents[i] = actorRegistry.actorFor(TestActor.class);
			threads[i] = new FutureCountThread(agents[i]);
		}

		long beforeReceive = new Date().getTime();

		for (int i = 0; i < maxActors; ++i) {
			threads[i].start();
		}

		this.stayAlive(millis);

		for (int i = 0; i < maxActors; ++i) {
			threads[i].close();
		}

		this.stayAlive(500L);

		long totalTime = new Date().getTime() - beforeReceive;

		for (int i = 0; i < maxActors; ++i) {
			askCounter += threads[i].askCount();
			answerCounter += threads[i].answerCount();
		}

		System.out.println("Total milliseconds: " + totalTime);
		System.out.println("Ask count: " + askCounter + " Average/Second: " + (askCounter / (totalTime / 1000)));
		System.out.println("Answer count: " + answerCounter + " Average/Second: " + (answerCounter / (totalTime / 1000)));
	}

	public void testExecuteCommand() throws Exception {
		System.out.println("testExecuteCommand()");

		ActorAgent agent = actorRegistry.actorFor(TestActor.class);

		assertNotNull(agent);

		agent.tell(new TestCommand());

		this.stayAlive(500);
	}

	public void testExecuteWhenCommand() throws Exception {
		System.out.println("testExecuteCommand()");

		ActorAgent agent = actorRegistry.actorFor(TestAggregateActor.class);

		assertNotNull(agent);

		agent.tell(new TestCommand());

		this.stayAlive(100);
	}

	public void testExecuteWhenCommandFor5Seconds() throws Exception {
		System.out.println("testExecuteWhenCommandFor5Seconds()");

		ActorAgent agent = actorRegistry.actorFor(TestAggregateActor.class);

		assertNotNull(agent);

		RawMessageThoughputCountThread rawMessageThroughput =
				new RawMessageThoughputCountThread(agent);

		long beforeReceive = new Date().getTime();

		rawMessageThroughput.start();

		this.stayAlive(5000L);

		rawMessageThroughput.close();

		this.stayAlive(200L);

		long totalTime = new Date().getTime() - beforeReceive;

		System.out.println("Total milliseconds: " + totalTime);
		System.out.println("Message count: " + rawMessageThroughput.messageCount()
				+ " Average/Second: " + (rawMessageThroughput.messageCount() / (totalTime / 1000)));

		assertTrue(rawMessageThroughput.messageCount() >= 1000000);
	}

	public void testExecuteEvent() throws Exception {
		System.out.println("testExecuteEvent()");

		ActorAgent agent = actorRegistry.actorFor(TestActor.class);

		assertNotNull(agent);

		agent.tell(new TestEvent());

		this.stayAlive(200);
	}

	public void testExecuteWhenEvent() throws Exception {
		System.out.println("testExecuteEvent()");

		ActorAgent agent = actorRegistry.actorFor(TestAggregateActor.class);

		assertNotNull(agent);

		agent.tell(new TestEvent());

		this.stayAlive(200);
	}

	public void testExecuteMessage() throws Exception {
		System.out.println("testExecuteMessage()");

		ActorAgent agent = actorRegistry.actorFor(TestActor.class);

		assertNotNull(agent);

		agent.tell(new TestMessage());

		this.stayAlive(200);
	}

	public void testExecuteWhenMessage() throws Exception {
		System.out.println("testExecuteMessage()");

		ActorAgent agent = actorRegistry.actorFor(TestAggregateActor.class);

		assertNotNull(agent);

		agent.tell(new TestMessage());

		this.stayAlive(200);
	}

	@Override
	protected void tearDown() throws Exception {
		System.out.println("-----------------------");

		super.tearDown();
	}

	private static class FutureCountThread extends Thread {

		private boolean closed;
		private ActorAgent agent;
		private int askCount;
		private int answerCount;
		private FutureInterest futureInterest;

		public FutureCountThread(ActorAgent anAgent) {
			super();

			this.agent = anAgent;

			this.futureInterest = new FutureInterest() {
				@Override
				public void completedWith(Future aFuture) {
					++answerCount;
				}
				@Override
				public void timedOut(Future aFuture) {
				}
			};
		}

		public int askCount() {
			return this.askCount;
		}

		public int answerCount() {
			return this.answerCount;
		}

		public void close() {
			this.closed = true;
		}

		@Override
		public void run() {
			for ( ; askCount < 1000000 && !this.closed; ) {
				agent.ask(new TestMessage(), this.futureInterest);
				++askCount;
			}
		}
	}

	private static class RawMessageThoughputCountThread extends Thread {

		private boolean closed;
		private ActorAgent agent;
		private int messageCount;

		public RawMessageThoughputCountThread(ActorAgent anAgent) {
			super();

			this.agent = anAgent;
		}

		public int messageCount() {
			return this.messageCount;
		}

		public void close() {
			this.closed = true;
		}

		@Override
		public void run() {
			while (!this.closed) {
				agent.tell(new TestRawThroughputCommand());
				++messageCount;
			}
		}
	}

	private void warmUp() {
		System.out.println("Warming up...");

		ActorAgent agent = actorRegistry.actorFor(TestActor.class);

		FutureCountThread futureCountThread = new FutureCountThread(agent);
		futureCountThread.start();

		this.stayAlive(5000L);

		futureCountThread.close();

		this.stayAlive(100L);
	}
}
