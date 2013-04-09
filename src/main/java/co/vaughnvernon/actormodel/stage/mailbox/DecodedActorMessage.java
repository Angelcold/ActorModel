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

import co.vaughnvernon.actormodel.actor.Actor;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.message.Message;
import co.vaughnvernon.actormodel.util.serializer.ObjectSerializer;

/**
 * Defines a class to decode a Message being sent to an Actor,
 * and supply the various parts.
 *
 * @author Vaughn Vernon
 */
public class DecodedActorMessage {

	private Message message;
	private MessageInfo messageInfo;

	/**
	 * Initializes my default state.
	 * @param aMessage the Message to set as my message
	 * @param aMessageInfo the MessageInfo to set as my messageInfo
	 */
	public DecodedActorMessage(Message aMessage, MessageInfo aMessageInfo) {
		super();

		this.message = aMessage;
		this.messageInfo = aMessageInfo;
	}

	/**
	 * Initializes my default state.
	 * @param aTextMessage the String encoded message
	 * @throws Exception
	 */
	public DecodedActorMessage(String aTextMessage) throws Exception {
		super();

        int separatorPos = aTextMessage.indexOf(',');

        String messageInfoLengthEncoding =
        		aTextMessage.substring(0, separatorPos);

        int messageInfoLength =
        		Integer.parseInt(messageInfoLengthEncoding);

        int messageInfoPos = separatorPos + 1;

        String messageInfoBuffer =
        		aTextMessage.substring(messageInfoPos, messageInfoPos + messageInfoLength);

        this.messageInfo =
        		ObjectSerializer
        			.instance()
        			.deserialize(
        					messageInfoBuffer,
        					MessageInfo.class);

        this.message =
        		ObjectSerializer
        			.instance()
        			.deserialize(
        					aTextMessage.substring(messageInfoPos + messageInfoLength),
        					this.messageType());
	}

	/**
	 * Answers my decoded message.
	 * @return Message
	 */
	public Message message() {
		return this.message;
	}

	/**
	 * Answers my decoded messageInfo.
	 * @return MessageInfo
	 */
	public MessageInfo messageInfo() {
		return this.messageInfo;
	}

	public Actor toActor() {
		return null;
	}

	/**
	 * Answers the Address of the Actor receiving the Message.
	 * @return Address
	 */
	public Address toAddress() {
		return Address.instanceFrom(this.messageInfo.toAddress());
	}

	/**
	 * Answers the Class of the Actor receiving the Message.
	 * @return Class<? extends Actor>
	 */
	@SuppressWarnings("unchecked")
	public Class<? extends Actor> toActorType() throws Exception {
        Class<? extends Actor> actorType =
        		(Class<? extends Actor>) Class.forName(this.messageInfo.toActorType());

		return actorType;
	}

	/**
	 * Answers the type of the Message being sent.
	 * @return Class<? extends Message>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Class<? extends Message> messageType() throws Exception {
        Class<? extends Message> messageType =
        		(Class<? extends Message>) Class.forName(this.messageInfo.messageType());

		return messageType;
	}
}
