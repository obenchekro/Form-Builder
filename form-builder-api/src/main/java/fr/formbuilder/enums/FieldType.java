package fr.formbuilder.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum FieldType {
    TEXT("text"),
    EMAIL("email"),
    NUMBER("number"),
    DATE("date"),
    TEXTAREA("textarea"),
    SELECT("select"),
    SWITCH("switch"),
    RADIO("radio"),
    CHECKBOX("checkbox"),
    FILE("file");

    private final String json;
    FieldType(String json) {
        this.json = json;
    }

    @JsonValue
    public String toJson() {
        return json;
    }

    @JsonCreator
    public static FieldType from(String fieldType) {
        String trueType = fieldType == null ? null : fieldType.toLowerCase(Locale.ROOT);
        for (FieldType type : values()) if (type.json.equals(trueType)) return type;
        throw new IllegalArgumentException("Unknown FieldType: " + fieldType);
    }
}
