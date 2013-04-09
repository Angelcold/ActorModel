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

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.future.Future;
import co.vaughnvernon.actormodel.future.FutureInterest;
import co.vaughnvernon.actormodel.message.Message;

public class LocalMailboxFuture implements Future {

	private Object answer;
	private FutureInterest futureInterest;
	private LocalMailboxActorAgent receiverAgent;
	private ActorAgent senderAgent;
	private boolean timedOut;

	public LocalMailboxFuture(
			ActorRegistry anActorRegistry,
			LocalMailboxActorAgent aReceiverActorAgent,
			Message aMessage,
			FutureInterest aFutureInterest) {

		super();

		this.setFutureInterest(aFutureInterest);
		this.setReceiverAgent(aReceiverActorAgent);

		ActorInitializer initializer = anActorRegistry.newActorInitializer();
		initializer.putParameter("future", this);
		initializer.putParameter("registry", anActorRegistry);

		ActorAgent senderAgent =
				anActorRegistry.actorFor(LocalMailboxFutureActor.class, initializer);

		this.setSenderAgent(senderAgent);

		aReceiverActorAgent.sendTo(senderAgent, aMessage, true);
	}

	@Override
	public boolean isInoperable() {
		return false;
	}

	@Override
	public ActorAgent receiver() {
		return this.receiverAgent;
	}

	@Override
	public ActorAgent sender() {
		return this.senderAgent;
	}

	@Override
	public boolean timedOut() {
		return this.timedOut;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T value() {
		return (T) this.answer;
	}

	protected FutureInterest futureInterest() {
		return this.futureInterest;
	}

	protected void setAnswer(Object anAnswer) {
		this.answer = anAnswer;
	}

	private void setFutureInterest(FutureInterest aFutureInterest) {
		this.futureInterest = aFutureInterest;
	}

	private void setReceiverAgent(LocalMailboxActorAgent aReceiverAgent) {
		this.receiverAgent = aReceiverAgent;
	}

	private void setSenderAgent(ActorAgent aSenderAgent) {
		this.senderAgent = aSenderAgent;
	}
}
