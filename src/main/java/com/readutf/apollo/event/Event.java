package com.readutf.apollo.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class Event {

    private final Instant timestamp;
    private final Map<String, Number> values;
    private final Map<String, String> tags;

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", values=" + values +
                ", tags=" + tags +
                '}';
    }

    public static EventBuilder builder() {
        return new EventBuilder();
    }

    public static class EventBuilder {

        private Instant timestamp;
        private final Map<String, Number> values;
        private final Map<String, String> tags;

        private EventBuilder() {
            this.values = new HashMap<>();
            this.tags = new HashMap<>();
        }

        public EventBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public EventBuilder addValue(String key, Number value) {
            values.put(key, value);
            return this;
        }

        public EventBuilder addTag(String key, String value) {
            tags.put(key, value);
            return this;
        }

        public Event build() {
            return new Event(timestamp, values, tags);
        }

    }


}
