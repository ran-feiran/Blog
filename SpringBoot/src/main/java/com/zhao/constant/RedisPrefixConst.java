package com.zhao.constant;


import org.springframework.stereotype.Component;

/**
 * redis常量
 *
 * @author ran-feiran
 * @date 2022/09/27
 */
@Component
public class RedisPrefixConst {

    /**
     * 验证码过期时间
     */
    public static final long CODE_EXPIRE_TIME = 15 * 60;

    /**
     * 验证码
     */
    public static final String USER_EMAIL_KEY = "email:";

    /**
     * 博客浏览量
     */
    public static final String BLOG_VIEWS_COUNT = "blog_views_count";

    /**
     * 文章浏览量
     */
    public static final String ARTICLE_VIEWS_COUNT = "article_views_count";

    /**
     * 文章点赞量
     */
    public static final String ARTICLE_LIKE_COUNT = "article_like_count";

    /**
     * 用户点赞文章
     */
    public static final String ARTICLE_USER_LIKE = "article_user_like:";

    /**
     * 评论点赞量
     */
    public static final String COMMENT_LIKE_COUNT = "comment_like_count";

    /**
     * 用户点赞评论
     */
    public static final String COMMENT_USER_LIKE = "comment_user_like:";

    /**
     * 访客地区
     */
    public static final String VISITOR_AREA = "visitor_area";

    /**
     * 用户地区
     */
    public static final String USER_AREA = "user_area";

    /**
     * 访客
     */
    public static final String UNIQUE_VISITOR = "unique_visitor";

    /**
     * 网站配置
     */
    public static final String WEBSITE_CONFIG = "website_config";

    /**
     * 页面封面
     */
    public static final String PAGE_COVER = "page_cover";

    /**
     * 关于
     */
    public static final String ABOUT = "about_me";
}
