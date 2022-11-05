package com.zhao.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    EMAIL(0,"email","邮箱"),

    QQ(1,"QQ","qqLoginStrategyImpl");

    private final Integer id;

    private final String type;

    private final String name;

    public static String getName(Integer id) {
        for (LoginTypeEnum value : LoginTypeEnum.values()) {
            if (value.getId().equals(id)) {
                return value.getName();
            }
        }
        return null;
    }
}
