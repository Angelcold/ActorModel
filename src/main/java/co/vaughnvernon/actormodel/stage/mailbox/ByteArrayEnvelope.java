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

import co.vaughnvernon.actormodel.message.Message;

/**
 * I define the envelope for a single byte[] representation of a Message.
 *
 * @author Vaughn Vernon
 */
public class ByteArrayEnvelope implements Envelope {

	private boolean delivered;
	private byte[] message;

	public ByteArrayEnvelope(byte[] aMessage) {
		super();

		this.setMessage(aMessage);
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Envelope#delivered()
	 */
	@Override
	public void delivered() {
		this.delivered = true;
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Envelope#isDelivered()
	 */
	@Override
	public boolean isDelivered() {
		return this.delivered;
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Envelope#length()
	 */
	@Override
	public int length() {
		return this.messageAsBytes().length;
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Envelope#message()
	 */
	@Override
	public Message message() {
		throw new UnsupportedOperationException("Use messageAsBytes() instead.");
	}

	/**
	 * @see co.vaughnvernon.actormodel.stage.mailbox.Envelope#message()
	 */
	@Override
	public byte[] messageAsBytes() {
		return message;
	}

	/**
	 * Sets my message.
	 * @param message the byte[] to set as my message
	 */
	private void setMessage(byte[] message) {
		this.message = message;
	}
}
