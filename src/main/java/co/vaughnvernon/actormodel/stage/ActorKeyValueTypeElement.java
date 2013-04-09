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

package co.vaughnvernon.actormodel.stage;

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;
import co.vaughnvernon.actormodel.stage.persistence.KeyValueStore;

/**
 * I define an element that holds state associated with a specific Actor type.
 * There is the single Mailbox and a KeyValueStore.
 *
 * @author Vaughn Vernon
 */
public class ActorKeyValueTypeElement {

	/** My mailbox. */
	private Mailbox mailbox;

	/** My store. */
	private KeyValueStore<String,Actor> store;

	/**
	 * Initializes my default state.
	 * @param aMailbox the Mailbox to set as my mailbox
	 * @param aStore the KeyValueStore to set as my store
	 */
	public ActorKeyValueTypeElement(Mailbox aMailbox, KeyValueStore<String, Actor> aStore) {
		super();

		this.setMailbox(aMailbox);
		this.setStore(aStore);
	}

	/**
	 * Answers my mailbox.
	 * @return Mailbox
	 */
	public Mailbox mailbox() {
		return mailbox;
	}

	/**
	 * Answers my store.
	 * @return KeyValueStore
	 */
	public KeyValueStore<String, Actor> store() {
		return store;
	}

	/**
	 * Sets my mailbox.
	 * @param aMailbox the Mailbox to set as my mailbox
	 */
	private void setMailbox(Mailbox aMailbox) {
		this.mailbox = aMailbox;
	}

	/**
	 * Sets my store.
	 * @param aKeyValueStore the KeyValueStore to set as my store
	 */
	private void setStore(KeyValueStore<String, Actor> aKeyValueStore) {
		this.store = aKeyValueStore;
	}
}
