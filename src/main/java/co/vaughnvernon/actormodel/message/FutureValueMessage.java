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

import java.util.Date;

/**
 * I define a Message that transports Future answers
 * to FutureInterests.
 *
 * @author Vaughn Vernon
 */
public class FutureValueMessage implements Message {

	private Object answer;
	private Date occurredOn;

	/**
	 * Initializes my default state with anAnswerValue.
	 * @param anAnswerValue the Object value that is the answer
	 */
	public FutureValueMessage(Object anAnswerValue) {
		super();

		this.setAnswer(anAnswerValue);
		this.setOccurredOn(new Date());
	}

	/**
	 * Answers the Actors answer to the ask.
	 * @return Object
	 */
	public Object answer() {
		return this.answer;
	}

	/**
	 * Answers the Actors answer to the ask.
	 * @return the T typed Object
	 */
	@SuppressWarnings("unchecked")
	public <T> T typedAnswer() {
		return (T) this.answer;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}

	private void setAnswer(Object anAnswerValue) {
		this.answer = anAnswerValue;
	}

	private void setOccurredOn(Date occurredOn) {
		this.occurredOn = occurredOn;
	}
}
