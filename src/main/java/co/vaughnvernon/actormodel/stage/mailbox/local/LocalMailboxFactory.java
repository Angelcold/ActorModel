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

import co.vaughnvernon.actormodel.actor.ActorFinder;
import co.vaughnvernon.actormodel.actor.ActorPersister;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;
import co.vaughnvernon.actormodel.stage.mailbox.MailboxFactory;

/**
 * I implement a MailboxFactory for creating ThreadManagedRingBufferMailbox instances.
 *
 * @author Vaughn Vernon
 */
public class LocalMailboxFactory implements MailboxFactory {

	/**
	 * Initializes my default state.
	 */
	public LocalMailboxFactory() {
		super();
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.MailboxFactory#newMailboxFor(
	 * 		java.lang.String,
	 *      com.shiftmethod.actup.actor.ActorRegistry,
	 *      com.shiftmethod.actup.actor.ActorPersister,
	 *      com.shiftmethod.actup.actor.ActorFinder)
	 */
	@Override
	public Mailbox newMailboxFor(
			String anActorTypeName,
			final ActorRegistry anActorRegistry,
			final ActorPersister anActorPersister,
			final ActorFinder anActorFinder) {

		// TODO: make configuration parameters soft

		return new ThreadManagedRingBufferMailbox(
				anActorTypeName,
				4096,
				30,
				new LocalMailboxEnvelopeRecipient(anActorPersister));
	}
}
