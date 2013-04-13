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

import java.util.HashMap;
import java.util.Map;

/**
 * Defines the initializer for an Actor. The default constructor
 * automatically generates a unique Address. Note that each instance
 * of ActorInitializer should be obtained from the ActorRegistry.
 *
 * TODO: Add field-level reflection-based initialization onto a given Actor.
 * From the Actor's constructor we would do this:
 *
 * public SomeActor(ActorInitializer anInitializer) {
 *     super();
 *     anInititalizer.initialize(this);
 *     this.postInititalize(); // actor can perform post initializations
 * }
 *
 * @author Vaughn Vernon
 */
public class ActorInitializer {

	/** My address, which is a new address for the Actor being initialized. */
	private Address address;

	/** My parameters, which hold all initialization parameters other than the Address. */
	private Map<String, Object> parameters;

	private ActorRegistry registry;

	/**
	 * Constructs my default state.
	 * @param anActorRegistry the ActorRegistry
	 */
	public ActorInitializer(ActorRegistry anActorRegistry) {
		super();

		this.address = anActorRegistry.newAddress();
		this.parameters = new HashMap<String,Object>();
		this.registry = anActorRegistry;
	}

	/**
	 * Answers my address.
	 * @return Address
	 */
	public Address address() {
		return this.address;
	}

	/**
	 * Answers the parameter named aName.
	 * @param aName the String name of the parameter to get
	 * @return the T typed parameter
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String aName) {
		return (T) this.parameters.get(aName);
	}

	/**
	 * Sets the parameter with aName and aValue.
	 * @param aName the String name of the parameter to set
	 * @param aValue the Object value to set
	 */
	public void putParameter(String aName, Object aValue) {
		this.parameters.put(aName, aValue);
	}

	/**
	 * Answers my registry.
	 * @return ActorRegistry
	 */
	public ActorRegistry registry() {
		return this.registry;
	}
}
