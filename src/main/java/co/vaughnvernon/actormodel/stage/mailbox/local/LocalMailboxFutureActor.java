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

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.FutureValueMessage;
import co.vaughnvernon.actormodel.message.Message;

public class LocalMailboxFutureActor implements Actor {

	private Address address;
	private ActorRegistry actorRegistry;
	private LocalMailboxFuture future;

	public LocalMailboxFutureActor(
			ActorInitializer anInitializer) {
		super();

		this.address = anInitializer.address();
		this.actorRegistry = anInitializer.getParameter("registry");
		this.future = anInitializer.getParameter("future");
	}

	@Override
	public Address address() {
		return this.address;
	}

	@Override
	public String actorType() {
		return this.getClass().getName();
	}

	@Override
	public Object answer(Message aMessage) {
		throw new UnsupportedOperationException("Future actors do not answer.");
	}

	@Override
	public void execute(Command aCommand) {
		throw new UnsupportedOperationException("Future actors do not execute commands.");
	}

	@Override
	public void handle(Event anEvent) {
		throw new UnsupportedOperationException("Future actors do not handle events.");
	}

	@Override
	public void reactTo(Message aMessage) {
		this.future.setAnswer(((FutureValueMessage) aMessage).answer());

		this.actorRegistry.deregister(this.future.sender());

		this.future.futureInterest().completedWith(this.future);
	}

	@Override
	public ActorAgent self() {
		return null;
	}

	@Override
	public ActorAgent sender() {
		return null;
	}

	@Override
	public void start() {
		// TODO
	}

	@Override
	public void stop() {
		// TODO
	}

	@Override
	public boolean wantsSmartMessageDispatching() {
		return false;
	}
}
