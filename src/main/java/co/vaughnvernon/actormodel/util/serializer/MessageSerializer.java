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

package co.vaughnvernon.actormodel.util.serializer;

import co.vaughnvernon.actormodel.message.Message;

/**
 * I serialize Events to JSON and deserialize from JSON
 * to Events.
 *
 * @author Vaughn Vernon
 */
public class MessageSerializer extends AbstractSerializer {

    /** My default, singleton instance. */
    private static MessageSerializer eventSerializer;

    /**
     * Answers the singleton instance of the default EventSerializer,
     * which supports non-compact and non-pretty.
     * @return EventSerializer
     */
    public static synchronized MessageSerializer instance() {
        if (MessageSerializer.eventSerializer == null) {
            MessageSerializer.eventSerializer = new MessageSerializer();
        }

        return MessageSerializer.eventSerializer;
    }

    /**
     * Constructs my state for possible compact support.
     * @param isCompact the boolean indicating compact support, or non-compact
     */
    public MessageSerializer(boolean isCompact) {
        this(false, isCompact);
    }

    /**
     * Constructs my state for possible pretty and compact support.
     * @param isPretty the boolean indicating pretty support, or non-pretty
     * @param isCompact the boolean indicating compact support, or non-compact
     */
    public MessageSerializer(boolean isPretty, boolean isCompact) {
        super(isPretty, isCompact);
    }

    /**
     * Answers the serialized String representation of aMessage.
     * @param aMessage the Message to serialize
     * @return String
     */
    public String serialize(Message aMessage) {
        String serialization = this.gson().toJson(aMessage);

        return serialization;
    }

    /**
     * Answers the concrete Message instance deserialized from
     * aSerialization as an instance of aType.
     * @param aSerialization the String serialized representation
     * @param aType the Class<T> concrete type of the Event
     * @return the Message concrete implementor instance
     */
    public <T extends Message> T deserialize(String aSerialization, final Class<T> aType) {
        T message = this.gson().fromJson(aSerialization, aType);

        return message;
    }

    /**
     * Constructs my default state.
     */
    private MessageSerializer() {
        this(false, false);
    }
}
