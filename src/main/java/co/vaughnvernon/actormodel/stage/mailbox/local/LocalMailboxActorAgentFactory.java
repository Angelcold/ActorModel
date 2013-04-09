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
import co.vaughnvernon.actormodel.actor.ActorAgentFactory;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;

/**
 * I implement an ActorAgentFactory for LocalMailboxActorAgent instances.
 *
 * @author Vaughn Vernon
 */
public class LocalMailboxActorAgentFactory implements ActorAgentFactory {

	/**
	 * Initializes my default state.
	 */
	public LocalMailboxActorAgentFactory() {
		super();
	}

	/**
	 * @see com.shiftmethod.actup.actor.ActorAgentFactory#newActorAgentFor(
	 *      com.shiftmethod.actup.actor.ActorRegistry,
	 * 		java.lang.Class,
	 * 		com.shiftmethod.actup.actor.Actor,
	 *      com.shiftmethod.actup.stage.mailbox.Mailbox)
	 */
	@Override
	public ActorAgent newActorAgentFor(
			ActorRegistry anActorRegistry,
			Class<? extends Actor> anActorType,
			Actor anActor,
			Mailbox aMailbox) {

		return new LocalMailboxActorAgent(anActorRegistry, anActorType, anActor, aMailbox);
	}
}
