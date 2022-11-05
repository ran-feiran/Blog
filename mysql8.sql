SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_about
-- ----------------------------
DROP TABLE IF EXISTS `tb_about`;
CREATE TABLE `tb_about`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '//内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_about
-- ----------------------------
-- ----------------------------
-- Table structure for tb_api
-- ----------------------------
DROP TABLE IF EXISTS `tb_api`;
CREATE TABLE `tb_api`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `api_id` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '//api名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//请求地址',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `pid` int NULL DEFAULT NULL COMMENT '父id',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_api
-- ----------------------------
INSERT INTO `tb_api` VALUES (1, 1, 'API模块', NULL, '', NULL, 'api模块', 1);
INSERT INTO `tb_api` VALUES (2, NULL, '分类条件查询查询api的信息,父pid查询模块所有api', '/api/listApiInfoBack', 'GET', 1, '分类条件查询查询api的信息,父pid查询模块所有api', 101);
INSERT INTO `tb_api` VALUES (3, NULL, '根据swagger2的api文档读取api信息存入数据库', '/api/saveOrUpdateApiFromSwagger', 'post', 1, '根据swagger2的api文档读取api信息存入数据库', 102);
INSERT INTO `tb_api` VALUES (4, 2, '关于模块', NULL, NULL, NULL, 'About Controller', 2);
INSERT INTO `tb_api` VALUES (5, NULL, '获取关于我的信息', '/getAbout', 'GET', 2, '/获取关于我的信息', 201);
INSERT INTO `tb_api` VALUES (6, NULL, '更新关于我', '/updateAbout', 'POST', 2, '更新关于我', 202);
INSERT INTO `tb_api` VALUES (7, 3, '分类模块', NULL, NULL, NULL, 'Category Controller', 3);
INSERT INTO `tb_api` VALUES (8, NULL, '新增或者编辑分类', '/category/addOrEditCategory', 'POST', 3, '新增或者编辑分类', 301);
INSERT INTO `tb_api` VALUES (10, NULL, '获取所有的标签', '/category/getCategoryList', 'GET', 3, '获取所有的标签', 303);
INSERT INTO `tb_api` VALUES (11, NULL, '查看博客展示分类列表', '/category/listCategories', 'GET', 3, '查看博客展示分类列表', 304);
INSERT INTO `tb_api` VALUES (12, NULL, '分页条件查询分类列表', '/category/listCategory', 'GET', 3, '分页条件查询分类列表', 305);
INSERT INTO `tb_api` VALUES (13, NULL, '查看分类下对应的文章', '/category/*', 'GET', 3, '查看分类下对应的文章', 306);
INSERT INTO `tb_api` VALUES (14, 4, '博客信息模块', NULL, NULL, NULL, 'User Info Controller', 4);
INSERT INTO `tb_api` VALUES (15, NULL, '获取博主的基本信息', '/blogInfo/getBlogInfo', 'GET', 4, '获取博主的基本信息', 401);
INSERT INTO `tb_api` VALUES (16, 5, '友链模块', NULL, NULL, NULL, 'Friend Link Controller', 5);
INSERT INTO `tb_api` VALUES (17, NULL, '添加或者修改友链', '/friendLink/addOrEditFriendLink', 'POST', 5, '添加或者修改友链', 501);
INSERT INTO `tb_api` VALUES (18, NULL, '根据关键词分页查询友链', '/friendLink/listLinks', 'GET', 5, '根据关键词分页查询友链', 502);
INSERT INTO `tb_api` VALUES (19, 6, '文章模块', NULL, NULL, NULL, 'Article Controller', 6);
INSERT INTO `tb_api` VALUES (20, NULL, '查看首页文章', '/article/articles', 'GET', 6, '查看首页文章', 601);
INSERT INTO `tb_api` VALUES (21, NULL, '彻底删除文章', '/article/del/batch', 'DELETE', 6, '彻底删除文章', 602);
INSERT INTO `tb_api` VALUES (22, NULL, '根据文章id查询文章信息', '/article/getArticleById', 'GET', 6, '根据文章id查询文章信息', 603);
INSERT INTO `tb_api` VALUES (23, NULL, '根据文章标题分页查询文章列表', '/article/listArticle', 'GET', 6, '根据文章标题分页查询文章列表', 604);
INSERT INTO `tb_api` VALUES (24, NULL, '保存或者更新文章', '/article/saveOrUpdateArticle', 'POST', 6, '保存或者更新文章', 605);
INSERT INTO `tb_api` VALUES (25, NULL, '查询文章归档', '/article/archives', 'GET', 6, '查询文章归档', 606);
INSERT INTO `tb_api` VALUES (26, NULL, '查看首页文章', '/article/blog/*', 'GET', 6, '查看首页文章', 607);
INSERT INTO `tb_api` VALUES (27, 7, '标签模块', NULL, NULL, NULL, 'Tag Controller', 7);
INSERT INTO `tb_api` VALUES (28, NULL, '新增或者编辑标签', '/tag/addOrEditTag', 'POST', 7, '新增或者编辑标签', 701);
INSERT INTO `tb_api` VALUES (30, NULL, '后台获取所有的标签', '/tag/getTagList', 'GET', 7, '后台获取所有的标签', 703);
INSERT INTO `tb_api` VALUES (31, NULL, '后台分页条件查询标签列表', '/tag/listTags', 'GET', 7, '后台分页条件查询标签列表', 704);
INSERT INTO `tb_api` VALUES (32, NULL, '博客查看标签列表', '/tag/tags', 'GET', 7, '博客查看标签列表', 705);
INSERT INTO `tb_api` VALUES (33, NULL, '查看标签下对应的文章', '/tag/*', 'GET', 7, '查看标签下对应的文章', 706);
INSERT INTO `tb_api` VALUES (34, 8, '用户模块', NULL, NULL, NULL, 'User Controller', 8);
INSERT INTO `tb_api` VALUES (35, NULL, '根据用户角色和昵称分页查询用户列表', '/user/getUserList', 'GET', 8, '根据用户角色和昵称分页查询用户列表', 801);
INSERT INTO `tb_api` VALUES (36, NULL, '分页单表查询用户列表', '/user/getUserListSignal', 'GET', 8, '分页单表查询用户列表', 802);
INSERT INTO `tb_api` VALUES (37, NULL, '注册用户', '/user/registerUser', 'POST', 8, '注册用户', 803);
INSERT INTO `tb_api` VALUES (38, NULL, '发送邮箱验证码', '/user/sendEmailCode', 'GET', 8, '发送邮箱验证码', 804);
INSERT INTO `tb_api` VALUES (39, 9, '用户留言模块', NULL, NULL, NULL, 'Message Controller', 9);
INSERT INTO `tb_api` VALUES (40, NULL, '查看留言列表', '/message/getMessageList', 'GET', 9, '查看留言列表', 901);
INSERT INTO `tb_api` VALUES (41, NULL, '添加留言', '/message/messages', 'POST', 9, '添加留言', 902);
INSERT INTO `tb_api` VALUES (42, 10, '用户登录信息模块', NULL, NULL, NULL, 'User Login Controller', 10);
INSERT INTO `tb_api` VALUES (43, NULL, '分页获取用户登录信息列表', '/userLogin/getOnlineUser', 'GET', 10, '分页获取用户登录信息列表', 1001);
INSERT INTO `tb_api` VALUES (44, 11, '菜单展示模块', NULL, NULL, NULL, 'Menu Controller', 11);
INSERT INTO `tb_api` VALUES (45, NULL, '获取展示菜单列表', '/menu/getMenuList', 'GET', 11, '获取展示菜单列表', 1101);
INSERT INTO `tb_api` VALUES (46, 12, '角色模块', NULL, NULL, NULL, 'Role Controller', 12);
INSERT INTO `tb_api` VALUES (47, NULL, '查询角色信息', '/role/getRoleList', 'GET', 12, '查询角色信息', 1201);
INSERT INTO `tb_api` VALUES (48, 13, '评论模块', NULL, NULL, NULL, 'Comment Controller', 13);
INSERT INTO `tb_api` VALUES (49, NULL, '查询评论', '/comment/comments', 'GET', 13, '查询评论', 1301);
INSERT INTO `tb_api` VALUES (50, NULL, '添加评论或回复', '/comment/comments', 'POST', 13, '添加评论或回复', 1302);
INSERT INTO `tb_api` VALUES (51, NULL, '查看回复评论', '/comment/comments/replies', 'GET', 13, '查看回复评论', 1303);
INSERT INTO `tb_api` VALUES (52, NULL, '分页获取用户评论列表', '/comment/getUserCommentList', 'GET', 13, '分页获取用户评论列表', 1304);
INSERT INTO `tb_api` VALUES (53, 14, '阿里云对象存储ossAPI模块', NULL, NULL, NULL, 'Ali Oss Controller', 14);
INSERT INTO `tb_api` VALUES (54, NULL, '图片上传', '/file/uploadImage', 'POST', 14, '图片上传', 1401);
INSERT INTO `tb_api` VALUES (55, 15, 'ADMIN全部持有', '/**', 'ALL', NULL, '所有权限', 15);
INSERT INTO `tb_api` VALUES (56, NULL, '通过角色ID获取角色名字', '/role/getRoleNameById', 'GET', 12, '查询角色名字', 1202);
INSERT INTO `tb_api` VALUES (57, NULL, '获取角色对应的API列表', '/role/getAssignedApiIdByUserRoleId', 'GET', 12, '获取角色对应的API列表', 1203);
INSERT INTO `tb_api` VALUES (58, NULL, '保存角色API列表', '/role/saveRolePermissionList/*', 'POST', 12, '保存角色API列表', 1204);
INSERT INTO `tb_api` VALUES (59, NULL, '文章点赞', '/article/like/*', 'POST', 6, '文章点赞', 608);
INSERT INTO `tb_api` VALUES (60, NULL, '逻辑删除文章', '/article/del/batch', 'PUT', 6, '逻辑删除文章', 609);
INSERT INTO `tb_api` VALUES (61, NULL, '批量删除分类', '/category/del/batch', 'DELETE', 3, '批量删除分类', 307);
INSERT INTO `tb_api` VALUES (62, NULL, '评论点赞', '/comment/like/*', 'POST', 13, '评论点赞', 1305);
INSERT INTO `tb_api` VALUES (63, NULL, '删除用户评论', '/comment/del/batch', 'DELETE', 13, '删除用户评论', 1306);
INSERT INTO `tb_api` VALUES (64, NULL, '获取后台信息', '/blogInfo/getBlogInfoBack', 'GET', 4, '获取后台信息', 402);
INSERT INTO `tb_api` VALUES (65, NULL, '获取所有菜单', '/menu/getAllMenuList', 'GET', 11, '获取所有菜单', 1102);
INSERT INTO `tb_api` VALUES (66, NULL, '删除留言', '/message/del/batch', 'DELETE', 9, '删除留言', 903);
INSERT INTO `tb_api` VALUES (67, NULL, '保存用户菜单', '/role/saveRoleMenu/*', 'POST', 12, '保存用户菜单', 1205);
INSERT INTO `tb_api` VALUES (68, NULL, '查询角色信息', '/role/findRoleList', 'GET', 12, '查询角色信息', 1206);
INSERT INTO `tb_api` VALUES (69, NULL, '新增或更新角色', '/role/saveOrUpdateRole', 'POST', 12, '新增或更新角色', 1207);
INSERT INTO `tb_api` VALUES (70, NULL, '删除角色', '/role/deleteRole', 'DELETE', 12, '删除角色', 1208);
INSERT INTO `tb_api` VALUES (71, NULL, '批量删除标签', '/tag/del/batch', 'DELETE', 7, '批量删除标签', 707);
INSERT INTO `tb_api` VALUES (72, NULL, '删除登录用户信息', '/userLogin/deleteUser', 'DELETE', 10, '删除登录用户信息', 1002);
INSERT INTO `tb_api` VALUES (73, NULL, '下载', '/file/download/*', 'GET', 14, '下载', 1402);
INSERT INTO `tb_api` VALUES (74, NULL, '通过id更新用户信息', '/user/updateUserById', 'PUT', 8, '通过id更新用户信息', 805);
INSERT INTO `tb_api` VALUES (75, NULL, '更新用户角色', '/user/updateUserRole', 'POST', 8, '更新用户角色', 806);
INSERT INTO `tb_api` VALUES (76, NULL, '添加用户', '/user/addUser', 'POST', 8, '添加用户', 807);
INSERT INTO `tb_api` VALUES (77, NULL, '禁言设置', '/user/updateSilenceById', 'POST', 8, '禁言设置', 808);
INSERT INTO `tb_api` VALUES (78, NULL, '逻辑删除', '/user/logicDeleteUser', 'DELETE', 8, '逻辑删除', 809);
INSERT INTO `tb_api` VALUES (79, NULL, '修改博客信息', '/user/info', 'PUT', 8, '修改博客信息', 810);
INSERT INTO `tb_api` VALUES (80, NULL, '上传头像', '/user/avatar', 'POST', 8, '上传头像', 811);
INSERT INTO `tb_api` VALUES (81, NULL, '修改密码', '/user/forgetPassword', 'POST', 8, '修改密码', 812);
INSERT INTO `tb_api` VALUES (82, NULL, 'QQ授权登录', '/user/oauth/qq', 'POST', 8, 'QQ授权登录', 813);
INSERT INTO `tb_api` VALUES (83, NULL, '全文搜索', '/article/search', 'GET', 6, '全文搜索', 610);
INSERT INTO `tb_api` VALUES (84, NULL, '绑定邮箱', '/user/saveEmail', 'POST', 8, '绑定邮箱', 814);
INSERT INTO `tb_api` VALUES (85, NULL, '用户地域分布', '/blogInfo/getUserArea', 'GET', 4, '用户地域分布', 403);
INSERT INTO `tb_api` VALUES (86, NULL, '上传访客信息', '/blogInfo/report', 'POST', 4, '上传访客信息', 404);
INSERT INTO `tb_api` VALUES (87, NULL, '修改文章置顶', '/article/top', 'PUT', 6, '修改文章置顶', 610);
INSERT INTO `tb_api` VALUES (88, NULL, '审核留言', '/message/review', 'PUT', 9, '审核留言', 904);
INSERT INTO `tb_api` VALUES (89, NULL, '审核评论', '/comment/review', 'PUT', 13, '审核评论', 1307);
INSERT INTO `tb_api` VALUES (90, NULL, '修改后台密码', '/user/password', 'PUT', 8, '修改后台密码', 815);

