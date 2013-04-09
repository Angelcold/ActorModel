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

package co.vaughnvernon.actormodel.actor;

import co.vaughnvernon.actormodel.future.Future;
import co.vaughnvernon.actormodel.future.FutureInterest;
import co.vaughnvernon.actormodel.future.NullFuture;
import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.Message;

/**
 * I am a NullObject implementation of an ActorAgent.
 * I provide a special case, no-op implementation.
 *
 * @author Vaughn Vernon
 */
public class NullActorAgent implements ActorAgent {

	/** Singleton instance. */
	private static ActorAgent nullActorAgent = new NullActorAgent();

	/** My no-op nullActorAddress. */
	private Address nullAddress;

	/** My no-op nullFuture. */
	private Future nullFuture;

	public static ActorAgent instance() {
		return nullActorAgent;
	}

	@Override
	public Actor actor() {
		return null;
	}

	@Override
	public Address address() {
		return this.nullAddress;
	}

	@Override
	public String actorType() {
		return "";
	}

	/**
	 * Answers a no-op Future.
	 */
	@Override
	public Future ask(Message aMessage, FutureInterest aListener) {
		return this.nullFuture;
	}

	/**
	 * Answers a no-op Future.
	 */
	@Override
	public Future ask(Message aMessage, long aTimeout, FutureInterest aListener) {
		return this.nullFuture;
	}

	/**
	 * Answers false.
	 */
	@Override
	public boolean actorExists() {
		return false;
	}

	/**
	 * No-op shutdown.
	 */
	@Override
	public void shutdown() {
	}

	/**
	 * No-op shutdown.
	 */
	@Override
	public void stop() {
	}

	/**
	 * No-op tell.
	 */
	@Override
	public void tell(Command aCommand) {
	}

	/**
	 * No-op tell.
	 */
	@Override
	public void tell(ActorAgent aSenderAgent, Command aCommand) {
	}

	/**
	 * No-op tell.
	 */
	@Override
	public void tell(Event anEvent) {
	}

	/**
	 * No-op tell.
	 */
	@Override
	public void tell(ActorAgent aSenderAgent, Event anEvent) {
	}

	/**
	 * No-op tell.
	 */
	@Override
	public void tell(Message aMessage) {
	}

	/**
	 * No-op tell.
	 */
	@Override
	public void tell(ActorAgent aSenderAgent, Message aMessage) {
	}

	/**
	 * No-op terminate.
	 */
	@Override
	public void terminate() {
	}

	/**
	 * Constructs my default state.
	 */
	private NullActorAgent() {
		super();

		this.nullAddress = Address.newNullInstance();

		this.nullFuture = NullFuture.instance();
	}
}
