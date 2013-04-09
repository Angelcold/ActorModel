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

import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;

/**
 * I define the interface for a Factory of ActorAgents.
 *
 * @author Vaughn Vernon
 */
public interface ActorAgentFactory {

	/**
	 * Answers a new ActorAgent from anActorType, anActor, and aMailbox.
	 * @param anActorRegistry the ActorRegistry in use
	 * @param anActorType the Class<? extends Actor> type of the Actor
	 * @param anActor the Actor for which the ActorAgent is to be created
	 * @param aMailbox the Mailbox of the type of Actor
	 * @return ActorAgent
	 */
	public ActorAgent newActorAgentFor(
			ActorRegistry anActorRegistry,
			Class<? extends Actor> anActorType,
			Actor anActor,
			Mailbox aMailbox);
}
