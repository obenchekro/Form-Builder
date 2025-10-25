package fr.formbuilder.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.util.Locale;

public enum FieldKind {
    INPUT("input"),
    SELECT("select"),
    SELECT_FILTER("select-filter");

    @Getter
    private final String json;

    FieldKind(String json) {
        this.json = json;
    }

    @JsonValue
    public String toJson() {
        return json;
    }

    @JsonCreator
    public static FieldKind from(String formKind) {
        String trueKind = formKind == null ? null : formKind.toLowerCase(Locale.ROOT);
        for (FieldKind kind : values()) if (kind.getJson().equals(trueKind)) return kind;
        throw new IllegalArgumentException("Unknown FormKind: " + formKind);
    }
}