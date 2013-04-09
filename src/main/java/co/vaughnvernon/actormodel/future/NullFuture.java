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

package co.vaughnvernon.actormodel.future;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.NullActorAgent;

/**
 * Implements a NullObject version of Future.
 *
 * @author Vaughn Vernon
 */
public class NullFuture implements Future {

	private static NullFuture nullFuture = new NullFuture();

	/** My nullActorAgent, which is a NullObject. */
	private ActorAgent nullActorAgent;

	/**
	 * Answers an instance of a NullFuture.
	 * @return Future
	 */
	public static Future instance() {
		return nullFuture;
	}

	/**
	 * Always answers true.
	 */
	@Override
	public boolean isInoperable() {
		return true;
	}

	/**
	 * Answers my nullActorAgent as my receiver ActorAgent.
	 */
	@Override
	public ActorAgent receiver() {
		return this.nullActorAgent;
	}

	/**
	 * Answers my nullActorAgent as my sender ActorAgent.
	 */
	@Override
	public ActorAgent sender() {
		return this.nullActorAgent;
	}

	/**
	 * Always answers false.
	 */
	@Override
	public boolean timedOut() {
		return false;
	}

	/**
	 * Always answers null.
	 */
	@Override
	public <T> T value() {
		return null;
	}

	/**
	 * Constructs my default state.
	 */
	private NullFuture() {
		super();

		this.nullActorAgent = NullActorAgent.instance();
	}
}