-- ----------------------------
-- Table structure for tb_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_article`;
CREATE TABLE `tb_article`  (
  `article_id` int NOT NULL AUTO_INCREMENT COMMENT '//文章主键id',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '//文章标题',
  `article_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '//文章内容',
  `article_cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//文章封面',
  `category_id` int NULL DEFAULT NULL COMMENT '//分类id',
  `is_top` tinyint(1) NULL DEFAULT 0 COMMENT '//是否置顶 0：不置顶 1：置顶',
  `is_draft` tinyint(1) NULL DEFAULT 0 COMMENT '//是否草稿 0：不是草稿 1:草稿',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '//创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '//更新时间',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '//是否删除，0:不删除，1:删除',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_article
-- ----------------------------
-- ----------------------------
-- Table structure for tb_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_tag`;
CREATE TABLE `tb_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '//article 和 tag的关联 id',
  `tag_id` int NULL DEFAULT NULL COMMENT '//标签id',
  `article_id` int NULL DEFAULT NULL COMMENT '//文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_article_tag
-- ----------------------------


-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '//分类id',
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//分类名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '//创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '//更新时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_category
-- ----------------------------
INSERT INTO `tb_category` VALUES (1, '测试', '2020-11-25 09:29:08', '2022-06-15 18:38:58');

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '//评论主键id',
  `user_id` int NULL DEFAULT NULL COMMENT '//用户id',
  `article_id` int NULL DEFAULT NULL COMMENT '//文章id',
  `comment_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '//评论内容',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '//评论时间',
  `reply_id` int NULL DEFAULT NULL COMMENT '//回复用户id',
  `parent_id` int NULL DEFAULT NULL COMMENT '//父评论id',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '//是否删除',
  `is_review` tinyint(1) NULL DEFAULT 1 COMMENT '是否审核',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '评论类型 1.文章 2.友链 3.说说',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_comment
