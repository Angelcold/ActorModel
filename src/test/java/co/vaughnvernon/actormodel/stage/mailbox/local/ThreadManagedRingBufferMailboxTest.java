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

package co.vaughnvernon.actormodel.stage.mailbox.local;

import java.util.Date;

import co.vaughnvernon.actormodel.actor.ActorTestCase;
import co.vaughnvernon.actormodel.stage.mailbox.ByteArrayEnvelope;
import co.vaughnvernon.actormodel.stage.mailbox.Envelope;
import co.vaughnvernon.actormodel.stage.mailbox.EnvelopeRecipient;
import co.vaughnvernon.actormodel.stage.mailbox.Mailbox;

public class ThreadManagedRingBufferMailboxTest extends ActorTestCase {

	private int receivedCount;
	private int sentCount;

	public void testBasicReceive() throws Exception {
		Mailbox mailbox =
				new ThreadManagedRingBufferMailbox(
						"TestOne",
						0,
						0,
						new EnvelopeRecipient() {
							@Override
							public void receive(Envelope anEnvelope) {
								assertEquals(1, anEnvelope.length());
								++receivedCount;
							}
						});

		++sentCount;

		mailbox.receive(new ByteArrayEnvelope(new byte[1]) {});

		this.stayAlive(1L);

		assertEquals(sentCount, receivedCount);
	}

	public void testReceiveMany() throws Exception {
		Mailbox mailbox =
				new ThreadManagedRingBufferMailbox(
						"TestMany",
						4096,
						20,
						new EnvelopeRecipient() {
							@Override
							public void receive(Envelope anEnvelope) {
								assertEquals(100, anEnvelope.length());
								++receivedCount;
							}
						});

		long beforeReceive = new Date().getTime();

		for ( ; sentCount < 2000000; ++sentCount) {
			mailbox.receive(new ByteArrayEnvelope(new byte[100]) {});
		}

		this.stayAlive(1000L);

		long totalTime = new Date().getTime() - beforeReceive;

		System.out.println("Received: " + receivedCount + " in milliseconds: " + totalTime);
		System.out.println("----------");

		assertEquals(sentCount, receivedCount);
	}

	public void testExecute15Senders() throws Exception {

		final int maxActors = 15;
		final long millis = 2000;

		Mailbox mailbox =
				new ThreadManagedRingBufferMailbox(
						"TestMailboxSenderThread",
						1024,
						20,
						new EnvelopeRecipient() {
							@Override
							public void receive(Envelope anEnvelope) {
								assertEquals(100, anEnvelope.length());
								++receivedCount;
							}
						});

		long beforeReceive = new Date().getTime();

		MailboxSenderThread[] threads = new MailboxSenderThread[maxActors];

		for (int i = 0; i < maxActors; ++i) {
			threads[i] = new MailboxSenderThread(mailbox);
			threads[i].start();
		}

		this.stayAlive(millis);

		long totalTime = new Date().getTime() - beforeReceive;

		for (int i = 0; i < maxActors; ++i) {
			sentCount += threads[i].sendCount();
		}

		System.out.println("Total time in milliseconds: " + totalTime);
		System.out.println("Send count: " + sentCount + " Average/Second: " + (sentCount / millis * 1000L));
		System.out.println("Receive count: " + receivedCount + " Average/Second: " + (receivedCount / millis * 1000L));
		System.out.println("----------");
	}

	public static class MailboxSenderThread extends Thread {

		private Mailbox mailbox;
		private int sendCount;

		public MailboxSenderThread(Mailbox aMailbox) {
			super();

			this.mailbox = aMailbox;
		}

		public int sendCount() {
			return this.sendCount;
		}

		@Override
		public void run() {
			for ( ; sendCount < 100000; ++sendCount) {
				mailbox.receive(new ByteArrayEnvelope(new byte[100]) {});
			}
		}
	}
}
