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

import co.vaughnvernon.actormodel.message.Message;
import co.vaughnvernon.actormodel.message.TestCommand;
import co.vaughnvernon.actormodel.message.TestEvent;
import co.vaughnvernon.actormodel.message.TestMessage;
import co.vaughnvernon.actormodel.message.TestRawThroughputCommand;


public class TestAggregateActor extends AdaptiveTestActor {

	// private int count = 1;
	private boolean answered;
	private boolean executed;
	private boolean handled;
	private boolean reactedTo;

	public TestAggregateActor(ActorInitializer anInitializer) {
		super((Address) anInitializer.address());
	}

	@Override
	public Object answer(Message aMessage) {
		this.answered = true;

		// System.out.print("answered: " + (count++) + " ");

		return "answer to asking: 1, 2, 3";
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

	public void when(TestCommand aCommand) {
		this.executed = true;

		System.out.println("executed when(command)");
	}

	public void when(TestEvent anEvent) {
		this.handled = true;

		System.out.println("handled when(event)");
	}

	public void when(TestMessage aMessage) {
		this.reactedTo = true;

		System.out.println("reacted to when(message)");
	}

	public void when(TestRawThroughputCommand aCommand) {
		this.executed = true;
	}

	@Override
	public boolean wantsSmartMessageDispatching() {
		return true;
	}
}
