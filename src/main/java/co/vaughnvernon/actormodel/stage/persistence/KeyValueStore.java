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

import java.util.Collection;
import java.util.Map;

import co.vaughnvernon.actormodel.actor.Actor;

/**
 * I define an interface for key-value stores, which may be implemented
 * by any number of concrete types.
 *
 * @author Vaughn Vernon
 *
 * @param <K> the type of the keys for a given implementation
 * @param <V> the type of the values for a given implementation
 */
public interface KeyValueStore<K,V> {

    /**
     * Clears my entire store, removing all key-value pairs.
     */
    public void clear();

    /**
     * Answers true if I am storying a key-value pair with aKey; otherwise false.
     * @param aKey the K typed key
     * @return boolean
     */
    public boolean containsKey(K aKey);

    /**
     * Answers the V typed value associated with aKey; otherwise null.
     * @param aKey the K typed key
     * @return V
     */
    public V get(K aKey);

    /**
     * Answers true if I am storing no key-value pairs; otherwise false.
     * @return boolean
     */
    public boolean isEmpty();

    /**
     * Answers the Collection of all keys in my store.
     * @return Collection<K>
     */
    public Collection<K> keySet();

	/**
	 * Answers my name.
	 * @return String
	 */
	public String name();

    /**
     * Puts the key-value pair into my store.
     * @param aKey the K typed key
     * @param aValue the V typed value
     */
    public void put(K key, V value);

    /**
     * Puts in bulk all key-value pairs in aMap to my store.
     * @param aMap the Map containing the key-value pairs to put
     */
    public void putAll(Map<String, Actor> aMap);

    /**
     * Answers true after having removed the key-value pair
     * identified by aKey from my store; otherwise answers false.
     * @param aKey the K typed key
     * @return boolean
     */
    public boolean remove(K aKey);

    /**
     * Answers whether or not the receiver requires value
     * modifications to be updated to the underlying store
     * by means of put().
     * @return boolean
     */
    public boolean requiresValueUpdates();

	/**
	 * Answers the number of key-value pairs that I am storing.
	 * @return long
	 */
    public long size();
}
