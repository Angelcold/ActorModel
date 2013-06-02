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

package co.vaughnvernon.actormodel.agilepm.domain.model.product;

import java.util.Date;

import co.vaughnvernon.actormodel.actor.ActorAgent;
import co.vaughnvernon.actormodel.actor.ActorInitializer;
import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.actor.BaseActor;
import co.vaughnvernon.actormodel.message.Message;

public class Sprint extends BaseActor {

	private Date begins;
    private Date ends;
    private String goals;
    private String name;
    private Address product;

    public Sprint(ActorInitializer anInitializer) {
		super(anInitializer.address(), anInitializer.registry());

		this.begins = anInitializer.getParameter("begins");
		this.ends = anInitializer.getParameter("ends");
		this.goals = anInitializer.getParameter("goals");
		this.name = anInitializer.getParameter("name");

		ActorAgent product = anInitializer.getParameter("product");

		this.product = product.address();

		product.tell(new SprintScheduled(product.address(), this.self().address()));
	}

	@Override
	public void reactTo(Message aMessage) {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "Sprint [name=" + name + ", goals=" + goals + ", begins="
				+ begins + ", ends=" + ends + ", product=" + product.address() + "]";
	}
}
