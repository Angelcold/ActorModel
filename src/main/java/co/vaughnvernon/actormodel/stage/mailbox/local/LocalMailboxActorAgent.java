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
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.actor.NullActorAgent;
import co.vaughnvernon.actormodel.future.Future;
import co.vaughnvernon.actormodel.future.FutureInterest;
import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.Message;
import co.vaughnvernon.actormodel.stage.mailbox.LocalMessageEnvelope;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;

/**
 * I am a local Mailbox implementation of ActorAgent.
 *
 * @author Vaughn Vernon
 */
public class LocalMailboxActorAgent implements ActorAgent {

	/** My actor. */
	private Actor actor;

	/** My actorRegistry. */
	private ActorRegistry actorRegistry;

	/** My actorType. */
	private Class<? extends Actor> actorType;

	/** My mailbox. */
	private Mailbox mailbox;

	/**
	 * Constructs my default state.
	 */
	public LocalMailboxActorAgent(
			ActorRegistry anActorRegistry,
			Class<? extends Actor> anActorType,
			Actor anActor,
			Mailbox aMailbox) {

		super();

		this.setActorRegistry(anActorRegistry);
		this.setActorType(anActorType);
		this.setActor(anActor);
		this.setMailbox(aMailbox);
	}

	/**
	 * @see com.shiftmethod.actup.actor.ActorAgent#actor()
	 */
	@Override
	public Actor actor() {
		return this.actor;
	}

	@Override
	public Address address() {
		return this.actor.address();
	}

	@Override
	public String actorType() {
		return this.actorType.getName();
	}

	@Override
	public Future ask(Message aMessage, FutureInterest aFutureInterest) {
		return new LocalMailboxFuture(this.actorRegistry(), this, aMessage, aFutureInterest);
	}

	@Override
	public Future ask(Message aMessage, long aTimeout, FutureInterest aListener) {
		return null; // TODO
	}

	@Override
	public boolean actorExists() {
		return true;
	}

	@Override
	public void shutdown() {
		// TODO
	}

	@Override
	public void stop() {
		// TODO
	}

	@Override
	public void tell(Command aCommand) {
		this.sendTo(aCommand, false);
	}

	@Override
	public void tell(ActorAgent aSenderAgent, Command aCommand) {
		this.sendTo(aSenderAgent, aCommand, false);
	}

	@Override
	public void tell(Event anEvent) {
		this.sendTo(anEvent, false);
	}

	@Override
	public void tell(ActorAgent aSenderAgent, Event anEvent) {
		this.sendTo(aSenderAgent, anEvent, false);
	}

	@Override
	public void tell(Message aMessage) {
		this.sendTo(aMessage, false);
	}

	@Override
	public void tell(ActorAgent aSenderAgent, Message aMessage) {
		this.sendTo(aSenderAgent, aMessage, false);
	}

	@Override
	public void terminate() {
	}

	protected void setActor(Actor anActor) {
		this.actor = anActor;
	}

	protected ActorRegistry actorRegistry() {
		return actorRegistry;
	}

	protected void setActorRegistry(ActorRegistry actorRegistry) {
		this.actorRegistry = actorRegistry;
	}

	protected void setActorType(Class<? extends Actor> actorType) {
		this.actorType = actorType;
	}

	/**
	 * Answers my mailbox.
	 * @return Mailbox
	 */
	protected Mailbox mailbox() {
		return mailbox;
	}

	/**
	 * Sets my mailbox.
	 * @param aMailbox the Mailbox to set as my mailbox
	 */
	protected void setMailbox(Mailbox aMailbox) {
		this.mailbox = aMailbox;

	}

	protected void sendTo(Message aMessage, boolean isForFuture) {
		ActorAgent agent = NullActorAgent.instance();

        this.sendTo(agent, aMessage, isForFuture);
	}

	protected void sendTo(ActorAgent aSenderAgent, Message aMessage, boolean isAsking) {
        this.mailbox().receive(new LocalMessageEnvelope(aSenderAgent, this, aMessage, isAsking));
	}
}
