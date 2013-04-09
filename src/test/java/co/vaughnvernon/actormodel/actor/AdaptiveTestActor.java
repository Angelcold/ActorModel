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

import co.vaughnvernon.actormodel.message.Command;
import co.vaughnvernon.actormodel.message.Event;
import co.vaughnvernon.actormodel.message.Message;

public abstract class AdaptiveTestActor implements Actor {

	private Address address;

	protected AdaptiveTestActor(Address anAddress) {
		super();

		this.address = anAddress;
	}

	@Override
	public Address address() {
		return this.address;
	}

	@Override
	public String actorType() {
		return this.getClass().getName();
	}

	@Override
	public Object answer(Message aMessage) {
		return null;
	}

	@Override
	public void execute(Command aCommand) {
	}

	@Override
	public void handle(Event anEvent) {
	}

	@Override
	public void reactTo(Message aMessage) {
	}

	@Override
	public ActorAgent self() {
		return null;
	}

	@Override
	public ActorAgent sender() {
		return null;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}
}
