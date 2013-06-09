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

import co.vaughnvernon.actormodel.stage.mailbox.Envelope;
import co.vaughnvernon.actormodel.stage.mailbox.EnvelopeRecipient;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;
import co.vaughnvernon.actormodel.stage.mailbox.Throttle;

/**
 * Implements a Mailbox using a ring buffer data structure, and that
 * is managed by a single Thread. Currently messages for all actor
 * instances of a given type are delivered through a single mailbox.
 *
 * @author Vaughn Vernon
 */
public class ThreadManagedRingBufferMailbox extends Thread implements Mailbox {

	private static final int MINIMUM_CAPACITY = 1024;

	/** My closed indicator. */
	private boolean closed;

	/** The position in my ring to which a new Envelope is placed. */
	private int inIndex;

	/** My lock. */
	private Object lock;

	/** My ring buffer of messages. */
	private Envelope[] messages;

	/** The position in the ring from which the next message is taken. */
	private int outIndex;

	/** The recipient of my received Envelopes. */
	private EnvelopeRecipient recipient;

	/** My throttle. */
	private Throttle throttle;

	/**
	 * Initializes my default state.
	 * @param aName the String to name me
	 * @param anInitialCapacity the int indicating my initial capacity for messages
	 * @param aMaximumThrottledMessages the int to set as my throttleCount
	 * @param aRecipient the EnvelopeRecipient that receives all messages
	 */
	public ThreadManagedRingBufferMailbox(
			String aName,
			int anInitialCapacity,
			int aMaximumThrottledMessages,
			EnvelopeRecipient aRecipient) {

		super(aName);

		this.lock = new Object();

		if (anInitialCapacity < MINIMUM_CAPACITY) {
			anInitialCapacity = MINIMUM_CAPACITY;
		}

		this.messages = new Envelope[anInitialCapacity];

		this.recipient = aRecipient;

		this.throttle = new Throttle(aMaximumThrottledMessages);

		this.start();
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Mailbox#name()
	 */
	@Override
	public String name() {
		return this.getName();
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Mailbox#receive(co.vaughnvernon.actormodel.stage.mailbox.Envelope)
	 */
	@Override
	public void receive(Envelope anEnvelope) {

		boolean mustYield = false;
		boolean received = false;

		while (!received) {

			synchronized (this.lock) {
				mustYield = this.overlapping();

				if (!mustYield) {
					received = true;

					this.messages[this.inIndex] = anEnvelope;

					this.incrementInIndex();
				}
			}

			if (mustYield) {
				this.interrupt();

				this.yieldFor(1L);
			}
		}

		this.interrupt();
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Mailbox#close()
	 */
	@Override
	public void close() {
		this.closed = true;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		boolean mustYield = true;

		while (!this.isClosed()) {
			synchronized (this.lock) {
				if (this.hasMessage()) {
					Envelope envelope = this.messages[this.outIndex];

					this.messages[this.outIndex] = null;

					try {
						this.recipient.receive(envelope);
					} catch (Exception e) {
						// TODO
						System.out.println("Mailbox Error: RECIPIENT: " + e.getMessage());
						e.printStackTrace(System.out);
					}

					this.incrementOutIndex();

					mustYield = !this.throttle.stillThrottling(this.hasMessage());

				} else {
					mustYield = true;
				}
			}

			if (mustYield) {
				this.yieldFor(10L);
			}
		}
	}

	/**
	 * Answers whether or not there is one or more messages queued.<br/><br/>
	 * NOTE: It is possible for the input to the ring to lap the output from
	 * the ring, in which case outIndex may be equal to inIndex, but in that
	 * case the message[outIndex] will not be null. If this test is not done
	 * it can lead to a sort of deadlock.
	 * @return boolean
	 */
	private boolean hasMessage() {
		return this.messages[this.outIndex] != null;
	}

	/**
	 * Increments my inIndex.
	 */
	private void incrementInIndex() {
		if (++this.inIndex >= this.messages.length) {
			this.inIndex = 0;
		}
	}

	/**
	 * Increments my outIndex.
	 */
	private void incrementOutIndex() {
		if (++this.outIndex >= this.messages.length) {
			this.outIndex = 0;
		}
	}

	/**
	 * Answers whether or not I am closed.
	 * @return boolean
	 */
	private boolean isClosed() {
		return closed;
	}

	/**
	 * Answers whether or not the ring has gotten in to
	 * an overlapping situation, which is indicated by
	 * the input location having an active Envelope.
	 * @return boolean
	 */
	private boolean overlapping() {
		try {
			return this.messages[this.inIndex] != null;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Causes the current thread to yield for aMillis.
	 * @param aMillis the long milliseconds to yield
	 */
	private void yieldFor(long aMillis) {
		try {
			Thread.sleep(aMillis);
		} catch (InterruptedException e) {
			// ignore
		}
	}
}
