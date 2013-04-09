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

public class MessageInfo {

	private boolean asking;
	private String fromAddress;
	private String fromActorType;
	private String messageType;
	private String toAddress;
	private String toActorType;

	public MessageInfo(
			ActorAgent aFromAgent,
			ActorAgent aToAgent,
			Message aMessage,
			boolean isAsking) {

		super();

		this.setFromAddress(aFromAgent.address().address());
		this.setFromActorType(aFromAgent.actorType());

		this.setToAddress(aToAgent.address().address());
		this.setToActorType(aToAgent.actorType());

		this.setMessageType(aMessage.getClass().getName());

		this.setAsking(isAsking);
	}

	public MessageInfo() {
		super();
	}

	public boolean isAsking() {
		return this.asking;
	}

	public ActorAgent fromActorAgent() {
		return null;
	}

	public ActorAgent toActorAgent() {
		return null;
	}

	public String fromAddress() {
		return fromAddress;
	}

	public String fromActorType() {
		return fromActorType;
	}

	public String messageType() {
		return messageType;
	}

	public String toAddress() {
		return toAddress;
	}

	public String toActorType() {
		return toActorType;
	}

	private void setAsking(boolean isAsking) {
		this.asking = isAsking;
	}

	private void setFromAddress(String aFromAddress) {
		this.fromAddress = aFromAddress;
	}

	private void setFromActorType(String fromActorType) {
		this.fromActorType = fromActorType;
	}

	private void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	private void setToAddress(String aToAddress) {
		this.toAddress = aToAddress;
	}

	private void setToActorType(String toActorType) {
		this.toActorType = toActorType;
	}
}
