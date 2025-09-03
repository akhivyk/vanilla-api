package com.solvd.enums;

import lombok.Getter;

@Getter
public enum ContentType {
    TEXT("text/plain"),
    JSON("application/json", "application/javascript", "text/javascript", "text/json"),
    XML("application/xml", "text/xml", "application/xhtml+xml");

    ContentType(String... contentTypes) {
    }
}
