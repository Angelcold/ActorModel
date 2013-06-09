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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorAgentFactory;
import co.vaughnvernon.actormodel.actor.ActorFinder;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.ActorPersister;
import co.vaughnvernon.actormodel.actor.ActorRegistry;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.actor.AddressFactory;
import co.vaughnvernon.actormodel.actor.NullActorAgent;
import co.vaughnvernon.actormodel.actor.Query;
import co.vaughnvernon.actormodel.message.Message;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;
import co.vaughnvernon.actormodel.stage.mailbox.MailboxFactory;
import co.vaughnvernon.actormodel.stage.persistence.KeyValueStore;
import co.vaughnvernon.actormodel.stage.persistence.KeyValueStoreFactory;

/**
 * I am a KeyValueStore compatible implementation of the ActorRegistry.
 *
 * @author Vaughn Vernon
 */
public class KeyValueStoreActorRegistry
	implements ActorRegistry, ActorPersister, ActorFinder {

	/** My singleton registry class variable. */
	private static ActorRegistry registry;

	/** My actorAgentFactory. */
	private ActorAgentFactory actorAgentFactory;

	/** My addressFactory. */
	private AddressFactory addressFactory;

	/** My actorTypeElements, one element per Actor type. */
	private Map<String, ActorKeyValueTypeElement> actorTypeElements;

	/** My keyValueStoreFactory. */
	private KeyValueStoreFactory<String,Actor> keyValueStoreFactory;

	/** My mailboxFactory. */
	private MailboxFactory mailboxFactory;

	/** My messageLog. */
	private List<Message> messageLog;

	/**
	 * Answers my singleton instance.
	 * @param anAddressFactory the AddressFactory
	 * @param anActorAgentFactory the ActorAgentFactory
	 * @param aMailboxFactory the MailboxFactory
	 * @param aKeyValueStoreFactory the KeyValueStoreFactory
	 * @return ActorRegistry
	 */
	public static synchronized ActorRegistry instance(
			AddressFactory anAddressFactory,
			ActorAgentFactory anActorAgentFactory,
			MailboxFactory aMailboxFactory,
			KeyValueStoreFactory<String,Actor> aKeyValueStoreFactory) {

		if (registry == null) {
			registry =
					new KeyValueStoreActorRegistry(
							anAddressFactory,
							anActorAgentFactory,
							aMailboxFactory,
							aKeyValueStoreFactory);
		}

		return registry;
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#actorAgentFor(co.vaughnvernon.actormodel.actor.Actor)
	 */
	@Override
	public ActorAgent actorAgentFor(Actor anActor) {
		return this.actorAgentFor(anActor.getClass(), anActor);
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#actorFor(java.lang.Class)
	 */
	public ActorAgent actorFor(Class<? extends Actor> anActorType) {
		return this.actorFor(anActorType, new ActorInitializer(this));
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#actorFor(java.lang.Class, co.vaughnvernon.actormodel.actor.ActorInitializer)
	 */
	public synchronized ActorAgent actorFor(Class<? extends Actor> anActorType, ActorInitializer anInitializer) {
		Actor actor = null;

		if (anInitializer == null) {
			throw new IllegalArgumentException("Requires non-null ActorInitializer.");
		}

		try {
			Constructor<? extends Actor> ctor = anActorType.getConstructor(ActorInitializer.class);
			actor = (Actor) ctor.newInstance(anInitializer);
			this.storeActor(anActorType, actor);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Cannot instantiate actor of type: "
					+ anActorType.getName()
					+ " because: "
					+ e.getMessage());
		}

		return this.actorAgentFor(anActorType, actor);
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#actorRegisteredAs(java.lang.Class, co.vaughnvernon.actormodel.actor.Address)
	 */
	public ActorAgent actorRegisteredAs(Class<? extends Actor> anActorType, Address anAddress) {
		ActorAgent agent = null;

		Actor actor = this.readActor(anActorType, anAddress);

		if (actor == null) {
			agent = NullActorAgent.instance();
		} else {
			agent = this.actorAgentFor(anActorType, actor);
		}

		return agent;
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#deregister(co.vaughnvernon.actormodel.actor.ActorAgent)
	 */
	@Override
	public void deregister(ActorAgent anActorAgent) {
		Class<? extends Actor> actorType =
				this.actorTypeFor(anActorAgent.actorType());

		if (actorType != null) {
			this.removeActor(actorType, anActorAgent.address());
		}
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#expectedMessage(java.lang.Class)
	 */
	@Override
    public void expectedMessage(Class<? extends Message> aMessageType) {
        this.expectedMessage(aMessageType, 1);
    }

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#expectedMessage(java.lang.Class, int)
	 */
	@Override
    public void expectedMessage(Class<? extends Message> aMessageType, int aTotal) {
        int count = 0;

        for (Message message : this.messageLog) {
            if (message.getClass() == aMessageType) {
                ++count;
            }
        }

        if (count != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aMessageType.getSimpleName() + " messages, but logged "
                    + this.messageLog.size() + " messages: " + this.messageLog);
        }
    }

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#expectedMessages(int)
	 */
	@Override
    public void expectedMessages(int aTotal) {
        if (this.messageLog.size() != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " messages, but logged " + this.messageLog.size()
                    + " events: " + this.messageLog);
        }
    }

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#log(co.vaughnvernon.actormodel.message.Message)
	 */
	@Override
	public void log(Message aMessage) {
		synchronized (this.messageLog) {
			this.messageLog.add(aMessage);
		}
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#newAddress()
	 */
	@Override
	public Address newAddress() {
		return this.addressFactory().newAddress();
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#newActorInitializer()
	 */
	@Override
	public ActorInitializer newActorInitializer() {
		return new ActorInitializer(this);
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorRegistry#shutDown()
	 */
	@Override
	public void shutDown() {
		// TODO: do full shut down

		this.messageLog.clear();

		registry = null;
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorFinder#findActorBy(java.lang.Class, co.vaughnvernon.actormodel.actor.Address)
	 */
	@Override
	public Actor findActorBy(
			Class<? extends Actor> anActorType,
			Address anAddress) {

		return this.readActor(anActorType, anAddress);
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorFinder#findActorAgentBy(java.lang.Class, co.vaughnvernon.actormodel.actor.Query)
	 */
	@Override
	public ActorAgent findFirstMatching(
			Class<? extends Actor> anActorType,
			Query aQuery) {

		ActorKeyValueTypeElement typeElement = this.typeElementFor(anActorType);

		for (Actor actor : typeElement.store().values()) {
			if (actor.matches(aQuery)) {
				ActorAgent actorAgent =
						this.actorAgentFactory()
							.newActorAgentFor(this, anActorType, actor, typeElement.mailbox());

				return actorAgent;
			}
		}

		return null;
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorPersister#requiresValueUpdates()
	 */
	@Override
	public boolean requiresValueUpdates() {
		return this.keyValueStoreFactory().requiresValueUpdates();
	}

	/**
	 * @see co.vaughnvernon.actormodel.actor.ActorPersister#store(co.vaughnvernon.actormodel.actor.Actor)
	 */
	@Override
	public void store(Actor anActor) {
		this.storeActor(anActor.getClass(), anActor);
	}

	/**
	 * Answers the Class<? extends Actor> type given anActorTypeName.
	 * @param anActorTypeName the String name of the Actor type
	 * @return Class<? extends Actor>
	 */
	protected Class<? extends Actor> actorTypeFor(String anActorTypeName) {
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Actor> actorType =
					(Class<? extends Actor>) Class.forName(anActorTypeName);

			return actorType;
		} catch (Exception e) {

			// TODO: deal with ClassNotFoundException and possibly others

			return null;
		}
	}

	/**
	 * Answers a new ActorAgent with Actor type, Address, and Mailbox state.
	 * @param anActorType the Class<? extends Actor> of the concrete Actor
	 * @param anActor the Actor instance
	 * @return ActorAgent
	 */
	protected ActorAgent actorAgentFor(Class<? extends Actor> anActorType, Actor anActor) {
		ActorKeyValueTypeElement typeElement = this.typeElementFor(anActorType);

		ActorAgent actorAgent =
				this.actorAgentFactory()
					.newActorAgentFor(this, anActorType, anActor, typeElement.mailbox());

		return actorAgent;
	}

	/**
	 * Answers the Actor of type anActorType and anAddress.
	 * NOTE: This is a public internal method.
	 * @param anActorType the Class<? extends Actor> concrete type of the Actor
	 * @param anAddress the Address of the Actor
	 * @return Actor
	 */
	protected Actor readActor(Class<? extends Actor> anActorType, Address anAddress) {
		ActorKeyValueTypeElement typeElement = this.typeElementFor(anActorType);

		Actor actor = typeElement.store().get(anAddress.address());

		return actor;
	}

	/**
	 * Removes the Actor of type anActorType and with anAddress
	 * from my cache.
	 * @param anActorType the Class<? extends Actor> of the concrete Actor to remove
	 * @param anAddress the Address of the Actor to removed
	 */
	protected void removeActor(Class<? extends Actor> anActorType, Address anAddress) {
		ActorKeyValueTypeElement typeElement = this.typeElementFor(anActorType);

		typeElement.store().remove(anAddress.address());
	}

	/**
	 * Stores anActor in my cache according to its type, anActorType.
	 * @param anActorType the Class<? extends Actor> of the concrete Actor type
	 * @param anActor the Actor instance to store
	 */
	protected void storeActor(Class<? extends Actor> anActorType, Actor anActor) {
		ActorKeyValueTypeElement typeElement = this.typeElementFor(anActorType);

		typeElement.store().put(anActor.address().address(), anActor);
	}

	/**
	 * Answers an ActorTypeElement for anActorType, which may require creation.
	 * @param anActorType the Class of the Actor
	 * @return ActorTypeElement
	 */
	protected synchronized ActorKeyValueTypeElement typeElementFor(
			Class<? extends Actor> anActorType) {

		String actorTypeName = anActorType.getName();

		ActorKeyValueTypeElement typeElement =
				this.actorTypeElements().get(actorTypeName);

		if (typeElement == null) {
			Mailbox mailbox =
					this.mailboxFactory().newMailboxFor(
							actorTypeName,
							this,
							this,
							this);

			KeyValueStore<String,Actor> keyValueStore =
					this.keyValueStoreFactory().newKeyValueStore(actorTypeName);

			typeElement = new ActorKeyValueTypeElement(mailbox, keyValueStore);

			this.actorTypeElements().put(actorTypeName, typeElement);
		}

		return typeElement;
	}

	/**
	 * Initializes my default state.
	 * @param anAddressFactory the AddressFactory I will use
	 * @param anActorAgentFactory the ActorAgentFactory I will use
	 * @param aMailboxFactory the MailboxFactory I will use
	 * @param aKeyValueStoreFactory the KeyValueStoreFactory<String,Actor> I will use
	 */
	private KeyValueStoreActorRegistry(
			AddressFactory anAddressFactory,
			ActorAgentFactory anActorAgentFactory,
			MailboxFactory aMailboxFactory,
			KeyValueStoreFactory<String,Actor> aKeyValueStoreFactory) {

		this.setAddressFactory(anAddressFactory);
		this.setActorAgentFactory(anActorAgentFactory);
		this.setActorTypeElements(new ConcurrentHashMap<String, ActorKeyValueTypeElement>());
		this.setKeyValueStoreFactory(aKeyValueStoreFactory);
		this.setMailboxFactory(aMailboxFactory);
		this.messageLog = new ArrayList<Message>();
	}

	/**
	 * Answers my actorAgentFactory.
	 * @return ActorAgentFactory
	 */
	private ActorAgentFactory actorAgentFactory() {
		return this.actorAgentFactory;
	}

	/**
	 * Sets my actorAgentFactory.
	 * @param anActorAgentFactory the ActorAgentFactory to set as my actorAgentFactory
	 */
	private void setActorAgentFactory(ActorAgentFactory anActorAgentFactory) {
		this.actorAgentFactory = anActorAgentFactory;
	}

	/**
	 * Answers my addressFactory.
	 * @return AddressFactory
	 */
	private AddressFactory addressFactory() {
		return addressFactory;
	}

	/**
	 * Sets my addressFactory.
	 * @param anAddressFactory the AddressFactory to set as my addressFactory
	 */
	private void setAddressFactory(AddressFactory anAddressFactory) {
		this.addressFactory = anAddressFactory;
	}

	/**
	 * Answers my actorTypeElements.
	 * @return Map<String, ActorTypeElement>
	 */
	private Map<String, ActorKeyValueTypeElement> actorTypeElements() {
		return this.actorTypeElements;
	}

	/**
	 * @param aMap the Map to set as my actorTypeElements
	 */
	private void setActorTypeElements(Map<String, ActorKeyValueTypeElement> aMap) {
		this.actorTypeElements = aMap;
	}

	/**
	 * @return the keyValueStoreFactory
	 */
	private KeyValueStoreFactory<String,Actor> keyValueStoreFactory() {
		return keyValueStoreFactory;
	}

	/**
	 * Sets my keyValueStoreFactory.
	 * @param aKeyValueStoreFactory the KeyValueStoreFactory<String,Actor> to set as my keyValueStoreFactory
	 */
	private void setKeyValueStoreFactory(KeyValueStoreFactory<String,Actor> aKeyValueStoreFactory) {
		this.keyValueStoreFactory = aKeyValueStoreFactory;
	}

	/**
	 * Answers my mailboxFactory.
	 * @return the MailboxFactory
	 */
	private MailboxFactory mailboxFactory() {
		return this.mailboxFactory;
	}

	/**
	 * Sets my mailboxFactory.
	 * @param aMailboxFactory the MailboxFactory to set as my mailboxFactory
	 */
	private void setMailboxFactory(MailboxFactory aMailboxFactory) {
		this.mailboxFactory = aMailboxFactory;
	}
}
