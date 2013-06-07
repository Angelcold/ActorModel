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

import co.vaughnvernon.actormodel.message.BigDecimalArrayMessage;
import co.vaughnvernon.actormodel.message.BigDecimalMessage;
import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.DoubleArrayMessage;
import co.vaughnvernon.actormodel.message.DoubleMessage;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.FloatArrayMessage;
import co.vaughnvernon.actormodel.message.FloatMessage;
import co.vaughnvernon.actormodel.message.IntegerArrayMessage;
import co.vaughnvernon.actormodel.message.IntegerMessage;
import co.vaughnvernon.actormodel.message.LongArrayMessage;
import co.vaughnvernon.actormodel.message.LongMessage;
import co.vaughnvernon.actormodel.message.Message;
import co.vaughnvernon.actormodel.message.NullAnswer;
import co.vaughnvernon.actormodel.message.ShortArrayMessage;
import co.vaughnvernon.actormodel.message.ShortMessage;
import co.vaughnvernon.actormodel.message.StringArrayMessage;
import co.vaughnvernon.actormodel.message.StringMessage;

/**
 * I implement the Actor interface in a basic way allowing
 * concrete Actors to focus on only necessary behavior.
 *
 * @author Vaughn Vernon
 */
public abstract class BaseActor implements Actor {

	private Address address;
	private ActorRegistry registry;

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#address()
	 */
	@Override
	public Address address() {
		return this.address;
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#actorType()
	 */
	@Override
	public String actorType() {
		return this.getClass().getName();
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#answer(co.vaughnvernon.actormodel.message.Message)
	 */
	@Override
	public Object answer(Message aMessage) {
		return NullAnswer.instance();
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#execute(co.vaughnvernon.actormodel.message.Command)
	 */
	@Override
	public void execute(Command aCommand) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#handle(co.vaughnvernon.actormodel.message.Event)
	 */
	@Override
	public void handle(Event anEvent) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#matches(co.vaughnvernon.actormodel.actor.Query)
	 */
	@Override
	public boolean matches(Query aQuery) {
		return false; // override to implement
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.Message)
	 */
	@Override
	public void reactTo(Message aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.BigDecimalMessage)
	 */
	@Override
	public void reactTo(BigDecimalMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.BigDecimalArrayMessage)
	 */
	@Override
	public void reactTo(BigDecimalArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.DoubleMessage)
	 */
	@Override
	public void reactTo(DoubleMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.DoubleArrayMessage)
	 */
	@Override
	public void reactTo(DoubleArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.FloatMessage)
	 */
	@Override
	public void reactTo(FloatMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.FloatArrayMessage)
	 */
	@Override
	public void reactTo(FloatArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.IntegerMessage)
	 */
	@Override
	public void reactTo(IntegerMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.IntegerArrayMessage)
	 */
	@Override
	public void reactTo(IntegerArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.LongMessage)
	 */
	@Override
	public void reactTo(LongMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.LongArrayMessage)
	 */
	@Override
	public void reactTo(LongArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.ShortMessage)
	 */
	@Override
	public void reactTo(ShortMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.ShortArrayMessage)
	 */
	@Override
	public void reactTo(ShortArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.StringMessage)
	 */
	@Override
	public void reactTo(StringMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#reactTo(co.vaughnvernon.actormodel.message.StringArrayMessage)
	 */
	@Override
	public void reactTo(StringArrayMessage aMessage) {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#self()
	 */
	@Override
	public ActorAgent self() {
		return this.registry().actorAgentFor(this);
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#sender()
	 */
	@Override
	public ActorAgent sender() {
		return NullActorAgent.instance();
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#start()
	 */
	@Override
	public void start() {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#stop()
	 */
	@Override
	public void stop() {

	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.Actor#wantsFilteredDelivery()
	 */
	@Override
	public boolean wantsFilteredDelivery() {
		return false;
	}

	protected BaseActor(Address anAddress, ActorRegistry anActorRegistry) {
		super();

		this.address = anAddress;
		this.registry = anActorRegistry;
	}

	protected BaseActor(ActorInitializer anInitializer) {
		this(anInitializer.address(), anInitializer.registry());
	}

	protected ActorRegistry registry() {
		return this.registry;
	}
}
