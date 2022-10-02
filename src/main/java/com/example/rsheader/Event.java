package com.example.rsheader;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Event {
    private Schema schema;
    private Payload payload;

    @Data
    public static class Schema {
        private String type;
        private Boolean optional;
        private String name;
        private List<Field> fields;

        @Data
        public static class Field {
            private String type;
            private Boolean optional;
            private String name;
            private String field;
            private List<Field> fields;
        }
    }

    @Data
    public static class Payload {
        private Map<String, Object> before;
        private Map<String, Object> after;
        private Map<String, Object> source;
        private String op;
        private Long ts_ms;
    }

}
