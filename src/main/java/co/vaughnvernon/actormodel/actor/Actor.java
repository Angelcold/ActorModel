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
import co.vaughnvernon.actormodel.message.ShortArrayMessage;
import co.vaughnvernon.actormodel.message.ShortMessage;
import co.vaughnvernon.actormodel.message.StringArrayMessage;
import co.vaughnvernon.actormodel.message.StringMessage;

/**
 * Defines the interface for Actors allowing commands to be
 * executed, events to be handled, and messages to be reacted to.
 *
 * @author Vaughn Vernon
 */
public interface Actor {

	public static final String ACTOR_ADDRESS = "address";

	/**
	 * Answers the Address of the receiver.
	 * @return Address
	 */
	public Address address();

	/**
	 * Answers the String type of the Actor.
	 * @return String
	 */
	public String actorType();

	/**
	 * Answers the Object result of reacting to aMessage.
	 * @param aMessage the Message to react to
	 * @return Object
	 */
	public Object answer(Message aMessage);

	/**
	 * Executes aCommand.
	 * @param aCommand the Command to execute
	 */
	public void execute(Command aCommand);

	/**
	 * Handles anEvent.
	 * @param anEvent the Event to handle
	 */
	public void handle(Event anEvent);

	/**
	 * Answers whether or not I match aQuery.
	 * @param aQuery the Query to match
	 * @return boolean
	 */
	public boolean matches(Query aQuery);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the Message to react to
	 */
	public void reactTo(Message aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the BigDecimalMessage to react to
	 */
	public void reactTo(BigDecimalMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the BigDecimalArrayMessage to react to
	 */
	public void reactTo(BigDecimalArrayMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the DoubleMessage to react to
	 */
	public void reactTo(DoubleMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the DoubleArrayMessage to react to
	 */
	public void reactTo(DoubleArrayMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the FloatMessage to react to
	 */
	public void reactTo(FloatMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the FloatArrayMessage to react to
	 */
	public void reactTo(FloatArrayMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the IntegerMessage to react to
	 */
	public void reactTo(IntegerMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the IntegerArrayMessage to react to
	 */
	public void reactTo(IntegerArrayMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the LongMessage to react to
	 */
	public void reactTo(LongMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the LongArrayMessage to react to
	 */
	public void reactTo(LongArrayMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the ShortMessage to react to
	 */
	public void reactTo(ShortMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the ShortArrayMessage to react to
	 */
	public void reactTo(ShortArrayMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the StringMessage to react to
	 */
	public void reactTo(StringMessage aMessage);

	/**
	 * Reacts to aMessage.
	 * @param aMessage the StringArrayMessage to react to
	 */
	public void reactTo(StringArrayMessage aMessage);

	/**
	 * Answers the ActorAgent for myself.
	 * @return ActorAgent
	 */
	public ActorAgent self();

	/**
	 * Answers the ActorAgent of the sender of my current interaction.
	 * @return ActorAgent
	 */
	public ActorAgent sender();

	/**
	 * Allows the receiver to prepare to be started.
	 */
	public void start();

	/**
	 * Allows the receiver to prepare to be stopped.
	 */
	public void stop();

	/**
	 * Answers whether or not this Actor expects the Mailbox to deliver
	 * Messages directly to the methods that handle that message type. If
	 * true, the pattern is that the Actor must define methods of the form:
	 * <br/><br/>when(ConcreteMessageType aMessage)
	 * @return boolean
	 */
	public boolean wantsFilteredDelivery();
}
