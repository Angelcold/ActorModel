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

package co.vaughnvernon.actormodel.stage.mailbox;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.message.Message;

/**
 * I define an Envelope for a single local, in-memory Message.
 *
 * @author Vaughn Vernon
 */
public class LocalMessageEnvelope implements Envelope {

	private boolean asking;
	private boolean delivered;
	private Message message;
	private ActorAgent receiverAgent;
	private ActorAgent senderAgent;

	/**
	 * Constructs my default state.
	 * @param aMessage the Message to set as my message
	 */
	public LocalMessageEnvelope(
			ActorAgent aSenderAgent,
			ActorAgent aReceiverAgent,
			Message aMessage,
			boolean isAsking) {
		super();

		this.asking = isAsking;
		this.receiverAgent = aReceiverAgent;
		this.senderAgent = aSenderAgent;

		this.setMessage(aMessage);
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.Envelope#delivered()
	 */
	@Override
	public void delivered() {
		this.delivered = true;
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.Envelope#isDelivered()
	 */
	@Override
	public boolean isDelivered() {
		return this.delivered;
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.Envelope#length()
	 */
	@Override
	public int length() {
		return 1;
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.Envelope#message()
	 */
	@Override
	public Message message() {
		return this.message;
	}

	/**
	 * @see com.shiftmethod.actup.stage.mailbox.Envelope#messageAsBytes()
	 */
	@Override
	public byte[] messageAsBytes() {
		throw new UnsupportedOperationException("Use message() instead.");
	}

	public boolean isAsking() {
		return this.asking;
	}

	public ActorAgent receiverAgent() {
		return this.receiverAgent;
	}

	public ActorAgent senderAgent() {
		return this.senderAgent;
	}

	/**
	 * Sets my message.
	 * @param aMessage the Message to set as my Message
	 */
	private void setMessage(Message aMessage) {
		this.message = aMessage;
	}
}