-- ----------------------------
-- ----------------------------
-- Table structure for tb_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `tb_friend_link`;
CREATE TABLE `tb_friend_link`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '友链主键id',
  `link_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接名',
  `link_avatar` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接头像',
  `link_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `link_intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接简介',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_friend_link
-- ----------------------------
INSERT INTO `tb_friend_link` VALUES (1, '一个简单的技术分享页', 'https://static.ran-feiran.cn/2022/9/26/20220926190156.jpg', 'https://www.ran-feiran.cn', '以身为棋，胜天半子', '2022-07-21 17:36:30');

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `order_num` tinyint(1) NOT NULL COMMENT '排序',
  `is_hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏  0否1是',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单icon',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组件',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名',
  `parent_id` int NULL DEFAULT NULL COMMENT '父id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单路径',
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 222 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myshouye', '/home/Home.vue', '2021-01-26 17:06:51', '首页', NULL, '2021-01-26 17:06:53', '/', 1, 0);
INSERT INTO `tb_menu` VALUES (2, 0, 'el-icon-mywenzhang-copy', 'Layout', '2021-01-25 20:43:07', '文章管理', NULL, '2021-01-25 20:43:09', '/article-submenu', 2, 0);
INSERT INTO `tb_menu` VALUES (3, 0, 'el-icon-myxiaoxi', 'Layout', '2021-01-25 20:44:17', '消息管理', NULL, '2021-01-25 20:44:20', '/message-submenu', 3, 0);
INSERT INTO `tb_menu` VALUES (5, 0, 'el-icon-myshezhi', 'Layout', '2021-01-25 20:45:57', '系统管理', NULL, '2021-01-25 20:45:59', '/system-submenu', 4, 0);
INSERT INTO `tb_menu` VALUES (7, 0, 'el-icon-myuser', '/setting/Setting.vue', '2021-01-26 17:22:38', '个人中心', NULL, '2021-01-26 17:22:41', '/setting', 5, 0);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myfabiaowenzhang', '/article/Article.vue', '2021-01-26 14:30:48', '发布文章', 2, '2021-01-26 14:30:51', '/articles', 6, 0);
INSERT INTO `tb_menu` VALUES (2, 1, 'el-icon-myfabiaowenzhang', '/article/Article.vue', '2021-01-26 14:31:32', '修改文章', 2, '2021-01-26 14:31:34', '/articles/*', 7, 0);
INSERT INTO `tb_menu` VALUES (3, 0, 'el-icon-mywenzhangliebiao', '/article/ArticleList.vue', '2021-01-26 14:32:13', '文章列表', 2, '2021-01-26 14:32:16', '/article-list', 8, 0);
INSERT INTO `tb_menu` VALUES (4, 0, 'el-icon-myfenlei', '/category/Category.vue', '2021-01-26 14:33:42', '分类管理', 2, '2021-01-26 14:33:43', '/categories', 9, 0);
INSERT INTO `tb_menu` VALUES (5, 0, 'el-icon-myicontag', '/tag/Tag.vue', '2021-01-26 14:34:33', '标签管理', 2, '2021-01-26 14:34:36', '/tags', 10, 0);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-mypinglunzu', '/comment/Comment.vue', '2021-01-26 14:35:31', '评论管理', 3, '2021-01-26 14:35:34', '/comments', 11, 0);
INSERT INTO `tb_menu` VALUES (2, 0, 'el-icon-myliuyan', '/message/Message.vue', '2021-01-26 14:36:09', '留言管理', 3, '2021-01-26 14:36:13', '/messages', 12, 0);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myyonghuliebiao', '/user/User.vue', '2021-01-26 14:38:09', '用户列表', 202, '2021-01-26 14:38:12', '/users', 13, 0);
INSERT INTO `tb_menu` VALUES (3, 0, 'el-icon-myjiaoseliebiao', '/role/Role.vue', '2021-01-26 14:39:01', '角色管理', 213, '2022-10-14 12:58:35', '/roles', 14, 0);
INSERT INTO `tb_menu` VALUES (2, 0, 'el-icon-myjiekouguanli', '/resource/Resource.vue', '2021-01-26 14:40:14', '接口管理', 213, '2021-08-07 20:00:28', '/resources', 15, 0);
INSERT INTO `tb_menu` VALUES (2, 0, 'el-icon-mycaidan', '/menu/Menu.vue', '2021-01-26 14:40:54', '菜单管理', 213, '2021-08-07 10:18:49', '/menus', 16, 0);
INSERT INTO `tb_menu` VALUES (3, 0, 'el-icon-mydashujukeshihuaico-', '/friendLink/FriendLink.vue', '2021-01-26 14:41:35', '友链管理', 4, '2021-01-26 14:41:37', '/links', 17, 0);
INSERT INTO `tb_menu` VALUES (4, 0, 'el-icon-myguanyuwo', '/about/About.vue', '2021-01-26 14:42:05', '关于我', 4, '2021-01-26 14:42:10', '/about', 18, 0);
INSERT INTO `tb_menu` VALUES (6, 0, 'el-icon-myguanyuwo', 'Layout', '2021-01-31 21:33:56', '日志管理', NULL, '2021-01-31 21:33:59', '/log-submenu', 19, 0);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myguanyuwo', '/log/Operation.vue', '2021-01-31 15:53:21', '操作日志', 19, '2021-01-31 15:53:25', '/operation/log', 20, 0);
INSERT INTO `tb_menu` VALUES (7, 0, 'el-icon-myyonghuliebiao', '/user/Online.vue', '2021-02-05 14:59:51', '在线用户', 202, '2021-02-05 14:59:53', '/online/users', 201, 0);
INSERT INTO `tb_menu` VALUES (4, 0, 'el-icon-myyonghuliebiao', 'Layout', '2021-02-06 23:44:59', '用户管理', NULL, '2021-02-06 23:45:03', '/users-submenu', 202, 0);
INSERT INTO `tb_menu` VALUES (5, 0, 'el-icon-myimage-fill', 'Layout', '2021-08-03 15:10:54', '相册管理', NULL, '2021-08-07 20:02:06', '/album-submenu', 205, 1);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myzhaopian', '/album/Album.vue', '2021-08-03 20:29:19', '相册列表', 205, '2021-08-04 11:45:47', '/albums', 206, 1);
INSERT INTO `tb_menu` VALUES (1, 1, 'el-icon-myzhaopian', '/album/Photo.vue', '2021-08-03 21:37:47', '照片管理', 205, '2021-08-05 10:24:08', '/albums/:albumId', 208, 1);
INSERT INTO `tb_menu` VALUES (2, 0, 'el-icon-myyemianpeizhi', '/page/Page.vue', '2021-08-04 11:36:27', '页面管理', 4, '2021-08-07 20:01:26', '/pages', 209, 0);
INSERT INTO `tb_menu` VALUES (3, 1, 'el-icon-myhuishouzhan', '/album/Delete.vue', '2021-08-05 13:55:19', '照片回收站', 205, NULL, '/photos/delete', 210, 1);
INSERT INTO `tb_menu` VALUES (4, 0, 'el-icon-mydaohanglantubiao_quanxianguanli', 'Layout', '2021-08-07 19:56:55', '权限管理', NULL, '2021-08-07 19:59:40', '/permission-submenu', 213, 0);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myxitong', '/website/Website.vue', '2021-08-07 20:06:41', '网站管理', 4, NULL, '/website', 214, 0);
INSERT INTO `tb_menu` VALUES (5, 0, 'el-icon-mypinglun', 'Layout', '2022-01-23 20:17:59', '说说管理', NULL, '2022-01-23 20:38:06', '/talk-submenu', 215, 1);
INSERT INTO `tb_menu` VALUES (1, 0, 'el-icon-myfabusekuai', '/talk/Talk.vue', '2022-01-23 20:18:43', '发布说说', 215, '2022-01-23 20:38:19', '/talks', 216, 1);
INSERT INTO `tb_menu` VALUES (2, 0, 'el-icon-myiconfontdongtaidianji', '/talk/TalkList.vue', '2022-01-23 23:28:24', '说说列表', 215, '2022-01-24 00:02:48', '/talk-list', 217, 1);
INSERT INTO `tb_menu` VALUES (3, 1, 'el-icon-myshouye', '/talk/Talk.vue', '2022-01-24 00:10:44', '修改说说', 215, NULL, '/talks/:talkId', 218, 1);
INSERT INTO `tb_menu` VALUES (8, 0, 'el-icon-myshouye', 'Layout', '2022-10-12 21:10:12', '测试', NULL, '2022-10-12 21:13:27', '/', 219, 1);
INSERT INTO `tb_menu` VALUES (1, 1, 'el-icon-myxiaoxi', 'Layout', '2022-10-12 21:12:00', '测试子菜单', 219, '2022-10-12 21:12:00', '/sub', 220, 1);
INSERT INTO `tb_menu` VALUES (1, 1, 'el-icon-myshouye', '1', '2022-10-12 21:15:39', '1', 219, '2022-10-12 21:15:39', '1', 221, 0);

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//ip',
  `ip_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//ip地理位置',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//昵称',
  `avatar` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//头像',
  `message_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '//留言内容',
  `time` tinyint(1) NULL DEFAULT NULL COMMENT '//弹幕速度',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '//发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_review` tinyint(1) NULL DEFAULT 1 COMMENT '是否审核',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_message
-- ----------------------------


