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

import java.io.Serializable;

/**
 * I define the address of an Actor.
 *
 * @author Vaughn Vernon
 */
public final class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address;

	/**
	 * Answers a new, immutable, Address using anAddress.
	 * @param anAddress the String address of the actor
	 * @return Address
	 */
	public static Address instanceFrom(String anAddress) {
		return new Address(anAddress);
	}

	/**
	 * Answers a new, immutable, unique Address.
	 * @return Address
	 */
	public static Address newNullInstance() {
		return new Address(null);
	}

	/**
	 * Answers my address.
	 * @return String
	 */
	public String address() {
		return address;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((this.address() == null) ? 0 : this.address().hashCode());

		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object anObject) {
		boolean equals = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			Address typedObject = (Address) anObject;
			if (this.address() == null) {
				equals = typedObject.address() == null;
			} else {
				equals = this.address().equals(typedObject.address());
			}
		}

		return equals;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address=" + this.address();
	}

	/**
	 * Constructs my default state with anId.
	 * @param anAddress the String id to set as my id
	 */
	private Address(String anAddress) {
		super();

		this.setAddress(anAddress);
	}

	/**
	 * Sets my address.
	 * @param anAddress the String to set as my address
	 */
	private void setAddress(String anAddress) {
		this.address = anAddress;
	}
}
