/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.19 : Database - myblog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myblog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `myblog`;

/*Table structure for table `tb_about` */

DROP TABLE IF EXISTS `tb_about`;

CREATE TABLE `tb_about` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext COMMENT '//内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_about` */

insert  into `tb_about`(`id`,`content`) values (1,'?1111?1111?1');

/*Table structure for table `tb_api` */

DROP TABLE IF EXISTS `tb_api`;

CREATE TABLE `tb_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '//api名',
  `url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//请求地址',
  `method` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '请求方式',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_api` */

insert  into `tb_api`(`id`,`api_id`,`name`,`url`,`method`,`pid`,`description`,`sort`) values (1,1,'API模块',NULL,'',NULL,'api模块',1),(2,NULL,'分类条件查询查询api的信息,父pid查询模块所有api','/api/listApiInfoBack','GET',1,'分类条件查询查询api的信息,父pid查询模块所有api',101),(3,NULL,'根据swagger2的api文档读取api信息存入数据库','/api/saveOrUpdateApiFromSwagger','post',1,'根据swagger2的api文档读取api信息存入数据库',102),(4,2,'关于模块',NULL,NULL,NULL,'About Controller',2),(5,NULL,'获取关于我的信息','/getAbout','GET',2,'/获取关于我的信息',201),(6,NULL,'更新关于我','/updateAbout','POST',2,'更新关于我',202),(7,3,'分类模块',NULL,NULL,NULL,'Category Controller',3),(8,NULL,'新增或者编辑分类','/category/addOrEditCategory','POST',3,'新增或者编辑分类',301),(9,NULL,'根据id删除分类','/category/deleteCategory','DELETE',3,'根据id删除分类',302),(10,NULL,'获取所有的标签','/category/getCategoryList','GET',3,'获取所有的标签',303),(11,NULL,'查看博客展示分类列表','/category/listCategories','GET',3,'查看博客展示分类列表',304),(12,NULL,'分页条件查询分类列表','/category/listCategory','GET',3,'分页条件查询分类列表',305),(13,NULL,'查看分类下对应的文章','/category/*','GET',3,'查看分类下对应的文章',306),(14,4,'博客信息模块',NULL,NULL,NULL,'User Info Controller',4),(15,NULL,'获取博主的基本信息','/blogInfo/getBlogInfo','GET',4,'获取博主的基本信息',401),(16,5,'友链模块',NULL,NULL,NULL,'Friend Link Controller',5),(17,NULL,'添加或者修改友链','/friendLink/addOrEditFriendLink','POST',5,'添加或者修改友链',501),(18,NULL,'根据关键词分页查询友链','/friendLink/listLinks','GET',5,'根据关键词分页查询友链',502),(19,6,'文章模块',NULL,NULL,NULL,'Article Controller',6),(20,NULL,'查看首页文章','/article/articles','GET',6,'查看首页文章',601),(21,NULL,'根据id删除文章','/article/deleteArticleById','DELETE',6,'根据id删除文章',602),(22,NULL,'根据文章id查询文章信息','/article/getArticleById','GET',6,'根据文章id查询文章信息',603),(23,NULL,'根据文章标题分页查询文章列表','/article/listArticle','GET',6,'根据文章标题分页查询文章列表',604),(24,NULL,'保存或者更新文章','/article/saveOrUpdateArticle','POST',6,'保存或者更新文章',605),(25,NULL,'查询文章归档','/article/archives','GET',6,'查询文章归档',606),(26,NULL,'查看首页文章','/article/blog/*','GET',6,'查看首页文章',607),(27,7,'标签模块',NULL,NULL,NULL,'Tag Controller',7),(28,NULL,'新增或者编辑标签','/tag/addOrEditTag','POST',7,'新增或者编辑标签',701),(29,NULL,'根据id删除标签','/tag/deleteTag','DELETE',7,'根据id删除标签',702),(30,NULL,'后台获取所有的标签','/tag/getTagList','GET',7,'后台获取所有的标签',703),(31,NULL,'后台分页条件查询标签列表','/tag/listTags','GET',7,'后台分页条件查询标签列表',704),(32,NULL,'博客查看标签列表','/tag/tags','GET',7,'博客查看标签列表',705),(33,NULL,'查看标签下对应的文章','/tag/*','GET',7,'查看标签下对应的文章',706),(34,8,'用户模块',NULL,NULL,NULL,'User Controller',8),(35,NULL,'根据用户角色和昵称分页查询用户列表','/user/getUserList','GET',8,'根据用户角色和昵称分页查询用户列表',801),(36,NULL,'分页单表查询用户列表','/user/getUserListSignal','GET',8,'/分页单表查询用户列表',802),(37,NULL,'注册用户','/user/registerUser','POST',8,'注册用户',803),(38,NULL,'发送邮箱验证码','/user/sendEmailCode','GET',8,'发送邮箱验证码',804),(39,9,'用户留言模块',NULL,NULL,NULL,'Message Controller',9),(40,NULL,'查看留言列表','/message/getMessageList','GET',9,'查看留言列表',901),(41,NULL,'添加留言','/message/messages','POST',9,'添加留言',902),(42,10,'用户登录信息模块',NULL,NULL,NULL,'User Login Controller',10),(43,NULL,'分页获取用户登录信息列表','/userLogin/getUserInfoList','GET',10,'分页获取用户登录信息列表',1001),(44,11,'菜单展示模块',NULL,NULL,NULL,'Menu Controller',11),(45,NULL,'获取展示菜单列表','/menu/getMenuList','GET',11,'获取展示菜单列表',1101),(46,12,'角色模块',NULL,NULL,NULL,'Role Controller',12),(47,NULL,'查询角色信息','/role/getRoleList','GET',12,'查询角色信息',1201),(48,13,'评论模块',NULL,NULL,NULL,'Comment Controller',13),(49,NULL,'查询评论','/comment/comments','GET',13,'查询评论',1301),(50,NULL,'添加评论或回复','/comment/comments','POST',13,'添加评论或回复',1302),(51,NULL,'查看回复评论','/comment/comments/replies','GET',13,'查看回复评论',1303),(52,NULL,'分页获取用户评论列表','/comment/getUserCommentList','GET',13,'分页获取用户评论列表',1304),(53,14,'阿里云对象存储ossAPI模块',NULL,NULL,NULL,'Ali Oss Controller',14),(54,NULL,'图片上传','/uploadImage','POST',14,'图片上传',1401),(55,15,'ADMIN全部持有','/**','ALL',NULL,'所有权限',15),(56,NULL,'通过角色ID获取角色名字','/role/getRoleNameById','GET',12,'查询角色名字',1202),(57,NULL,'获取角色对应的API列表','/role/getAssignedApiIdByUserRoleId','GET',12,'获取角色对应的API列表',1203),(58,NULL,'保存角色API列表','/role/saveRolePermissionList/*','POST',12,'保存角色API列表',1204),(59,NULL,'文章点赞','/article/like/*','POST',6,'文章点赞',608),(60,NULL,'批量删除文章','/article/del/batch','POST',6,'批量删除文章',609),(61,NULL,'批量删除分类','/category/del/batch','POST',3,'批量删除分类',307),(62,NULL,'评论点赞','/comment/*/like','POST',13,'评论点赞',1305),(63,NULL,'删除用户评论','/comment/delComments','POST',13,'删除用户评论',1306),(64,NULL,'获取后台信息','/home/getHomeInfo','GET',4,'获取后台信息',402),(65,NULL,'获取所有菜单','/menu/getAllMenuList','GET',11,'获取所有菜单',1102),(66,NULL,'删除留言','/message/deleteMessages','DELETE',9,'删除留言',903),(67,NULL,'保存用户菜单','/role/saveRoleMenu/*','POST',12,'保存用户菜单',1205),(68,NULL,'查询角色信息','/role/findRoleList','GET',12,'查询角色信息',1206),(69,NULL,'新增或更新角色','/role/saveOrUpdateRole','POST',12,'新增或更新角色',1207),(70,NULL,'删除角色','/role/deleteRole','DELETE',12,'删除角色',1208),(71,NULL,'批量删除标签','/tag/del/batch','POST',7,'批量删除标签',707),(72,NULL,'删除登录用户信息','/userLogin/deleteUser','DELETE',10,'删除登录用户信息',1002),(73,NULL,'下载','/download/*','GET',14,'下载',1402),(74,NULL,'通过id更新用户信息','/user/updateUserById','PUT',8,'通过id更新用户信息',805),(75,NULL,'更新用户角色','/user/updateUserRole','POST',8,'更新用户角色',806),(76,NULL,'添加用户','/user/addUser','POST',8,'添加用户',807),(77,NULL,'禁言设置','/user/updateSilenceById','POST',8,'禁言设置',808),(78,NULL,'逻辑删除','/user/logicDeleteUser','DELETE',8,'逻辑删除',809),(79,NULL,'修改博客信息','/user/info','PUT',8,'修改博客信息',810),(80,NULL,'上传头像','/user/avatar','POST',8,'上传头像',811),(81,NULL,'修改密码','/user/forgetPassword','POST',8,'修改密码',812);

/*Table structure for table `tb_article` */

DROP TABLE IF EXISTS `tb_article`;

CREATE TABLE `tb_article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//文章主键id',
  `article_title` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//文章标题',
  `article_content` longtext COMMENT '//文章内容',
  `article_cover` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//文章封面',
  `category_id` int(11) DEFAULT NULL COMMENT '//分类id',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '//是否置顶 0：不置顶 1：置顶',
  `is_draft` tinyint(1) DEFAULT '0' COMMENT '//是否草稿 0：不是草稿 1:草稿',
  `create_time` datetime DEFAULT NULL COMMENT '//创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '//更新时间',
  PRIMARY KEY (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_article` */

insert  into `tb_article`(`article_id`,`article_title`,`article_content`,`article_cover`,`category_id`,`is_top`,`is_draft`,`create_time`,`update_time`) values (11,'第一篇测试博客','**测试博客**','http://localhost:8080/download/356a30fd09674ef1be5fcff064801a58.jpeg',1,1,0,'2020-12-01 09:43:38','2022-07-29 17:36:25'),(14,'第二篇测试博客','**大家好，我是唱跳rap练习两年半的java实习生**','http://localhost:8080/download/dc209b6c1e004531852b8b105d35967b.jpeg',1,0,0,'2022-07-21 22:19:18','2022-07-29 18:02:47'),(15,'第三篇测试博客','?1111?1111','',1,0,0,'2022-07-22 08:45:37','2022-07-26 18:04:21'),(16,'第四篇测试博客','![net.jpeg](http://localhost:8080/download/1004411afb684607bc072b22f2a334be.jpeg)','',2,0,0,'2022-07-29 23:01:09','2022-07-29 23:01:09');

/*Table structure for table `tb_article_tag` */

DROP TABLE IF EXISTS `tb_article_tag`;

CREATE TABLE `tb_article_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//article 和 tag的关联 id',
  `tag_id` int(11) DEFAULT NULL COMMENT '//标签id',
  `article_id` int(11) DEFAULT NULL COMMENT '//文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_article_tag` */

insert  into `tb_article_tag`(`id`,`tag_id`,`article_id`) values (22,5,11),(23,6,11),(27,5,14),(28,5,15),(32,6,15),(33,5,16),(34,6,16),(35,7,16),(36,8,14);

/*Table structure for table `tb_category` */

DROP TABLE IF EXISTS `tb_category`;

CREATE TABLE `tb_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//分类id',
  `category_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//分类名称',
  `create_time` datetime DEFAULT NULL COMMENT '//创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '//更新时间',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_category` */

insert  into `tb_category`(`category_id`,`category_name`,`create_time`,`update_time`) values (1,'测试','2020-11-25 09:29:08','2022-06-15 18:38:58'),(2,'JAVA','2022-07-31 22:12:45','2022-07-31 22:12:51'),(3,'vue','2022-08-06 15:16:49','2022-08-06 15:16:49'),(4,'项目','2022-08-06 15:16:56','2022-08-06 15:16:56'),(5,'SpringBoot','2022-08-06 15:17:08','2022-08-06 15:17:08');

/*Table structure for table `tb_comment` */

DROP TABLE IF EXISTS `tb_comment`;

CREATE TABLE `tb_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//评论主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '//用户id',
  `article_id` int(11) DEFAULT NULL COMMENT '//文章id',
  `comment_content` longtext COMMENT '//评论内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '//评论时间',
  `reply_id` int(11) DEFAULT NULL COMMENT '//回复用户id',
  `parent_id` int(11) DEFAULT NULL COMMENT '//父评论id',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '//是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_comment` */

insert  into `tb_comment`(`id`,`user_id`,`article_id`,`comment_content`,`create_time`,`reply_id`,`parent_id`,`is_delete`) values (4,2,11,'hello','2022-07-13 10:37:54',NULL,NULL,0),(5,3,11,'nice to meet you','2022-07-13 10:51:20',NULL,NULL,0),(8,3,11,'2333','2022-08-02 16:27:00',2,4,0),(9,3,11,'11111','2022-08-02 16:27:00',3,4,0),(10,1,NULL,'hello world','2022-08-02 17:14:43',NULL,NULL,0),(11,1,NULL,'测试成功','2022-08-02 18:03:39',1,10,0),(12,1,NULL,'111','2022-08-02 18:08:40',1,10,0),(13,1,NULL,'12333','2022-08-02 18:11:03',1,10,0),(14,1,NULL,'23333','2022-08-02 18:13:37',1,10,0),(15,2,NULL,'hello world','2022-08-02 18:21:58',NULL,NULL,0),(16,2,NULL,'测试成功','2022-08-02 18:22:31',2,15,0),(17,2,NULL,'111','2022-08-02 18:23:16',2,15,0),(18,2,NULL,'23333','2022-08-02 18:23:16',2,15,0),(19,2,NULL,'12333','2022-08-02 18:23:16',2,15,0),(20,1,11,'hello111','2022-08-02 18:46:04',3,4,0),(21,2,NULL,'哈哈哈<img src= \'//i0.hdslb.com/bfs/emote/b5a5898491944a4268360f2e7a84623149672eb6.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>，我是测试一号','2022-08-04 10:52:57',NULL,NULL,0),(22,2,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/685612eadc33f6bc233776c6241813385844f182.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 10:56:39',NULL,NULL,0),(23,2,NULL,'你好啊，调皮鬼<img src= \'//i0.hdslb.com/bfs/emote/8290b7308325e3179d2154327c85640af1528617.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 11:02:08',2,22,0),(24,2,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/de4c0783aaa60ec03de0a2b90858927bfad7154b.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 11:10:49',NULL,NULL,0),(25,2,NULL,'111','2022-08-04 11:14:28',NULL,NULL,0),(26,2,NULL,'又报错了，啊啊啊<img src= \'//i0.hdslb.com/bfs/emote/44667b7d9349957e903b1b62cb91fb9b13720f04.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 11:32:25',NULL,NULL,0),(27,2,NULL,'搞定喽<img src= \'//i0.hdslb.com/bfs/emote/cb89184c97e3f6d50acfd7961c313ce50360d70f.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 11:33:47',2,26,0),(28,3,NULL,'23333','2022-08-04 11:35:35',2,26,0),(29,1,11,'23333','2022-08-04 11:40:03',NULL,NULL,0),(30,1,11,'111','2022-08-04 11:41:22',1,4,0),(31,1,11,'分页也ok了','2022-08-04 11:42:22',2,4,0),(32,1,11,'测试分页','2022-08-04 11:42:47',1,4,0),(35,1,NULL,'***','2022-08-04 15:16:21',NULL,NULL,0),(36,1,NULL,'敏感词过滤OK','2022-08-04 15:16:44',1,35,0),(37,1,NULL,'alert(\'1\')','2022-08-04 15:19:01',NULL,NULL,0),(38,1,NULL,'11111<img src= \'//i0.hdslb.com/bfs/emote/b5a5898491944a4268360f2e7a84623149672eb6.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 22:55:49',2,26,0),(39,1,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/b5a5898491944a4268360f2e7a84623149672eb6.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 22:56:37',1,26,0),(40,1,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/63c9d1a31c0da745b61cdb35e0ecb28635675db2.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-04 22:56:53',1,37,0),(41,2,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/b5a5898491944a4268360f2e7a84623149672eb6.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-05 09:21:48',NULL,NULL,0),(42,2,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/b5a5898491944a4268360f2e7a84623149672eb6.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-05 09:29:52',2,41,0),(43,1,NULL,'*','2022-08-05 09:39:45',NULL,NULL,0),(45,1,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/63c9d1a31c0da745b61cdb35e0ecb28635675db2.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-05 09:57:10',1,41,0),(46,1,NULL,'<img src= \'//i0.hdslb.com/bfs/emote/685612eadc33f6bc233776c6241813385844f182.png@112w_112h.webp\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-05 09:57:16',1,41,0),(48,1,NULL,'**','2022-08-05 09:59:55',NULL,NULL,0),(49,1,NULL,'<img src= \'https://static.talkxj.com/emoji/cy.jpg\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-05 10:03:55',NULL,NULL,0),(50,1,16,'111','2022-08-07 22:34:22',NULL,NULL,0),(51,1,16,'233','2022-08-07 22:34:36',1,50,0),(52,13,NULL,'大佬，你们好<img src= \' https://static.talkxj.com/emoji/dx.jpg\' width=\'22\' height=\'20\' style=\'padding: 0 1px\'/>','2022-08-08 10:47:54',NULL,NULL,0);

/*Table structure for table `tb_dict` */

DROP TABLE IF EXISTS `tb_dict`;

CREATE TABLE `tb_dict` (
  `id` int(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `tb_dict` */

insert  into `tb_dict`(`id`,`value`,`type`) values (1,'el-icon-user','icon'),(2,'el-icon-house','icon'),(3,'el-icon-s-custom','icon'),(4,'el-icon-s-custom','icon'),(5,'el-icon-platform-eleme','icon'),(6,'el-icon-s-cooperation','icon'),(7,'el-icon-s-tools','icon'),(8,'el-icon-document','icon'),(9,'el-icon-menu','icon'),(10,'el-icon-s-data','icon'),(11,'el-icon-office-building','icon'),(12,'el-icon-suitcase','icon'),(13,'el-icon-chat-dot-round','icon');

/*Table structure for table `tb_friend_link` */

DROP TABLE IF EXISTS `tb_friend_link`;

CREATE TABLE `tb_friend_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '友链主键id',
  `link_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '链接名',
  `link_avatar` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '链接头像',
  `link_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '链接地址',
  `link_intro` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '链接简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_friend_link` */

insert  into `tb_friend_link`(`id`,`link_name`,`link_avatar`,`link_address`,`link_intro`,`create_time`) values (1,'风丶宇的个人博客','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F8a%2F54%2F9e%2F8a549e5130a10daad7765a21b9f35fd9.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653105894&t=33f55fba77e07b768f2f347dc4255de2','https://www.talkxj.com/','成事在人 谋事在天','2020-12-01 01:46:05'),(2,'Ran-feiran的个人博客','http://localhost:8080/download/43dcb4111c624a66b0e44b041bdc8413.jpg','https://www.ran-feiran.cn','胜天半子','2022-07-21 17:36:30');

/*Table structure for table `tb_menu` */

DROP TABLE IF EXISTS `tb_menu`;

CREATE TABLE `tb_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//后台菜单id',
  `menu_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//菜单列表名字',
  `menu_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//菜单的url',
  `menu_component` varchar(255) DEFAULT NULL COMMENT '// 菜单对应得.vue',
  `parent_id` int(11) DEFAULT NULL COMMENT '//菜单的父ID',
  `menu_sort` int(255) DEFAULT NULL COMMENT '//菜单排序',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//描述',
  `menu_icon` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//按钮图标',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_menu` */

insert  into `tb_menu`(`menu_id`,`menu_name`,`menu_url`,`menu_component`,`parent_id`,`menu_sort`,`description`,`menu_icon`) values (1,'首页','/home','/Home',0,1,'首页','el-icon-s-home'),(2,'用户管理',NULL,NULL,0,2,'用户管理','el-icon-s-custom'),(3,'用户列表','/userList','/user/UserList',2,201,'用户列表','el-icon-user-solid'),(4,'登录信息','/userLoginInfo','/user/UserLoginInfo',2,202,'用户登录信息','el-icon-location'),(7,'权限管理',NULL,NULL,0,4,'权限管理','el-icon-notebook-1'),(8,'角色分配','/assignRole','/assign/AssignRole',7,701,'角色分配','el-icon-s-flag'),(9,'菜单分配','/assignMenu','/assign/AssignMenu',7,702,'菜单分配','el-icon-s-grid'),(10,'接口分配','/assignAPI','/assign/AssignAPI',7,703,'API分配','el-icon-files'),(11,'文章管理',NULL,NULL,0,5,'文章管理','el-icon-tickets'),(12,'博客列表','/blogList','/blog/BlogList',11,1101,'博客列表','el-icon-document'),(13,'新增博客','/blogAdd','/blog/BlogAdd',11,1102,'新增博客','el-icon-folder-add'),(14,'标签管理','/tag','/blog/Tag',11,1103,'标签管理','el-icon-collection-tag'),(15,'分类管理','/category','/blog/Category',11,1104,'分类管理','el-icon-menu'),(16,'消息留言',NULL,NULL,0,6,'消息留言','el-icon-message'),(17,'评论列表','/comment','/messageInfo/Comment',16,1601,'评论列表','el-icon-chat-dot-round'),(18,'留言列表','/message','/messageInfo/Message',16,1602,'留言列表','el-icon-message'),(19,'友链其他',NULL,NULL,0,7,'友链其他','el-icon-guide'),(20,'友链列表','/friendLink','/other/FriendLink',19,1901,'友链列表','el-icon-link'),(21,'关于我','/about','/other/About',19,1902,'关于我','el-icon-view'),(22,'接口文档','/swagger','/other/Swagger',19,1903,'接口文档','el-icon-printer'),(23,'个人中心','/settings','/Settings',0,8,'个人中心','el-icon-s-tools');

/*Table structure for table `tb_message` */

DROP TABLE IF EXISTS `tb_message`;

CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//ip',
  `ip_source` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//ip地理位置',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//昵称',
  `avatar` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '//头像',
  `message_content` varchar(500) DEFAULT NULL COMMENT '//留言内容',
  `time` tinyint(1) DEFAULT NULL COMMENT '//弹幕速度',
  `create_time` datetime DEFAULT NULL COMMENT '//发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_message` */

insert  into `tb_message`(`id`,`ip_address`,`ip_source`,`nickname`,`avatar`,`message_content`,`time`,`create_time`) values (1,'127.0.0.1','','游客','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fup.enterdesk.com%2Fedpic_source%2F8a%2F54%2F9e%2F8a549e5130a10daad7765a21b9f35fd9.jpg&refer=http%3A%2F%2Fup.enterdesk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653105894&t=33f55fba77e07b768f2f347dc4255de2','留言测试?',4,'2020-12-03 03:37:50'),(2,'127.0.0.1','','游客','https://gravatar.loli.net/avatar/d41d8cd98f00b204e9800998ecf8427e?d=mp&v=1.4.14','大家好啊',3,'2022-08-01 16:03:12'),(3,'127.0.0.1','','游客','https://gravatar.loli.net/avatar/d41d8cd98f00b204e9800998ecf8427e?d=mp&v=1.4.14','你好啊',4,'2022-08-01 16:06:19'),(5,'127.0.0.1','','游客','https://gravatar.loli.net/avatar/d41d8cd98f00b204e9800998ecf8427e?d=mp&v=1.4.14','搜索',9,'2022-08-08 17:04:05');

/*Table structure for table `tb_role` */

DROP TABLE IF EXISTS `tb_role`;

CREATE TABLE `tb_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色名',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `tb_role_pk` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_role` */

insert  into `tb_role`(`role_id`,`role_name`,`description`) values (1,'ADMIN','管理员'),(2,'USER','普通用户'),(3,'TEST','后台测试'),(6,'GUEST','游客');

/*Table structure for table `tb_role_api` */

DROP TABLE IF EXISTS `tb_role_api`;

CREATE TABLE `tb_role_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `api_id` int(11) DEFAULT NULL COMMENT 'api的主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_role_api` */

insert  into `tb_role_api`(`id`,`role_id`,`api_id`) values (1,1,55),(2,6,5),(3,6,13),(4,6,10),(5,6,11),(6,6,12),(7,6,15),(8,6,25),(9,6,26),(10,6,20),(11,6,22),(12,6,23),(13,6,32),(14,6,33),(15,6,38),(16,6,41),(17,6,51),(18,6,49),(19,6,73),(20,2,55),(21,6,18),(22,6,40),(23,6,37),(24,6,81);

/*Table structure for table `tb_role_menu` */

DROP TABLE IF EXISTS `tb_role_menu`;

CREATE TABLE `tb_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '//角色id',
  `menu_id` int(11) DEFAULT NULL COMMENT '//菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_role_menu` */

insert  into `tb_role_menu`(`id`,`role_id`,`menu_id`) values (1,1,1),(2,1,2),(3,1,3),(8,2,1),(9,1,11),(10,1,12),(11,1,13),(12,1,4),(13,1,14),(14,1,15),(15,1,16),(16,1,17),(17,1,18),(18,1,19),(19,1,20),(20,1,21),(21,1,22),(22,1,7),(23,1,8),(24,1,9),(25,1,10),(26,1,23),(27,3,1),(31,3,3),(32,3,7),(33,3,8),(34,3,9),(53,3,2),(54,3,11),(55,3,12),(57,3,16),(58,3,17),(59,3,19),(60,3,20),(62,3,22),(63,3,23),(68,2,3),(69,2,8),(70,2,2),(71,2,7);

/*Table structure for table `tb_role_user` */

DROP TABLE IF EXISTS `tb_role_user`;

CREATE TABLE `tb_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_role_user` */

insert  into `tb_role_user`(`id`,`user_id`,`role_id`) values (1,1,1),(2,2,2),(3,3,1),(5,4,2),(9,5,2),(11,13,2);

/*Table structure for table `tb_tag` */

DROP TABLE IF EXISTS `tb_tag`;

CREATE TABLE `tb_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '//标签id',
  `tag_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '//标签名称',
  `create_time` datetime DEFAULT NULL COMMENT '//创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '//更新时间',
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_tag` */

insert  into `tb_tag`(`tag_id`,`tag_name`,`create_time`,`update_time`) values (5,'测试','2020-12-01 09:42:04',NULL),(6,'开心',NULL,'2022-06-15 23:23:16'),(7,'伤心',NULL,NULL),(8,'旅游','2022-07-13 10:18:49','2022-07-13 10:18:49');

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `web_site` varchar(400) DEFAULT NULL COMMENT '个人网站',
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表主键',
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像',
  `intro` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '简介',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '0:可用  1：逻辑删除',
  `is_silence` tinyint(1) DEFAULT '0' COMMENT '0：不禁言  1：禁言',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `tb_user__uindex` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


/*Table structure for table `tb_user_login` */

DROP TABLE IF EXISTS `tb_user_login`;

CREATE TABLE `tb_user_login` (
  `user_login_id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `ip_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录的ip地址',
  `ip_sources` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'ip地址来源',
  `login_type` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录类型',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `avatar` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '登录头像',
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_login_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

/*Data for the table `tb_user_login` */

insert  into `tb_user_login`(`user_login_id`,`nickname`,`ip_address`,`ip_sources`,`login_type`,`login_time`,`last_login_time`,`avatar`,`status`) values (1,'Ran-feiran','127.0.0.1','','WINDOWS_10-CHROME10','2020-10-23 13:52:14','2022-08-09 10:02:16','http://localhost:8080/download/43dcb4111c624a66b0e44b041bdc8413.jpg',0),(2,'普通用户','127.0.0.1','','WINDOWS_10-CHROME10','2020-10-31 13:52:20','2022-08-08 23:11:14','http://localhost:8080/download/6bc2e775c8fe4e2ebb06f2be872e9db9.jpeg',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
