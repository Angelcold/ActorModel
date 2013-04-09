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

public interface Future {

	/**
	 * Answers whether or not the receiver is inoperable.
	 * Even a completed or timed out Future may be operable.
	 * This allows a special case of a Future that will
	 * complete, but will not provide a meaningful value.
	 * A true answer indicates that the Future is a NullFuture.
	 * @return boolean
	 */
	public boolean isInoperable();

	/**
	 * Answers the ActorAgent of the receiver of the ask request.
	 * The receivers ActorAgent is an agent of the Actor itself.
	 * @return ActorAgent
	 */
	public ActorAgent receiver();

	/**
	 * Answers the ActorAgent of the sender requesting the Future.
	 * If the sender is not itself an Actor, the answer is a no-op
	 * ActorAgent.
	 * @return ActorAgent
	 */
	public ActorAgent sender();

	/**
	 * Answers whether or not I have timed out.
	 * @return boolean
	 */
	public boolean timedOut();

	/**
	 * Answers my result value as the type T.
	 * @return T
	 */
	public <T> T value();
}
