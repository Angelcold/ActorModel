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

package co.vaughnvernon.actormodel.stage.persistence;


/**
 * I define the interface for Factory of KeyValueStore instances.
 *
 * @author Vaughn Vernon
 */
public interface KeyValueStoreFactory<K,V> {

	/**
	 * Answers a new KeyValueStore<K,V> instance that is
	 * identified by aKeyValueStoreName.
	 * @param aKeyValueStoreName the String name that identifies the store
	 * @return KeyValueStore<K,V>
	 */
	public KeyValueStore<K,V> newKeyValueStore(String aKeyValueStoreName);

    /**
     * Answers whether or not the underlying persistence store
     * for which I create instances requires value modifications
     * to be updated to the underlying store by means of put().
     * @return boolean
     */
    public boolean requiresValueUpdates();
}
