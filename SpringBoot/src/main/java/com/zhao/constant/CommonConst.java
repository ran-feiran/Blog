package com.zhao.constant;

import org.springframework.stereotype.Component;

/**
 * 常见常量
 *
 * @author ran-feiran
 * @date 2022/09/24
 */
@Component
public class CommonConst {

    /**
     * 是
     */
    public static final int TRUE = 1;

    /**
     * 否
     */
    public static final int FALSE = 0;

    /**
     * 是
     */
    public static final boolean BOOL_TRUE = true;

    /**
     * 否
     */
    public static final boolean BOOL_FALSE = false;

    /**
     * 高亮标签
     */
    public static final String PRE_TAG = "<span style='color:#f47466;'>";

    /**
     * 高亮标签
     */
    public static final String POST_TAG = "</span>";


    /**
     * 内容类型
     */
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";


    /**
     * 布局
     */
    public static final String LAYOUT = "Layout";


    /**
     * 省
     */
    public static final String PROVINCE = "省";

    /**
     * 市
     */
    public static final String CITY = "市";

    /**
     * 未知
     */
    public static final String UNKNOWN = "未知";

    /**
     * 测试标签
     */
    public static final Integer TEST_TAG = 5;

    /**
     * 测试分类
     */
    public static final Integer TEST_CATEGORY = 1;

    /**
     * 默认的配置id
     */
    public static final Integer DEFAULT_CONFIG_ID = 1;

    /**
     * 邮件主题
     */
    public static final String EMAIL_SUBJECT = "【一个简单的技术分享页】";

    /**
     * 电子邮件文本
     */
    public static final String EMAIL_TEXT_PRE = "您的验证码为:  <b style='color:orange;font-size:30px;margin-left:5px;margin-right:5px'>";

    /**
     * 电子邮件文本后
     */
    public static final String EMAIL_TEXT_POST = "</b> ，有效期15分钟，请勿泄露他人，如非本人操作，请忽略此信息。";

    /**
     * 得到
     */
    public static final String GET = "GET";

    /**
     * 帖子
     */
    public static final String POST = "POST";

    /**
     * 把
     */
    public static final String PUT = "PUT";

    /**
     * 删除
     */
    public static final String DELETE = "DELETE";

    /**
     * 匿名用户
     */
    public static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * 禁用
     */
    public static final String DISABLE = "disable";

    /**
     * 消息通知
     */
    public static final String MESSAGE_NOTICE = "发送留言成功通知";

    /**
     * 评论通知
     */
    public static final String COMMENT_NOTICE = "发送评论成功通知";

}
