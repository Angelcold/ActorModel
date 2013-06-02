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

import co.vaughnvernon.actormodel.message.Message;

public class GetSprintNamed implements Message {

	private String name;
	private Date occurredOn;

	public GetSprintNamed(String aName) {
		super();

		this.name = aName;
		this.occurredOn = new Date();
	}

	public String name() {
		return this.name;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}
}
