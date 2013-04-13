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

package co.vaughnvernon.actormodel.message;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ArrayMessage<T> implements Message {

	private Date occurredOn;
	private List<T> values;

	@SafeVarargs
	public ArrayMessage(final T... aValue) {
		super();

		this.occurredOn = new Date();
		this.values = Arrays.asList(aValue);
	}

	public final List<T> asList() {
		return Collections.unmodifiableList(this.values);
	}

	@Override
	public final Date occurredOn() {
		return this.occurredOn;
	}

	public final T value() {
		return this.values.get(0);
	}

	public final T value(int anIndex) {
		return this.values.get(anIndex);
	}

	@SuppressWarnings("unchecked")
	public final T[] values() {
		return (T[]) this.values.toArray();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()
				+ " [values=" + values + ", occurredOn=" + occurredOn + "]";
	}
}
