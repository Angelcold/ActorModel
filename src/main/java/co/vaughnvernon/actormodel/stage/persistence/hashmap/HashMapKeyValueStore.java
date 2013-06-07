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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.stage.persistence.KeyValueStore;

/**
 * I implement a KeyValueStore for local, in-VM storage. I simply
 * delegate all requests to my backing HashMap.
 *
 * @author Vaughn Vernon
 */
public class HashMapKeyValueStore implements KeyValueStore<String,Actor> {

	/** My name. */
	private String name;

	/** My backing store. */
	private Map<String,Actor> store;

	/**
	 * Initializes my default state.
	 * @param aName the Sting to set as my name
	 */
	public HashMapKeyValueStore(String aName) {
		super();

		this.setName(aName);

		this.setStore(new HashMap<String,Actor>());
	}

	@Override
	public void clear() {
		this.store().clear();
	}

	@Override
	public boolean containsKey(String aKey) {
		return this.store().containsKey(aKey);
	}

	@Override
	public Actor get(String aKey) {
		return this.store().get(aKey);
	}

	@Override
	public boolean isEmpty() {
		return this.store().isEmpty();
	}

	@Override
	public Collection<String> keySet() {
		return this.store().keySet();
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public void put(String aKey, Actor aValue) {
		this.store().put(aKey, aValue);
	}

	@Override
	public void putAll(Map<String,Actor> aMap) {
		this.store().putAll(aMap);
	}

	@Override
	public boolean remove(String aKey) {
		return this.store().remove(aKey) != null;
	}

	@Override
	public boolean requiresValueUpdates() {
		return false;
	}

	@Override
	public long size() {
		return this.store().size();
	}

	@Override
	public Collection<Actor> values() {
		return this.store().values();
	}

	/**
	 * Sets my name.
	 * @param aName the String to set as my name
	 */
	private void setName(String aName) {
		if (aName == null || aName.isEmpty()) {
			throw new IllegalArgumentException("The name of a key-value store may not be null.");
		}

		this.name = aName;
	}

	/**
	 * Answers my store.
	 * @return Map<String,Actor>
	 */
	private Map<String, Actor> store() {
		return this.store;
	}

	/**
	 * Sets my store.
	 * @param aMap the Map to set as my store
	 */
	private void setStore(Map<String, Actor> aMap) {
		this.store = aMap;
	}
}