-- ----------------------------
-- Table structure for tb_new_article
-- ----------------------------
DROP TABLE IF EXISTS `tb_new_article`;
CREATE TABLE `tb_new_article`  (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '作者',
  `category_id` int NULL DEFAULT NULL COMMENT '文章分类',
  `article_cover` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章缩略图',
  `article_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `article_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '文章类型 1原创 2转载 3翻译',
  `original_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原文链接',
  `is_top` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否置顶 0否 1是',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除  0否 1是',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态值 1公开 2私密 3评论可见',
  `create_time` datetime(0) NOT NULL COMMENT '发表时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_new_article
-- ----------------------------
INSERT INTO `tb_new_article` VALUES (21, 1, 1, 'https://static.ran-feiran.cn/2022/10/01/64bf64126ae14db5a9a481cdee71708e.jpeg', '测试文章', 1, NULL, 0, 0, 1, '2022-09-30 22:58:55', '2022-10-21 20:21:13');

-- ----------------------------
-- Table structure for tb_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `opt_module` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `opt_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作类型',
  `opt_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作url',
  `opt_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作方法',
  `opt_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `request_param` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求参数',
  `request_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方式',
  `response_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '返回数据',
  `user_id` int NOT NULL COMMENT '用户id',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作ip',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 229 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_operation_log
-- ----------------------------
-- ----------------------------
-- Table structure for tb_page
-- ----------------------------
DROP TABLE IF EXISTS `tb_page`;
CREATE TABLE `tb_page`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '页面id',
  `page_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '页面名',
  `page_label` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '页面标签',
  `page_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '页面封面',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '页面' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_page
-- ----------------------------
INSERT INTO `tb_page` VALUES (1, '首页', 'home', 'https://static.ran-feiran.cn/config/dea31facb7de37236b1177a2d427af69.jpg', '2022-10-21 14:40:18', '2022-11-05 21:32:19');
INSERT INTO `tb_page` VALUES (2, '归档', 'archive', 'https://static.ran-feiran.cn/config/643f28683e1c59a80ccfc9cb19735a9c.jpg', '2022-10-21 22:54:04', '2022-10-21 22:54:04');
INSERT INTO `tb_page` VALUES (3, '分类', 'category', 'https://static.ran-feiran.cn/config/72950642384cd1bd5ce78456f4e1afe3.jpg', '2022-10-21 22:54:51', '2022-11-03 14:08:14');
INSERT INTO `tb_page` VALUES (4, '标签', 'tag', 'https://static.ran-feiran.cn/config/1210fee4c9c77b65ee4cb91636c4be98.jpeg', '2022-10-21 22:56:06', '2022-10-21 22:56:06');
INSERT INTO `tb_page` VALUES (5, '友链', 'link', 'https://static.ran-feiran.cn/config/9034edddec5b8e8542c2e61b0da1c1da.jpg', '2022-10-21 22:57:56', '2022-10-21 22:57:56');
INSERT INTO `tb_page` VALUES (6, '关于', 'about', 'https://static.ran-feiran.cn/config/2a56d15dd742ff8ac238a512d9a472a1.jpg', '2022-10-21 22:58:21', '2022-10-21 22:58:21');
INSERT INTO `tb_page` VALUES (7, '留言', 'message', 'https://static.ran-feiran.cn/config/be47ddcf575cc48d8986bb5717fc9349.jpg', '2022-10-21 22:58:58', '2022-10-30 18:53:46');
INSERT INTO `tb_page` VALUES (8, '文章列表', 'articleList', 'https://static.ran-feiran.cn/config/6b558571bb97cb96471b8d5a5496e620.jpg', '2022-10-21 22:59:21', '2022-10-28 17:02:31');
INSERT INTO `tb_page` VALUES (9, '个人中心', 'user', 'https://static.ran-feiran.cn/config/ebae4c93de1b286a8d50aa62612caa59.jpeg', '2022-10-21 22:59:53', '2022-11-03 14:12:30');
INSERT INTO `tb_page` VALUES (10, '登录', 'login', 'https://static.ran-feiran.cn/config/29877d73fd18a979a8d8231176d108f2.jpeg', '2022-10-22 15:19:35', '2022-11-05 22:02:12');

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资源名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限路径',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `parent_id` int NULL DEFAULT NULL COMMENT '父权限id',
  `is_anonymous` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否匿名访问 0否 1是',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------
INSERT INTO `tb_resource` VALUES (2, '文章模块', NULL, NULL, NULL, 0, '2022-10-13 16:51:05', '2022-10-13 16:51:05');
INSERT INTO `tb_resource` VALUES (3, '文章搜索', '/article/search', 'GET', 2, 1, '2022-10-13 16:54:23', '2022-10-13 16:54:23');
INSERT INTO `tb_resource` VALUES (4, '博客信息模块', NULL, NULL, NULL, 0, '2022-10-13 18:00:52', '2022-10-19 11:59:21');
INSERT INTO `tb_resource` VALUES (6, '获取博客信息(后台)', '/blogInfo/getBlogInfoBack', 'GET', 4, 0, '2022-10-13 18:18:55', '2022-10-19 18:02:57');
INSERT INTO `tb_resource` VALUES (7, '用户地域分布', '/blogInfo/getUserArea', 'GET', 4, 0, '2022-10-13 18:19:27', '2022-10-19 18:02:45');
INSERT INTO `tb_resource` VALUES (8, '上传访客信息', '/blogInfo/report', 'POST', 4, 1, '2022-10-13 18:25:48', '2022-10-13 18:29:08');
INSERT INTO `tb_resource` VALUES (9, '关于我模块', NULL, NULL, NULL, 0, '2022-10-13 18:30:46', '2022-10-19 17:42:34');
INSERT INTO `tb_resource` VALUES (10, '得到关于我', '/about/getAbout', 'GET', 9, 1, '2022-10-13 18:31:30', '2022-10-19 18:04:38');
INSERT INTO `tb_resource` VALUES (11, '更新关于我', '/about/updateAbout', 'PUT', 9, 0, '2022-10-13 18:32:09', '2022-10-30 22:23:38');
INSERT INTO `tb_resource` VALUES (12, '分类模块', NULL, NULL, NULL, 0, '2022-10-13 18:38:42', '2022-10-13 18:38:42');
INSERT INTO `tb_resource` VALUES (13, '分类搜索', '/category/search', 'GET', 12, 0, '2022-10-13 18:42:10', '2022-10-13 18:42:10');
INSERT INTO `tb_resource` VALUES (14, '获取分类列表', '/category/listCategories', 'GET', 12, 1, '2022-10-13 18:43:04', '2022-10-19 18:07:01');
INSERT INTO `tb_resource` VALUES (15, '获取分类数据(后台)', '/category/getCategoryList', 'GET', 12, 0, '2022-10-13 18:44:22', '2022-10-19 18:07:21');
INSERT INTO `tb_resource` VALUES (16, '物理删除分类', '/category/del/batch', 'DELETE', 12, 0, '2022-10-13 18:45:24', '2022-10-19 18:07:29');
INSERT INTO `tb_resource` VALUES (17, '新增或更新分类', '/category/addOrEditCategory', 'POST', 12, 0, '2022-10-13 18:46:20', '2022-10-19 18:07:43');
INSERT INTO `tb_resource` VALUES (18, '获取分类列表(后台)', '/category/listCategory', 'GET', 12, 0, '2022-10-13 18:47:31', '2022-10-19 18:07:56');
INSERT INTO `tb_resource` VALUES (19, '通过分类id获取对应文章', '/category/query/*', 'GET', 12, 1, '2022-10-13 18:48:14', '2022-10-19 19:38:24');
INSERT INTO `tb_resource` VALUES (20, '标签模块', NULL, NULL, NULL, 0, '2022-10-13 18:49:02', '2022-10-13 18:49:02');
INSERT INTO `tb_resource` VALUES (21, '文章点赞', '/article/like/*', 'POST', 2, 0, '2022-10-19 17:53:22', '2022-10-19 17:53:22');
INSERT INTO `tb_resource` VALUES (22, '通过文章id获取文章', '/article/blog/*', 'GET', 2, 1, '2022-10-19 17:54:38', '2022-10-19 17:54:38');
INSERT INTO `tb_resource` VALUES (23, '文章归档', '/article/archives', 'GET', 2, 1, '2022-10-19 17:55:19', '2022-10-19 17:55:19');
INSERT INTO `tb_resource` VALUES (24, '获取文章列表', '/article/articles', 'GET', 2, 1, '2022-10-19 17:57:43', '2022-10-19 17:57:43');
INSERT INTO `tb_resource` VALUES (25, '新增或更新文章', '/article/saveOrUpdateArticle', 'POST', 2, 0, '2022-10-19 17:58:14', '2022-10-19 17:58:14');
INSERT INTO `tb_resource` VALUES (26, '通过文章id获取文章(后台)', '/article/getArticleById', 'GET', 2, 0, '2022-10-19 17:58:45', '2022-10-19 17:58:45');
INSERT INTO `tb_resource` VALUES (27, '获取文章列表(后台)', '/article/listArticle', 'GET', 2, 0, '2022-10-19 17:59:31', '2022-10-19 17:59:31');
INSERT INTO `tb_resource` VALUES (28, '逻辑删除文章', '/article/del/batch', 'PUT', 2, 0, '2022-10-19 18:00:04', '2022-10-19 18:00:04');
INSERT INTO `tb_resource` VALUES (29, '物理删除文章', '/article/del/batch', 'DELETE', 2, 0, '2022-10-19 18:00:21', '2022-10-19 18:00:21');
INSERT INTO `tb_resource` VALUES (30, '文章置顶', '/article/top', 'PUT', 2, 0, '2022-10-19 18:01:33', '2022-10-19 18:01:33');
INSERT INTO `tb_resource` VALUES (31, '获取博客信息', '/blogInfo/getBlogInfo', 'GET', 4, 1, '2022-10-19 18:03:16', '2022-10-19 18:03:16');
INSERT INTO `tb_resource` VALUES (32, '最近文章列表', '/blogInfo/getNewArticleList', 'GET', 4, 1, '2022-10-19 18:03:32', '2022-10-19 18:03:32');
INSERT INTO `tb_resource` VALUES (33, '标签搜索', '/tag/search', 'GET', 20, 1, '2022-10-19 18:21:51', '2022-10-19 18:21:51');
INSERT INTO `tb_resource` VALUES (34, '获取标签列表', '/tag/tags', 'GET', 20, 1, '2022-10-19 18:22:58', '2022-10-19 18:22:58');
INSERT INTO `tb_resource` VALUES (35, '获取分类数据(后台)', '/tag/getTagList', 'GET', 20, 0, '2022-10-19 18:23:20', '2022-10-19 18:23:20');
INSERT INTO `tb_resource` VALUES (36, '物理删除标签', '/tag/del/batch', 'DELETE', 20, 0, '2022-10-19 18:23:52', '2022-10-19 18:23:52');
INSERT INTO `tb_resource` VALUES (37, '新增或更新标签', '/tag/addOrEditTag', 'POST', 20, 0, '2022-10-19 18:24:10', '2022-10-19 18:24:10');
INSERT INTO `tb_resource` VALUES (38, '获取标签列表(后台)', '/tag/listTags', 'GET', 20, 0, '2022-10-19 18:24:29', '2022-10-19 18:24:29');
INSERT INTO `tb_resource` VALUES (39, '通过标签id获取对应文章', '/tag/query/*', 'GET', 20, 1, '2022-10-19 18:24:48', '2022-10-19 19:38:57');
INSERT INTO `tb_resource` VALUES (40, '文件模块', NULL, NULL, NULL, 0, '2022-10-19 18:28:32', '2022-10-19 18:28:32');
INSERT INTO `tb_resource` VALUES (41, '上传配置型图片', '/file/config/images', 'POST', 40, 0, '2022-10-19 18:28:51', '2022-10-19 18:28:51');
INSERT INTO `tb_resource` VALUES (42, '上传文章型图片', '/file/article/images', 'POST', 40, 0, '2022-10-19 18:29:08', '2022-10-19 18:29:08');
INSERT INTO `tb_resource` VALUES (43, '下载图片(本地接口：可忽略)', '/blog/*', 'GET', 40, 0, '2022-10-19 18:30:00', '2022-10-19 18:30:00');
INSERT INTO `tb_resource` VALUES (44, '评论模块', NULL, NULL, NULL, 0, '2022-10-19 18:31:38', '2022-10-19 18:31:38');
INSERT INTO `tb_resource` VALUES (45, '留言模块', NULL, NULL, NULL, 0, '2022-10-19 18:31:52', '2022-10-19 18:31:52');
INSERT INTO `tb_resource` VALUES (46, '友链模块', NULL, NULL, NULL, 0, '2022-10-19 18:32:00', '2022-10-19 18:32:00');
INSERT INTO `tb_resource` VALUES (47, '菜单模块', NULL, NULL, NULL, 0, '2022-10-19 18:32:22', '2022-10-19 18:32:22');
INSERT INTO `tb_resource` VALUES (48, '资源模块', NULL, NULL, NULL, 0, '2022-10-19 18:32:32', '2022-10-19 18:32:32');
INSERT INTO `tb_resource` VALUES (49, '评论点赞', '/comment/like/*', 'POST', 44, 0, '2022-10-19 18:34:44', '2022-10-19 18:34:44');
INSERT INTO `tb_resource` VALUES (50, '日志模块', NULL, NULL, NULL, 0, '2022-10-19 18:40:48', '2022-10-19 18:40:48');
INSERT INTO `tb_resource` VALUES (51, '页面模块', NULL, NULL, NULL, 0, '2022-10-19 18:41:01', '2022-10-19 18:41:01');
INSERT INTO `tb_resource` VALUES (52, '用户模块', NULL, NULL, NULL, 0, '2022-10-19 18:41:34', '2022-10-19 18:41:34');
INSERT INTO `tb_resource` VALUES (53, '用户登录信息模块', NULL, NULL, NULL, 0, '2022-10-19 18:41:47', '2022-10-19 18:41:47');
INSERT INTO `tb_resource` VALUES (54, '网站配置模块', NULL, NULL, NULL, 0, '2022-10-19 18:42:15', '2022-10-19 18:42:15');
INSERT INTO `tb_resource` VALUES (55, '更新id对应父评论下的子评论', '/comment/comments/replies', 'GET', 44, 1, '2022-10-19 18:50:53', '2022-10-19 18:50:53');
INSERT INTO `tb_resource` VALUES (56, '保存评论', '/comment/comments', 'POST', 44, 0, '2022-10-19 18:51:29', '2022-10-23 11:47:37');
INSERT INTO `tb_resource` VALUES (57, '获取评论列表', '/comment/comments', 'GET', 44, 1, '2022-10-19 18:51:54', '2022-10-19 18:51:54');
INSERT INTO `tb_resource` VALUES (58, '获取评论列表(后台)', '/comment/getUserCommentList', 'GET', 44, 0, '2022-10-19 18:52:27', '2022-10-19 18:52:27');
INSERT INTO `tb_resource` VALUES (59, '逻辑删除评论', '/comment/del/batch', 'DELETE', 44, 0, '2022-10-19 18:53:04', '2022-10-19 18:53:04');
INSERT INTO `tb_resource` VALUES (60, '评论审核', '/comment/review', 'PUT', 44, 0, '2022-10-19 18:53:46', '2022-10-19 18:53:46');
INSERT INTO `tb_resource` VALUES (61, '角色模块', NULL, NULL, NULL, 0, '2022-10-19 19:06:37', '2022-10-19 19:06:37');
INSERT INTO `tb_resource` VALUES (62, '获取留言列表(后台)', '/message/getMessageList', 'GET', 45, 1, '2022-10-19 19:10:16', '2022-10-19 19:10:16');
INSERT INTO `tb_resource` VALUES (63, '留言审核', '/message/review', 'PUT', 45, 0, '2022-10-19 19:10:41', '2022-10-19 19:10:41');
INSERT INTO `tb_resource` VALUES (64, '新增留言', '/message/messages', 'POST', 45, 1, '2022-10-19 19:11:08', '2022-10-19 19:11:08');
INSERT INTO `tb_resource` VALUES (65, '留言删除', '/message/del/batch', 'DELETE', 45, 0, '2022-10-19 19:11:27', '2022-10-19 19:11:27');
INSERT INTO `tb_resource` VALUES (66, '获取友链列表', '/friendLink/listLinks', 'GET', 46, 1, '2022-10-19 19:12:51', '2022-10-19 19:12:51');
INSERT INTO `tb_resource` VALUES (67, '新增或更新友链', '/friendLink/addOrEditFriendLink', 'POST', 46, 0, '2022-10-19 19:13:22', '2022-10-19 19:13:22');
INSERT INTO `tb_resource` VALUES (68, '删除友链', '/friendLink/del/batch', 'DELETE', 46, 0, '2022-10-19 19:13:47', '2022-10-19 19:13:47');
INSERT INTO `tb_resource` VALUES (69, '新增或更新菜单', '/menu/saveOrUpdateMenu', 'POST', 47, 0, '2022-10-19 19:23:03', '2022-10-19 19:23:03');
INSERT INTO `tb_resource` VALUES (70, '修改菜单隐藏状态', '/menu/updateHiddenById', 'PUT', 47, 0, '2022-10-19 19:23:25', '2022-10-19 19:23:25');
INSERT INTO `tb_resource` VALUES (71, '逻辑删除菜单', '/menu/del/*', 'DELETE', 47, 0, '2022-10-19 19:23:50', '2022-10-19 19:23:50');
INSERT INTO `tb_resource` VALUES (72, '根据角色名获取菜单列表', '/menu/getMenuList', 'GET', 47, 0, '2022-10-19 19:25:23', '2022-10-19 19:25:23');
INSERT INTO `tb_resource` VALUES (73, '获取所有菜单列表', '/menu/getAllMenuList', 'GET', 47, 0, '2022-10-19 19:26:19', '2022-10-19 19:26:19');
INSERT INTO `tb_resource` VALUES (74, '获取资源信息(后台)', '/resource/getResourcesInfoBack', 'GET', 48, 0, '2022-10-19 19:27:14', '2022-10-19 19:27:14');
INSERT INTO `tb_resource` VALUES (75, '修改资源匿名访问状态', '/resource/updateAnonymousById', 'PUT', 48, 0, '2022-10-19 19:28:31', '2022-10-19 19:28:31');
INSERT INTO `tb_resource` VALUES (76, '资源删除', '/resource/del/*', 'DELETE', 48, 0, '2022-10-19 19:29:22', '2022-10-19 19:29:22');
INSERT INTO `tb_resource` VALUES (77, '新增或更新资源', '/resource/addOrEditResource', 'POST', 48, 0, '2022-10-19 19:29:47', '2022-10-19 19:29:47');
INSERT INTO `tb_resource` VALUES (79, '获取操作日志', '/operation/listLogs', 'GET', 50, 0, '2022-10-19 19:34:49', '2022-10-19 19:34:49');
INSERT INTO `tb_resource` VALUES (80, '删除日志', '/operation/del/batch', 'DELETE', 50, 0, '2022-10-19 19:35:04', '2022-10-19 19:35:04');
INSERT INTO `tb_resource` VALUES (81, '获取页面列表', '/page/listPages', 'GET', 51, 0, '2022-10-19 19:36:34', '2022-10-19 19:36:34');
INSERT INTO `tb_resource` VALUES (82, '新增或更新页面', '/page/addOrEditPage', 'POST', 51, 0, '2022-10-19 19:36:47', '2022-10-19 19:36:47');
INSERT INTO `tb_resource` VALUES (83, '删除页面', '/page/del/batch/*', 'DELETE', 51, 0, '2022-10-19 19:37:17', '2022-10-19 19:37:17');
INSERT INTO `tb_resource` VALUES (84, '获取网站配置', '/website/getWebsiteConfig', 'GET', 54, 1, '2022-10-19 19:41:21', '2022-10-19 19:41:21');
INSERT INTO `tb_resource` VALUES (85, '更新网站配置', '/website/updateWebsiteConfig', 'PUT', 54, 0, '2022-10-19 19:41:40', '2022-10-19 19:41:40');
INSERT INTO `tb_resource` VALUES (86, '获取在线用户列表', '/userLogin/getOnlineUser', 'GET', 53, 0, '2022-10-19 19:42:24', '2022-10-19 19:42:24');
INSERT INTO `tb_resource` VALUES (87, '下线用户', '/userLogin/del/online/*', 'DELETE', 53, 0, '2022-10-19 19:42:44', '2022-10-19 19:42:44');
INSERT INTO `tb_resource` VALUES (88, '获取角色列表', '/role/listRoles', 'GET', 61, 0, '2022-10-19 19:43:24', '2022-10-19 19:43:24');
INSERT INTO `tb_resource` VALUES (89, '角色删除', '/role/del/batch', 'DELETE', 61, 0, '2022-10-19 19:44:00', '2022-10-19 19:44:00');
INSERT INTO `tb_resource` VALUES (90, '保存或更新角色权限', '/role/saveOrUpdateRolePermission', 'POST', 61, 0, '2022-10-19 19:44:17', '2022-10-19 19:44:17');
INSERT INTO `tb_resource` VALUES (91, 'QQ登录', '/user/oauth/qq', 'POST', 52, 1, '2022-10-19 19:46:50', '2022-10-19 19:46:50');
INSERT INTO `tb_resource` VALUES (92, '绑定邮箱', '/user/saveEmail', 'POST', 52, 0, '2022-10-19 19:47:57', '2022-10-19 19:47:57');
INSERT INTO `tb_resource` VALUES (93, '更新密码', '/user/forgetPassword', 'POST', 52, 1, '2022-10-19 19:48:18', '2022-10-19 19:48:18');
INSERT INTO `tb_resource` VALUES (94, '用户注册', '/user/registerUser', 'POST', 52, 1, '2022-10-19 19:48:35', '2022-10-19 19:48:35');
INSERT INTO `tb_resource` VALUES (95, '发送验证码', '/user/sendEmailCode', 'GET', 52, 1, '2022-10-19 19:48:57', '2022-10-23 13:23:45');
INSERT INTO `tb_resource` VALUES (96, '更新用户角色', '/user/updateUserRole', 'PUT', 52, 0, '2022-10-19 19:49:15', '2022-10-19 19:50:01');
INSERT INTO `tb_resource` VALUES (97, '获取用户列表(后台)', '/user/getUserList', 'GET', 52, 0, '2022-10-19 19:49:32', '2022-10-19 19:49:32');
INSERT INTO `tb_resource` VALUES (98, '更新用户禁言状态', '/user/updateSilenceById', 'PUT', 52, 0, '2022-10-19 19:49:56', '2022-10-19 19:49:56');
INSERT INTO `tb_resource` VALUES (99, '更新用户信息', '/user/info', 'PUT', 52, 0, '2022-10-19 19:50:32', '2022-10-19 19:50:32');
INSERT INTO `tb_resource` VALUES (100, '更新密码(后台)', '/user/password', 'PUT', 52, 0, '2022-10-19 19:51:28', '2022-10-19 19:51:28');
INSERT INTO `tb_resource` VALUES (101, '上传头像', '/user/avatar', 'POST', 52, 0, '2022-10-19 19:51:44', '2022-10-19 19:51:44');
INSERT INTO `tb_resource` VALUES (102, '获取角色数据', '/role/getRoleList', 'GET', 61, 1, '2022-10-20 17:36:33', '2022-10-20 17:36:33');
INSERT INTO `tb_resource` VALUES (103, '接口文档', '/doc.html', 'GET', 48, 1, '2022-10-28 21:44:52', '2022-10-28 21:44:55');
INSERT INTO `tb_resource` VALUES (104, 'load css', '/webjars/**', 'GET', 48, 1, '2022-10-28 22:14:15', '2022-10-28 22:14:16');
INSERT INTO `tb_resource` VALUES (105, 'load resources', '/swagger-resources/**', 'GET', 48, 1, '2022-10-28 22:21:13', '2022-10-28 22:21:15');
INSERT INTO `tb_resource` VALUES (106, 'load docs', '/v2/**', 'GET', 48, 1, '2022-10-28 22:23:38', '2022-10-28 22:23:40');
INSERT INTO `tb_resource` VALUES (107, 'load icon', '/favicon.ico', 'GET', 48, 1, '2022-10-28 22:49:55', '2022-10-28 22:49:55');
INSERT INTO `tb_resource` VALUES (108, '加载登录背景', '/blogInfo/loadLoginPage', 'GET', 4, 1, '2022-11-05 21:02:50', '2022-11-05 21:02:52');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `role_label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `is_disable` tinyint(1) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_delete` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `tb_role_pk`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, 'ADMIN', '管理员', 0, '2022-10-18 12:56:21', '2022-10-30 21:54:23', 0);
INSERT INTO `tb_role` VALUES (2, 'USER', '用户', 0, '2022-10-18 12:56:24', '2022-10-28 22:52:13', 0);
INSERT INTO `tb_role` VALUES (3, 'TEST', '测试', 0, '2022-10-18 12:56:25', '2022-10-28 22:52:23', 0);
INSERT INTO `tb_role` VALUES (6, 'GUEST', '游客', 0, '2022-10-18 12:56:27', '2022-10-20 14:27:37', 1);

-- ----------------------------
-- Table structure for tb_role_api
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_api`;
CREATE TABLE `tb_role_api`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL,
  `api_id` int NULL DEFAULT NULL COMMENT 'api的主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_role_api
-- ----------------------------
INSERT INTO `tb_role_api` VALUES (1, 1, 55);
INSERT INTO `tb_role_api` VALUES (2, 6, 5);
INSERT INTO `tb_role_api` VALUES (3, 6, 13);
INSERT INTO `tb_role_api` VALUES (4, 6, 10);
INSERT INTO `tb_role_api` VALUES (5, 6, 11);
INSERT INTO `tb_role_api` VALUES (6, 6, 12);
INSERT INTO `tb_role_api` VALUES (7, 6, 15);
INSERT INTO `tb_role_api` VALUES (8, 6, 25);
INSERT INTO `tb_role_api` VALUES (9, 6, 26);
INSERT INTO `tb_role_api` VALUES (10, 6, 20);
INSERT INTO `tb_role_api` VALUES (11, 6, 22);
INSERT INTO `tb_role_api` VALUES (12, 6, 23);
INSERT INTO `tb_role_api` VALUES (13, 6, 32);
INSERT INTO `tb_role_api` VALUES (14, 6, 33);
INSERT INTO `tb_role_api` VALUES (15, 6, 38);
INSERT INTO `tb_role_api` VALUES (16, 6, 41);
INSERT INTO `tb_role_api` VALUES (17, 6, 51);
INSERT INTO `tb_role_api` VALUES (18, 6, 49);
INSERT INTO `tb_role_api` VALUES (19, 6, 73);
INSERT INTO `tb_role_api` VALUES (21, 6, 18);
INSERT INTO `tb_role_api` VALUES (22, 6, 40);
INSERT INTO `tb_role_api` VALUES (23, 6, 37);
INSERT INTO `tb_role_api` VALUES (24, 6, 81);
INSERT INTO `tb_role_api` VALUES (25, 6, 82);
INSERT INTO `tb_role_api` VALUES (26, 6, 83);
INSERT INTO `tb_role_api` VALUES (27, 2, 5);
INSERT INTO `tb_role_api` VALUES (28, 2, 13);
INSERT INTO `tb_role_api` VALUES (29, 2, 12);
INSERT INTO `tb_role_api` VALUES (30, 2, 11);
INSERT INTO `tb_role_api` VALUES (31, 2, 10);
INSERT INTO `tb_role_api` VALUES (32, 2, 15);
INSERT INTO `tb_role_api` VALUES (33, 2, 64);
INSERT INTO `tb_role_api` VALUES (34, 2, 18);
INSERT INTO `tb_role_api` VALUES (35, 2, 59);
INSERT INTO `tb_role_api` VALUES (36, 2, 83);
INSERT INTO `tb_role_api` VALUES (37, 2, 26);
INSERT INTO `tb_role_api` VALUES (38, 2, 23);
INSERT INTO `tb_role_api` VALUES (39, 2, 25);
INSERT INTO `tb_role_api` VALUES (40, 2, 20);
INSERT INTO `tb_role_api` VALUES (41, 2, 22);
INSERT INTO `tb_role_api` VALUES (42, 2, 30);
INSERT INTO `tb_role_api` VALUES (43, 2, 31);
INSERT INTO `tb_role_api` VALUES (44, 2, 32);
INSERT INTO `tb_role_api` VALUES (45, 2, 33);
INSERT INTO `tb_role_api` VALUES (46, 2, 84);
INSERT INTO `tb_role_api` VALUES (47, 2, 79);
INSERT INTO `tb_role_api` VALUES (48, 2, 80);
INSERT INTO `tb_role_api` VALUES (49, 2, 81);
INSERT INTO `tb_role_api` VALUES (50, 2, 82);
INSERT INTO `tb_role_api` VALUES (51, 2, 35);
INSERT INTO `tb_role_api` VALUES (52, 2, 36);
INSERT INTO `tb_role_api` VALUES (53, 2, 37);
INSERT INTO `tb_role_api` VALUES (54, 2, 38);
INSERT INTO `tb_role_api` VALUES (55, 2, 40);
INSERT INTO `tb_role_api` VALUES (56, 2, 41);
INSERT INTO `tb_role_api` VALUES (57, 2, 43);
INSERT INTO `tb_role_api` VALUES (58, 2, 65);
INSERT INTO `tb_role_api` VALUES (59, 2, 45);
INSERT INTO `tb_role_api` VALUES (60, 2, 57);
INSERT INTO `tb_role_api` VALUES (61, 2, 47);
INSERT INTO `tb_role_api` VALUES (62, 2, 68);
INSERT INTO `tb_role_api` VALUES (63, 2, 56);
INSERT INTO `tb_role_api` VALUES (64, 2, 51);
INSERT INTO `tb_role_api` VALUES (65, 2, 50);
INSERT INTO `tb_role_api` VALUES (66, 2, 49);
INSERT INTO `tb_role_api` VALUES (67, 2, 52);
INSERT INTO `tb_role_api` VALUES (68, 2, 62);
INSERT INTO `tb_role_api` VALUES (69, 2, 73);
INSERT INTO `tb_role_api` VALUES (70, 2, 54);
INSERT INTO `tb_role_api` VALUES (71, 6, 86);

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------
INSERT INTO `tb_role_menu` VALUES (1, 1, 1);
INSERT INTO `tb_role_menu` VALUES (2, 1, 2);
INSERT INTO `tb_role_menu` VALUES (3, 1, 6);
INSERT INTO `tb_role_menu` VALUES (4, 1, 7);
INSERT INTO `tb_role_menu` VALUES (5, 1, 8);
INSERT INTO `tb_role_menu` VALUES (6, 1, 9);
INSERT INTO `tb_role_menu` VALUES (7, 1, 10);
INSERT INTO `tb_role_menu` VALUES (8, 1, 5);
INSERT INTO `tb_role_menu` VALUES (9, 1, 3);
INSERT INTO `tb_role_menu` VALUES (10, 1, 11);
INSERT INTO `tb_role_menu` VALUES (11, 1, 12);
INSERT INTO `tb_role_menu` VALUES (12, 1, 13);
INSERT INTO `tb_role_menu` VALUES (13, 1, 201);
INSERT INTO `tb_role_menu` VALUES (14, 1, 202);
INSERT INTO `tb_role_menu` VALUES (15, 1, 213);
INSERT INTO `tb_role_menu` VALUES (16, 1, 14);
INSERT INTO `tb_role_menu` VALUES (17, 1, 15);
INSERT INTO `tb_role_menu` VALUES (18, 1, 16);
INSERT INTO `tb_role_menu` VALUES (19, 1, 4);
INSERT INTO `tb_role_menu` VALUES (20, 1, 17);
INSERT INTO `tb_role_menu` VALUES (21, 1, 18);
INSERT INTO `tb_role_menu` VALUES (22, 1, 209);
INSERT INTO `tb_role_menu` VALUES (23, 1, 214);
INSERT INTO `tb_role_menu` VALUES (24, 1, 19);
INSERT INTO `tb_role_menu` VALUES (25, 1, 20);
INSERT INTO `tb_role_menu` VALUES (36, 3, 1);
INSERT INTO `tb_role_menu` VALUES (37, 3, 2);
INSERT INTO `tb_role_menu` VALUES (38, 3, 6);
INSERT INTO `tb_role_menu` VALUES (39, 3, 7);
INSERT INTO `tb_role_menu` VALUES (40, 3, 8);
INSERT INTO `tb_role_menu` VALUES (41, 3, 9);
INSERT INTO `tb_role_menu` VALUES (42, 3, 10);
INSERT INTO `tb_role_menu` VALUES (43, 3, 3);
INSERT INTO `tb_role_menu` VALUES (44, 3, 11);
INSERT INTO `tb_role_menu` VALUES (45, 3, 12);
INSERT INTO `tb_role_menu` VALUES (46, 3, 202);
INSERT INTO `tb_role_menu` VALUES (47, 3, 13);
INSERT INTO `tb_role_menu` VALUES (48, 3, 201);
INSERT INTO `tb_role_menu` VALUES (49, 3, 213);
INSERT INTO `tb_role_menu` VALUES (50, 3, 16);
INSERT INTO `tb_role_menu` VALUES (51, 3, 15);
INSERT INTO `tb_role_menu` VALUES (52, 3, 14);
INSERT INTO `tb_role_menu` VALUES (53, 3, 4);
INSERT INTO `tb_role_menu` VALUES (54, 3, 214);
INSERT INTO `tb_role_menu` VALUES (55, 3, 209);
INSERT INTO `tb_role_menu` VALUES (56, 3, 17);
INSERT INTO `tb_role_menu` VALUES (57, 3, 18);
INSERT INTO `tb_role_menu` VALUES (58, 3, 19);
INSERT INTO `tb_role_menu` VALUES (59, 3, 20);
INSERT INTO `tb_role_menu` VALUES (60, 3, 5);
INSERT INTO `tb_role_menu` VALUES (65, 2, 5);

-- ----------------------------
-- Table structure for tb_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resource`;
CREATE TABLE `tb_role_resource`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `resource_id` int NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 486 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_role_resource
-- ----------------------------
INSERT INTO `tb_role_resource` VALUES (297, 2, 24);
INSERT INTO `tb_role_resource` VALUES (298, 2, 23);
INSERT INTO `tb_role_resource` VALUES (299, 2, 22);
INSERT INTO `tb_role_resource` VALUES (300, 2, 21);
INSERT INTO `tb_role_resource` VALUES (301, 2, 3);
INSERT INTO `tb_role_resource` VALUES (302, 2, 32);
INSERT INTO `tb_role_resource` VALUES (303, 2, 31);
INSERT INTO `tb_role_resource` VALUES (304, 2, 8);
INSERT INTO `tb_role_resource` VALUES (305, 2, 10);
INSERT INTO `tb_role_resource` VALUES (306, 2, 19);
INSERT INTO `tb_role_resource` VALUES (307, 2, 14);
INSERT INTO `tb_role_resource` VALUES (308, 2, 13);
INSERT INTO `tb_role_resource` VALUES (309, 2, 39);
INSERT INTO `tb_role_resource` VALUES (310, 2, 34);
INSERT INTO `tb_role_resource` VALUES (311, 2, 33);
INSERT INTO `tb_role_resource` VALUES (312, 2, 57);
INSERT INTO `tb_role_resource` VALUES (313, 2, 56);
INSERT INTO `tb_role_resource` VALUES (314, 2, 55);
INSERT INTO `tb_role_resource` VALUES (315, 2, 49);
INSERT INTO `tb_role_resource` VALUES (316, 2, 64);
INSERT INTO `tb_role_resource` VALUES (317, 2, 62);
INSERT INTO `tb_role_resource` VALUES (318, 2, 66);
INSERT INTO `tb_role_resource` VALUES (319, 2, 72);
INSERT INTO `tb_role_resource` VALUES (320, 2, 107);
INSERT INTO `tb_role_resource` VALUES (321, 2, 106);
INSERT INTO `tb_role_resource` VALUES (322, 2, 105);
INSERT INTO `tb_role_resource` VALUES (323, 2, 104);
INSERT INTO `tb_role_resource` VALUES (324, 2, 103);
INSERT INTO `tb_role_resource` VALUES (325, 2, 79);
INSERT INTO `tb_role_resource` VALUES (326, 2, 101);
INSERT INTO `tb_role_resource` VALUES (327, 2, 100);
INSERT INTO `tb_role_resource` VALUES (328, 2, 99);
INSERT INTO `tb_role_resource` VALUES (329, 2, 95);
INSERT INTO `tb_role_resource` VALUES (330, 2, 93);
INSERT INTO `tb_role_resource` VALUES (331, 2, 92);
INSERT INTO `tb_role_resource` VALUES (332, 3, 27);
INSERT INTO `tb_role_resource` VALUES (333, 3, 26);
INSERT INTO `tb_role_resource` VALUES (334, 3, 24);
INSERT INTO `tb_role_resource` VALUES (335, 3, 23);
INSERT INTO `tb_role_resource` VALUES (336, 3, 22);
INSERT INTO `tb_role_resource` VALUES (337, 3, 21);
INSERT INTO `tb_role_resource` VALUES (338, 3, 3);
INSERT INTO `tb_role_resource` VALUES (339, 3, 4);
INSERT INTO `tb_role_resource` VALUES (340, 3, 32);
INSERT INTO `tb_role_resource` VALUES (341, 3, 31);
INSERT INTO `tb_role_resource` VALUES (342, 3, 8);
INSERT INTO `tb_role_resource` VALUES (343, 3, 7);
INSERT INTO `tb_role_resource` VALUES (344, 3, 6);
INSERT INTO `tb_role_resource` VALUES (345, 3, 10);
INSERT INTO `tb_role_resource` VALUES (346, 3, 19);
INSERT INTO `tb_role_resource` VALUES (347, 3, 18);
INSERT INTO `tb_role_resource` VALUES (348, 3, 15);
INSERT INTO `tb_role_resource` VALUES (349, 3, 14);
INSERT INTO `tb_role_resource` VALUES (350, 3, 13);
INSERT INTO `tb_role_resource` VALUES (351, 3, 39);
INSERT INTO `tb_role_resource` VALUES (352, 3, 38);
INSERT INTO `tb_role_resource` VALUES (353, 3, 35);
INSERT INTO `tb_role_resource` VALUES (354, 3, 34);
INSERT INTO `tb_role_resource` VALUES (355, 3, 33);
INSERT INTO `tb_role_resource` VALUES (356, 3, 58);
INSERT INTO `tb_role_resource` VALUES (357, 3, 57);
INSERT INTO `tb_role_resource` VALUES (358, 3, 56);
INSERT INTO `tb_role_resource` VALUES (359, 3, 55);
INSERT INTO `tb_role_resource` VALUES (360, 3, 49);
INSERT INTO `tb_role_resource` VALUES (361, 3, 64);
INSERT INTO `tb_role_resource` VALUES (362, 3, 62);
INSERT INTO `tb_role_resource` VALUES (363, 3, 66);
INSERT INTO `tb_role_resource` VALUES (364, 3, 73);
INSERT INTO `tb_role_resource` VALUES (365, 3, 72);
INSERT INTO `tb_role_resource` VALUES (366, 3, 107);
INSERT INTO `tb_role_resource` VALUES (367, 3, 106);
INSERT INTO `tb_role_resource` VALUES (368, 3, 105);
INSERT INTO `tb_role_resource` VALUES (369, 3, 104);
INSERT INTO `tb_role_resource` VALUES (370, 3, 103);
INSERT INTO `tb_role_resource` VALUES (371, 3, 74);
INSERT INTO `tb_role_resource` VALUES (372, 3, 79);
INSERT INTO `tb_role_resource` VALUES (373, 3, 81);
INSERT INTO `tb_role_resource` VALUES (374, 3, 97);
INSERT INTO `tb_role_resource` VALUES (375, 3, 86);
INSERT INTO `tb_role_resource` VALUES (376, 3, 84);
INSERT INTO `tb_role_resource` VALUES (377, 3, 102);
INSERT INTO `tb_role_resource` VALUES (378, 3, 88);
INSERT INTO `tb_role_resource` VALUES (379, 1, 2);
INSERT INTO `tb_role_resource` VALUES (380, 1, 30);
INSERT INTO `tb_role_resource` VALUES (381, 1, 29);
INSERT INTO `tb_role_resource` VALUES (382, 1, 28);
INSERT INTO `tb_role_resource` VALUES (383, 1, 27);
INSERT INTO `tb_role_resource` VALUES (384, 1, 26);
INSERT INTO `tb_role_resource` VALUES (385, 1, 25);
INSERT INTO `tb_role_resource` VALUES (386, 1, 24);
INSERT INTO `tb_role_resource` VALUES (387, 1, 23);
INSERT INTO `tb_role_resource` VALUES (388, 1, 22);
INSERT INTO `tb_role_resource` VALUES (389, 1, 21);
INSERT INTO `tb_role_resource` VALUES (390, 1, 3);
INSERT INTO `tb_role_resource` VALUES (391, 1, 4);
INSERT INTO `tb_role_resource` VALUES (392, 1, 32);
INSERT INTO `tb_role_resource` VALUES (393, 1, 31);
INSERT INTO `tb_role_resource` VALUES (394, 1, 8);
INSERT INTO `tb_role_resource` VALUES (395, 1, 7);
INSERT INTO `tb_role_resource` VALUES (396, 1, 6);
INSERT INTO `tb_role_resource` VALUES (397, 1, 9);
INSERT INTO `tb_role_resource` VALUES (398, 1, 11);
INSERT INTO `tb_role_resource` VALUES (399, 1, 10);
INSERT INTO `tb_role_resource` VALUES (400, 1, 12);
INSERT INTO `tb_role_resource` VALUES (401, 1, 19);
INSERT INTO `tb_role_resource` VALUES (402, 1, 18);
INSERT INTO `tb_role_resource` VALUES (403, 1, 17);
INSERT INTO `tb_role_resource` VALUES (404, 1, 16);
INSERT INTO `tb_role_resource` VALUES (405, 1, 15);
INSERT INTO `tb_role_resource` VALUES (406, 1, 14);
INSERT INTO `tb_role_resource` VALUES (407, 1, 13);
INSERT INTO `tb_role_resource` VALUES (408, 1, 20);
INSERT INTO `tb_role_resource` VALUES (409, 1, 39);
INSERT INTO `tb_role_resource` VALUES (410, 1, 38);
INSERT INTO `tb_role_resource` VALUES (411, 1, 37);
INSERT INTO `tb_role_resource` VALUES (412, 1, 36);
INSERT INTO `tb_role_resource` VALUES (413, 1, 35);
INSERT INTO `tb_role_resource` VALUES (414, 1, 34);
INSERT INTO `tb_role_resource` VALUES (415, 1, 33);
INSERT INTO `tb_role_resource` VALUES (416, 1, 40);
INSERT INTO `tb_role_resource` VALUES (417, 1, 43);
INSERT INTO `tb_role_resource` VALUES (418, 1, 42);
INSERT INTO `tb_role_resource` VALUES (419, 1, 41);
INSERT INTO `tb_role_resource` VALUES (420, 1, 44);
INSERT INTO `tb_role_resource` VALUES (421, 1, 60);
INSERT INTO `tb_role_resource` VALUES (422, 1, 59);
INSERT INTO `tb_role_resource` VALUES (423, 1, 58);
INSERT INTO `tb_role_resource` VALUES (424, 1, 57);
INSERT INTO `tb_role_resource` VALUES (425, 1, 56);
INSERT INTO `tb_role_resource` VALUES (426, 1, 55);
INSERT INTO `tb_role_resource` VALUES (427, 1, 49);
INSERT INTO `tb_role_resource` VALUES (428, 1, 45);
INSERT INTO `tb_role_resource` VALUES (429, 1, 65);
INSERT INTO `tb_role_resource` VALUES (430, 1, 64);
INSERT INTO `tb_role_resource` VALUES (431, 1, 63);
INSERT INTO `tb_role_resource` VALUES (432, 1, 62);
INSERT INTO `tb_role_resource` VALUES (433, 1, 46);
INSERT INTO `tb_role_resource` VALUES (434, 1, 68);
INSERT INTO `tb_role_resource` VALUES (435, 1, 67);
INSERT INTO `tb_role_resource` VALUES (436, 1, 66);
INSERT INTO `tb_role_resource` VALUES (437, 1, 47);
INSERT INTO `tb_role_resource` VALUES (438, 1, 73);
INSERT INTO `tb_role_resource` VALUES (439, 1, 72);
INSERT INTO `tb_role_resource` VALUES (440, 1, 71);
INSERT INTO `tb_role_resource` VALUES (441, 1, 70);
INSERT INTO `tb_role_resource` VALUES (442, 1, 69);
INSERT INTO `tb_role_resource` VALUES (443, 1, 48);
INSERT INTO `tb_role_resource` VALUES (444, 1, 107);
INSERT INTO `tb_role_resource` VALUES (445, 1, 106);
INSERT INTO `tb_role_resource` VALUES (446, 1, 105);
INSERT INTO `tb_role_resource` VALUES (447, 1, 104);
INSERT INTO `tb_role_resource` VALUES (448, 1, 103);
INSERT INTO `tb_role_resource` VALUES (449, 1, 77);
INSERT INTO `tb_role_resource` VALUES (450, 1, 76);
INSERT INTO `tb_role_resource` VALUES (451, 1, 75);
INSERT INTO `tb_role_resource` VALUES (452, 1, 74);
INSERT INTO `tb_role_resource` VALUES (453, 1, 50);
INSERT INTO `tb_role_resource` VALUES (454, 1, 80);
INSERT INTO `tb_role_resource` VALUES (455, 1, 79);
INSERT INTO `tb_role_resource` VALUES (456, 1, 51);
INSERT INTO `tb_role_resource` VALUES (457, 1, 83);
INSERT INTO `tb_role_resource` VALUES (458, 1, 82);
INSERT INTO `tb_role_resource` VALUES (459, 1, 81);
INSERT INTO `tb_role_resource` VALUES (460, 1, 52);
INSERT INTO `tb_role_resource` VALUES (461, 1, 101);
INSERT INTO `tb_role_resource` VALUES (462, 1, 100);
INSERT INTO `tb_role_resource` VALUES (463, 1, 99);
INSERT INTO `tb_role_resource` VALUES (464, 1, 98);
INSERT INTO `tb_role_resource` VALUES (465, 1, 97);
INSERT INTO `tb_role_resource` VALUES (466, 1, 96);
INSERT INTO `tb_role_resource` VALUES (467, 1, 95);
INSERT INTO `tb_role_resource` VALUES (468, 1, 94);
INSERT INTO `tb_role_resource` VALUES (469, 1, 93);
INSERT INTO `tb_role_resource` VALUES (470, 1, 92);
INSERT INTO `tb_role_resource` VALUES (471, 1, 91);
INSERT INTO `tb_role_resource` VALUES (472, 1, 53);
INSERT INTO `tb_role_resource` VALUES (473, 1, 87);
INSERT INTO `tb_role_resource` VALUES (474, 1, 86);
INSERT INTO `tb_role_resource` VALUES (475, 1, 54);
INSERT INTO `tb_role_resource` VALUES (476, 1, 85);
INSERT INTO `tb_role_resource` VALUES (477, 1, 84);
INSERT INTO `tb_role_resource` VALUES (478, 1, 61);
INSERT INTO `tb_role_resource` VALUES (479, 1, 102);
INSERT INTO `tb_role_resource` VALUES (480, 1, 90);
INSERT INTO `tb_role_resource` VALUES (481, 1, 89);
INSERT INTO `tb_role_resource` VALUES (482, 1, 88);
INSERT INTO `tb_role_resource` VALUES (483, 1, 108);
INSERT INTO `tb_role_resource` VALUES (484, 2, 108);
INSERT INTO `tb_role_resource` VALUES (485, 3, 108);

-- ----------------------------
-- Table structure for tb_role_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_user`;
CREATE TABLE `tb_role_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_role_user
-- ----------------------------
INSERT INTO `tb_role_user` VALUES (1, 1, 1);
INSERT INTO `tb_role_user` VALUES (2, 2, 2);
INSERT INTO `tb_role_user` VALUES (3, 3, 3);
-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `tag_id` int NOT NULL AUTO_INCREMENT COMMENT '//标签id',
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '//标签名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '//创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '//更新时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO `tb_tag` VALUES (5, '测试', '2020-12-01 09:42:04', '2022-10-05 22:28:54');

-- ----------------------------
-- Table structure for tb_unique_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_unique_view`;
CREATE TABLE `tb_unique_view`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `views_count` int NOT NULL COMMENT '访问量',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_unique_view
-- ----------------------------
-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `web_site` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人网站',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '0:可用  1：逻辑删除',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `login_type` tinyint(1) NULL DEFAULT NULL COMMENT '登陆类型',
  `is_silence` tinyint(1) NULL DEFAULT 0 COMMENT '0：不禁言  1：禁言',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `avatar` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `ip_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `tb_user__uindex`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (NULL, 0, '努力才能及格，拼命才会优秀', '$2a$10$jalwt9mC7GWOkM5HjZroeOuD/1xhXcqvXgSYYX7ruCXBSeIX/ypW6', 'admin@qq.com', '2022-11-05 21:57:26', '2020-10-23 13:52:14', 'Ran-feiran', 1, 0, '密码6个0', 'https://static.ran-feiran.cn/2022/9/26/20220926190156.jpg', 1, '2729627149@qq.com', '1.183.161.218', '内蒙古自治区锡林郭勒盟 电信', '2022-11-05 21:57:26');
INSERT INTO `tb_user` VALUES (NULL, 0, '普通用户的简介', '$2a$10$jalwt9mC7GWOkM5HjZroeOuD/1xhXcqvXgSYYX7ruCXBSeIX/ypW6', 'user@qq.com', '2022-10-30 22:32:22', '2020-10-31 13:52:20', '普通用户', 0, 0, '密码6个0', 'https://static.ran-feiran.cn/avatar/user.png', 2, 'user@qq.com', '112.54.168.26', '内蒙古自治区 移动', '2022-10-30 22:32:11');
INSERT INTO `tb_user` VALUES (NULL, 0, NULL, '$2a$10$jalwt9mC7GWOkM5HjZroeOuD/1xhXcqvXgSYYX7ruCXBSeIX/ypW6', 'test@qq.com', '2022-11-05 22:16:38', '2020-12-01 09:10:04', '测试用户', 0, 0, '密码6个0', 'https://static.ran-feiran.cn/avatar/user.png', 3, 'test@qq.com', '1.183.161.218', '内蒙古自治区锡林郭勒盟 电信', '2022-11-05 22:16:38');
-- ----------------------------
-- Table structure for tb_user_login
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_login`;
CREATE TABLE `tb_user_login`  (
  `user_login_id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录的ip地址',
  `ip_sources` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址来源',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `avatar` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录头像',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`user_login_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_user_login
-- ----------------------------
-- ----------------------------
-- Table structure for tb_website_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_website_config`;
CREATE TABLE `tb_website_config`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `config` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置信息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_website_config
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 1;
