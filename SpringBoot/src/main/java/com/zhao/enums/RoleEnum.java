package com.zhao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN(1, "管理员", "ADMIN"),
    /**
     * 普通用户
     */
    USER(2, "用户", "USER"),
    /**
     * 测试账号
     */
    TEST(3, "测试", "TEST"),
    /**
     * 游客
     */
    GUEST(6, "游客", "GUEST");  // 数据库并不存在

    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 描述
     */
    private final String name;

    /**
     * 权限标签
     */
    private final String label;
}
