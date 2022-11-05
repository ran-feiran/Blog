package com.zhao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SearchConstEnum {

    ARTICLE("article","_doc"),

    TEST("test","_doc");

    private final String index;

    private final String type;
}
