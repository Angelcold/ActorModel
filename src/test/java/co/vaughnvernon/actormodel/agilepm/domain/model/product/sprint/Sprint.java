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

package co.vaughnvernon.actormodel.agilepm.domain.model.product.sprint;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.actor.Query;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.SprintScheduled;
import co.vaughnvernon.actormodel.agilepm.domain.model.product.backlogitem.BacklogItemCommitted;
import co.vaughnvernon.actormodel.message.StringMessage;

public class Sprint extends BaseActor {

	private Date begins;
	private Set<Address> committedBacklogItems;
    private Date ends;
    private String goals;
    private String name;
    private Address product;

    public Sprint(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.begins = anInitializer.getParameter("begins");
		this.committedBacklogItems = new HashSet<Address>();
		this.ends = anInitializer.getParameter("ends");
		this.goals = anInitializer.getParameter("goals");
		this.name = anInitializer.getParameter("name");

		ActorAgent product = anInitializer.getParameter("product");

		this.product = product.address();

		product.tell(new SprintScheduled(product.address(), this.self().address()));
	}

	@Override
	public boolean matches(Query aQuery) {
		System.out.println("Matches: name: " + this.name + " == " + aQuery.getParameter("name"));

		if (this.name.equals(aQuery.getParameter("name"))) {
			return true;
		}

		return false;
	}

	@Override
	public boolean wantsFilteredDelivery() {
		return true;
	}

	public void when(BacklogItemCommitted anEvent) {
		System.out.println("Sprint when(BacklogItemCommitted)");

		this.committedBacklogItems.add(anEvent.backlogItem());
	}

	public void when(StringMessage aMessage) {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "Sprint [name=" + name + ", goals=" + goals + ", begins="
				+ begins + ", ends=" + ends + ", product=" + product.address()
				+ ", committedBacklogItems=" + this.committedBacklogItems.size() + "]";
	}
}
