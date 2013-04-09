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

import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.Message;

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
	 * Reacts to aMessage.
	 * @param aMessage the Message to react to
	 */
	public void reactTo(Message aMessage);

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
	 * Messages directly to the methods that handle that message type.
	 * The pattern is that the Actor must define methods of the form:
	 * <br/><br/>when(ConcreteMessageType aMessage)
	 * @return boolean
	 */
	public boolean wantsSmartMessageDispatching();
}
