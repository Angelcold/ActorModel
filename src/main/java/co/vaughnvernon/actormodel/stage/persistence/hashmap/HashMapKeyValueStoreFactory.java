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

package co.vaughnvernon.actormodel.stage.persistence.hashmap;

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.stage.persistence.KeyValueStore;
import co.vaughnvernon.actormodel.stage.persistence.KeyValueStoreFactory;

/**
 * I implement a KeyValueStoreFactory for the HashMapKeyValueStore.
 *
 * @author Vaughn Vernon
 */
public class HashMapKeyValueStoreFactory implements KeyValueStoreFactory<String, Actor> {

	/**
	 * Initializes my default state.
	 */
	public HashMapKeyValueStoreFactory() {
		super();
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.persistence.KeyValueStoreFactory#newKeyValueStore(java.lang.String)
	 */
	@Override
	public KeyValueStore<String, Actor> newKeyValueStore(String aKeyValueStoreName) {
		return new HashMapKeyValueStore(aKeyValueStoreName);
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.persistence.KeyValueStoreFactory#requiresValueUpdates()
	 */
	@Override
	public boolean requiresValueUpdates() {
		return false;
	}
}
