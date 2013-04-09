This is a preview of my experimental Actor Model grid.

Initially I am releasing only the local mailbox and
hash map persistence implementation. One interesting
thing about this is the high-performance local mailbox
implementation, namely ThreadManagedRingBufferMailbox.
I am seeing in the range of 1.5 million messages per
second passed through the mailbox, including ask/answer
future-based messaging. (Yes, the grid supports ask/answer
via futures.)

For now see the tests found here to for how the various
actor features work: LocalMailboxActorMessagingTest

I intend to release additional features including multi-
node grid support and various key-value and event
sourcing actor persistence mechanisms. That's going to
require a bit more time.

Vaughn Vernon
Author: Implementing Domain-Driven Design
http://vaughnvernon.co/?page_id=168
Twitter: @VaughnVernon

