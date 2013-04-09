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

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * I serialize objects to JSON and deserialize from JSON
 * to objects.
 *
 * @author Vaughn Vernon
 */
public class AbstractSerializer {

    /** My JSON serialization tool. */
    private Gson gson;

    /**
     * Constructs my state for possible compact support.
     * @param isCompact the boolean indicating compact support, or non-compact
     */
    protected AbstractSerializer(boolean isCompact) {
        this(false, isCompact);
    }

    /**
     * Constructs my state for possible pretty and compact support.
     * @param isPretty the boolean indicating pretty support, or non-pretty
     * @param isCompact the boolean indicating compact support, or non-compact
     */
    protected AbstractSerializer(boolean isPretty, boolean isCompact) {
        super();

        if (isPretty && isCompact) {
            this.buildForPrettyCompact();
        } else if (isCompact) {
                this.buildForCompact();
        } else {
            this.build();
        }
    }

    /**
     * Answers my gson.
     * @return Gson
     */
    protected Gson gson() {
        return this.gson;
    }

    /**
     * Builds my Gson instance for default behavior.
     */
    private void build() {
        this.gson =
            new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .serializeNulls()
                .create();
    }

    /**
     * Builds my Gson instance for compact serialization behavior.
     */
    private void buildForCompact() {
        this.gson =
            new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();
    }

    /**
     * Builds my Gson instance for pretty and compact serialization behavior.
     */
    private void buildForPrettyCompact() {
        this.gson =
            new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .setPrettyPrinting()
                .create();
    }

    /**
     * I am a custom serializer for java.util.Date instances.
     * I convert Date instances into time-stamp values.
     *
     * @author Vaughn Vernon
     */
    private class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date source, Type typeOfSource, JsonSerializationContext context) {
            return new JsonPrimitive(Long.toString(source.getTime()));
        }
    }

    /**
     * I am a custom deserializer for java.util.Date instances.
     * I convert time-stamp values into Date instances.
     *
     * @author Vaughn Vernon
     */
    private class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context)
            throws JsonParseException {
                long time = Long.parseLong(json.getAsJsonPrimitive().getAsString());
                return new Date(time);
        }
    }
}
