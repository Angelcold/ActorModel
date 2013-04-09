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
import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.Message;

/**
 * Defines the agent interface to give clients the means
 * to collaborate with Actors.
 *
 * @author Vaughn Vernon
 */
public interface ActorAgent {

	/**
	 * Answers the actual Actor of the receiver.
	 * @return Actor
	 */
	public Actor actor();

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
	 * Ask the Actor to respond to aMessage.
	 * @param aMessage the Message that the Actor should respond to
	 * @param aFutureInterest the FutureInterest to register with the Future
	 * @return Future
	 */
	public Future ask(Message aMessage, FutureInterest aFutureInterest);

	/**
	 * Ask the Actor to respond to aMessage.
	 * @param aMessage the Message that the Actor should respond to
	 * @param aTimeout the long timeout in milliseconds
	 * @param aFutureInterest the FutureInterest to register with the Future
	 * @return Future
	 */
	public Future ask(Message aMessage, long aTimeout, FutureInterest aFutureInterest);

	/**
	 * Answers whether or not the receiver is an agent of an existing Actor.
	 * A false answer indicates that the the agent is a NullActorAgent.
	 * @return boolean
	 */
	public boolean actorExists();

	/**
	 * Shutdown the Actor gracefully after all currently queued
	 * commands/events/messages are handled.
	 */
	public void shutdown();

	/**
	 * Stops the Actor gracefully after the current
	 * command/event/message is handled.
	 */
	public void stop();

	/**
	 * Tell the Actor to execute aCommand.
	 * @param aCommand the Command that the Actor should execute
	 */
	public void tell(Command aCommand);

	/**
	 * Tell the Actor to execute aCommand.
	 * @param aSenderAgent the ActorAgent telling to execute the Command
	 * @param aCommand the Command that the Actor should execute
	 */
	public void tell(ActorAgent aSenderAgent, Command aCommand);

	/**
	 * Tell the Actor to handle anEvent.
	 * @param anEvent the Event that the Actor should handle
	 */
	public void tell(Event anEvent);

	/**
	 * Tell the Actor to handle anEvent.
	 * @param aSenderAgent the ActorAgent telling to handle the Event
	 * @param anEvent the Event that the Actor should handle
	 */
	public void tell(ActorAgent aSenderAgent, Event anEvent);

	/**
	 * Tell the Actor to react to aMessage.
	 * @param aMessage the Message that the Actor should react to
	 */
	public void tell(Message aMessage);

	/**
	 * Tell the Actor to react to aMessage.
	 * @param aSenderAgent the ActorAgent telling to handle the Event
	 * @param aMessage the Message that the Actor should react to
	 */
	public void tell(ActorAgent aSenderAgent, Message aMessage);

	/**
	 * Terminates the Actor immediately.
	 */
	public void terminate();
}
