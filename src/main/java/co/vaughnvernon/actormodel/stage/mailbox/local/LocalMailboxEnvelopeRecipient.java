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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.actor.ActorPersister;
import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.FutureValueMessage;
import co.vaughnvernon.actormodel.message.Message;
import co.vaughnvernon.actormodel.stage.mailbox.Envelope;
import co.vaughnvernon.actormodel.stage.mailbox.EnvelopeRecipient;
import co.vaughnvernon.actormodel.stage.mailbox.LocalMessageEnvelope;

/**
 * I implement an EnvelopeRecipient for local Mailbox instances.
 *
 * @author Vaughn Vernon
 */
public class LocalMailboxEnvelopeRecipient implements EnvelopeRecipient {

	private static final String WHEN_METHOD_NAME = "when";

	private static Map<String, Method> whenMethods = new HashMap<String, Method>();

	private ActorPersister actorPersister;

	/**
	 * Initialize my default state.
	 */
	public LocalMailboxEnvelopeRecipient(final ActorPersister anActorPersister) {
		super();

		this.setActorPersister(anActorPersister);
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.EnvelopeRecipient#receive(com.shiftmethod.actup.stage.mailbox.Envelope)
	 */
	@Override
	public void receive(Envelope anEnvelope) throws Exception {
		LocalMessageEnvelope local = (LocalMessageEnvelope) anEnvelope;

		Message message = local.message();

		Actor actor = local.receiverAgent().actor();

		if (actor.wantsSmartMessageDispatching()) {
			this.dispatch(actor, message);
		} else if (message instanceof Command) {
			actor.execute((Command) message);
			this.store(actor);
		} else if (message instanceof Event) {
			actor.handle((Event) message);
			this.store(actor);
		} else {
			if (local.isAsking()) {
				Object answer = actor.answer(message);
				// by convention, asking does not modify the actor
				local.senderAgent().tell(new FutureValueMessage(answer));
			} else {
				actor.reactTo(message);
				this.store(actor);
			}
		}
	}

	/**
	 * Answers my actorPersister.
	 *
	 * @return ActorPersister
	 */
	private ActorPersister actorPersister() {
		return this.actorPersister;
	}

	/**
	 * Sets my actorPersister.
	 *
	 * @param anActorPersister
	 *            the ActorPersister to set as my actorPersister
	 */
	private void setActorPersister(ActorPersister anActorPersister) {
		this.actorPersister = anActorPersister;
	}

	private Method cacheWhenMethodFor(String aKey,
			Class<? extends Actor> anActorType,
			Class<? extends Message> aMessageType) {

		synchronized (whenMethods) {
			try {
				Method method = this.publicOrHiddenMethod(anActorType,
						aMessageType);

				method.setAccessible(true);

				whenMethods.put(aKey, method);

				return method;

			} catch (Exception e) {
				throw new IllegalArgumentException(
							"Actor does not understand "
									+ WHEN_METHOD_NAME
									+ "("
									+ aMessageType.getSimpleName()
									+ ") because: "
									+ e.getClass().getSimpleName()
									+ ">>>"
									+ e.getMessage(),
							e);
			}
		}
	}

	private void dispatch(Actor anActor, Message aMessage) {

		Class<? extends Actor> actorType = anActor.getClass();

		Class<? extends Message> messageType = aMessage.getClass();

		String key = actorType.getName() + ":" + messageType.getName();

		Method whenMethod = whenMethods.get(key);

		if (whenMethod == null) {
			whenMethod = this.cacheWhenMethodFor(key, actorType, messageType);
		}

		try {
			whenMethod.invoke(anActor, aMessage);

		} catch (InvocationTargetException e) {
			if (e.getCause() != null) {
				throw new RuntimeException(
						"Method "
								+ WHEN_METHOD_NAME
								+ "("
								+ messageType.getSimpleName()
								+ ") failed. See cause: "
								+ e.getMessage(),
						e.getCause());
			}

			throw new RuntimeException(
						"Method "
							+ WHEN_METHOD_NAME
							+ "("
							+ messageType.getSimpleName()
							+ ") failed. See cause: "
							+ e.getMessage(),
						e);

		} catch (IllegalAccessException e) {
			throw new RuntimeException(
						"Method "
								+ WHEN_METHOD_NAME
								+ "("
								+ messageType.getSimpleName()
								+ ") failed because of illegal access. See cause: "
								+ e.getMessage(),
						e);
		}
	}

	private Method publicOrHiddenMethod(Class<? extends Actor> anActorType,
			Class<? extends Message> aMessageType) throws Exception {

		Method method = null;

		try {

			// assume public...

			method = anActorType.getMethod(WHEN_METHOD_NAME, aMessageType);

		} catch (Exception e) {

			// then protected or private...

			method = anActorType.getDeclaredMethod(WHEN_METHOD_NAME,
					aMessageType);
		}

		return method;
	}

	private void store(Actor anActor) {
		if (this.actorPersister().requiresValueUpdates()) {
			this.actorPersister().store(anActor);
		}
	}
}
