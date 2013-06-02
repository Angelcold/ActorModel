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

import co.vaughnvernon.actormodel.actor.Address;
import co.vaughnvernon.actormodel.message.Event;

public class SprintScheduled implements Event {

	private Date occurredOn;
	private Address product;
	private Address sprint;

	public SprintScheduled(Address aProduct, Address aSprint) {
		super();

		this.occurredOn = new Date();
		this.product = aProduct;
		this.sprint = aSprint;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}

	public Address product() {
		return this.product;
	}

	public Address sprint() {
		return this.sprint;
	}
}
