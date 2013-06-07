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


/**
 * I define an interface for finding Actors.
 *
 * @author Vaughn Vernon
 */
public interface ActorFinder {

	/**
	 * Answers the Actor of anActorType and anAddress.
	 * @param anActorType the Class<? extends Actor> type of the Actor
	 * @param anAddress the Address of the Actor
	 * @return Actor
	 */
	public Actor findActorBy(Class<? extends Actor> anActorType, Address anAddress);

	/**
	 * Answers the first ActorAgent of anActorType matching aQuery.
	 * @param anActorType the Class<? extends Actor> type of the Actor
	 * @param aQuery the Query to run against each Actor
	 * @return ActorAgent
	 */
	public ActorAgent findFirstMatching(Class<? extends Actor> anActorType, Query aQuery);
}
