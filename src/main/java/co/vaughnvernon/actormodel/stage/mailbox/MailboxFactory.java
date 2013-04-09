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

package co.vaughnvernon.actormodel.stage.mailbox;

import co.vaughnvernon.actormodel.actor.ActorFinder;
import co.vaughnvernon.actormodel.actor.ActorPersister;
import co.vaughnvernon.actormodel.actor.ActorRegistry;

/**
 * I define an interface for creating Mailbox instances.
 *
 * @author Vaughn Vernon
 */
public interface MailboxFactory {

	/**
	 * Answers the new Mailbox for anActorTypeName. The factory may
	 * need to find an Actor when a Message is received, and thus may
	 * use anActorFinder.
	 * @param anActorTypeName the String name of the Actor type
	 * @param anActoryRegistry the ActorRegistry in use
	 * @param anActorPersister the ActorPersister in use
	 * @param anActorFinder the ActorFinder used to find an Actor that receives a Message
	 * @return Mailbox
	 */
	public Mailbox newMailboxFor(
			String anActorTypeName,
			final ActorRegistry anActoryRegistry,
			final ActorPersister anActorPersister,
			final ActorFinder anActorFinder);
}
