package com.solvd.enums;

import lombok.Getter;

@Getter
public enum ContentTypeEnum {
    TEXT("text/plain"),
    JSON("application/json", "application/javascript", "text/javascript", "text/json"),
    XML("application/xml", "text/xml", "application/xhtml+xml");

    ContentTypeEnum(String... contentTypes) {
    }
}
