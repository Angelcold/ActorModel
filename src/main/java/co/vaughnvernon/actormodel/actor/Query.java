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

import java.util.HashMap;
import java.util.Map;

public class Query {

	private Map<String, Object> parameters;

	public Query() {
		super();

		this.parameters = new HashMap<String,Object>();
	}

	public Query(String aName, Object aValue) {
		this();

		this.putParameter(aName, aValue);
	}

	@SuppressWarnings("unchecked")
	public <T> T getParameter(String aName) {
		return (T) this.parameters.get(aName);
	}

	public void putParameter(String aName, Object aValue) {
		this.parameters.put(aName, aValue);
	}
}
