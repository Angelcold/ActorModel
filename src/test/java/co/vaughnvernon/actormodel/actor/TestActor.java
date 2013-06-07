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

public class TestActor extends BaseActor {

	// private int count = 1;
	private boolean answered;
	private boolean executed;
	private boolean handled;
	private boolean reactedTo;

	public TestActor(ActorInitializer anInitializer) {
		super(anInitializer);
	}

	@Override
	public Object answer(Message aMessage) {
		this.answered = true;

		// System.out.print("answered: " + (count++) + " ");

		return "answer to asking: 1, 2, 3";
	}

	@Override
	public void execute(Command aCommand) {
		this.executed = true;

		System.out.println("executed command");
	}

	@Override
	public void handle(Event anEvent) {
		this.handled = true;

		System.out.println("handled event");
	}

	@Override
	public boolean matches(Query aQuery) {
		return false; // not implemented
	}

	@Override
	public void reactTo(Message aMessage) {
		this.reactedTo = true;

		System.out.println("reacted to message");
	}

	public boolean isAnswered() {
		return this.answered;
	}

	public boolean isExecuted() {
		return this.executed;
	}

	public boolean isHandled() {
		return this.handled;
	}

	public boolean isReactedTo() {
		return this.reactedTo;
	}

	@Override
	public boolean wantsFilteredDelivery() {
		return false;
	}
}
