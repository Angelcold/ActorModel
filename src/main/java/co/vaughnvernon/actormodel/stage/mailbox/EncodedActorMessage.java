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
import co.vaughnvernon.actormodel.util.serializer.MessageSerializer;
import co.vaughnvernon.actormodel.util.serializer.ObjectSerializer;

/**
 * Defines a class to encode a Message and MessageInfo.
 *
 * @author Vaughn Vernon
 */
public class EncodedActorMessage {

	private String encoding;

	/**
	 * Initializes my default state.
	 * @param aSenderAgent the ActorAgent of the Actor sending the Message
	 * @param aReceiverAgent the ActorAgent of the Actor receiving the Message
	 * @param aMessage the Message being sent
	 * @param isAsking the boolean indicating whether or not the sending is asking for an answer
	 */
	public EncodedActorMessage(
			ActorAgent aSenderAgent,
			ActorAgent aReceiverAgent,
			Message aMessage,
			boolean isAsking) {

		super();

		String serializedMessage =
				MessageSerializer
					.instance()
					.serialize(aMessage);

		MessageInfo info =
				new MessageInfo(
						aSenderAgent,
						aReceiverAgent,
						aMessage,
						isAsking);

		String serializedInfo = ObjectSerializer.instance().serialize(info);

		StringBuffer buffer = new StringBuffer();

		buffer
			.append(serializedInfo.length())
			.append(',')
			.append(serializedInfo)
			.append(serializedMessage);

		this.encoding = buffer.toString();
	}

	/**
	 * Answers my encoding.
	 * @return String
	 */
	public String encoding() {
		return this.encoding;
	}
}
