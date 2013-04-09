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

package co.vaughnvernon.actormodel.stage.mailbox;

public class Throttle {

	/** How many messages to throttle out of the queue if available. */
	private int maximumThrottledMessages;

	/** My count of how many messages have been throttled. */
	private int throttledMessages;

	public Throttle(int aMaximumThrottledMessages) {
		super();

		this.maximumThrottledMessages =
				aMaximumThrottledMessages <= 0 ? 1 : aMaximumThrottledMessages;
	}

	/**
	 * Answers whether or not more messages may be throttled out
	 * of the queue at this time.
	 * @return boolean
	 */
	public boolean stillThrottling(boolean anyMessages) {
		boolean still = false;

		if (anyMessages) {
			if (++this.throttledMessages < this.maximumThrottledMessages) {
				still = true;
			}
		}

		if (!still) {
			this.throttledMessages = 0;
		}

		return still;
	}
}
