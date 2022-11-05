## 博客介绍

<p align=center>
  <a href="https://www.ran-feiran.cn">
    <img src="https://static.ran-feiran.cn/config/eccc1fc8a5792333d10234bdb2291400.jpg" alt="一个简单的技术分享页" style="border-radius: 50%;width:250px;hight:250px" >
  </a>
</p>


<p align=center>
   基于Springboot + Vue 开发的前后端分离博客
</p>
<p align="center">
   <a target="_blank" href="https://github.com/ran-feiran/Blog">
      <img src="https://img.shields.io/hexpm/l/plug.svg"/>
      <img src="https://img.shields.io/badge/JDK-1.8+-green.svg"/>
      <img src="https://img.shields.io/badge/springboot-2.6.4.RELEASE-green"/>
      <img src="https://img.shields.io/badge/vue-2.6.11-green"/>
      <img src="https://img.shields.io/badge/mysql-8.0.26-green"/>
      <img src="https://img.shields.io/badge/mybatis--plus-3.5.1-green"/>
      <img src="https://img.shields.io/badge/redis-5.0.5-green"/>
      <img src="https://img.shields.io/badge/elasticsearch-7.14.0-green"/>
      <img src="https://img.shields.io/badge/rabbitmq-3.9.11-green"/>
   </a>
</p>



[在线地址](#在线地址) | [目录结构](#目录结构) | [项目特点](#项目特点) | [技术介绍](#技术介绍) | [运行环境](#运行环境) | [开发环境](#开发环境) | [项目截图](#项目截图) | [快速开始](#快速开始) | [注意事项](#注意事项) | [项目总结](#项目总结) | [交流群](#交流群)

## 在线地址

**项目链接：** [www.ran-feiran.cn](https://www.ran-feiran.cn)

**后台链接：** [master.ran-feiran.cn](https://master.ran-feiran.cn)

测试账号：test@qq.com，密码：000000，可登入后台查看。

**Github地址：** [https://github.com/ran-feiran/Blog](https://github.com/ran-feiran/Blog)

**在线接口文档地址：** [https://www.ran-feiran.cn/api/doc.html](https://www.ran-feiran.cn/api/doc.html)

您的star是我坚持的动力，感谢大家的支持，欢迎提交pr共同改进项目。

## 目录结构

前端项目位于Vue下，blog为前台，admin为后台。

后端项目位于Springboot下。

SQL文件位于根目录下的mysql8.sql，需要MYSQL8以上版本。

可直接导入该项目于本地，修改后端配置文件中的数据库等连接信息，项目中使用到的关于邮箱功能和第三方授权登录等需要自行开通。

当你克隆项目到本地后可使用邮箱账号：admin@qq.com，密码：000000 进行登录，也可自行注册账号并将其修改为admin角色。

本地访问接口文档地址：[http://127.0.0.1:8080/doc.html](http://127.0.0.1:8080/doc.html)

**ps：请先运行后端项目，再启动前端项目，前端项目配置由后端动态加载。** 

```java
blog-springboot
├── annotation    --  自定义注解
├── api			  --  api模块
├── aspect        --  aop模块
├── authentication--  用户认证模块
├── authority     --  权限处理模块
├── config        --  配置模块
├── constant      --  常量模块
├── consumer      --  MQ消费者模块
├── controller    --  控制器模块
├── dto           --  dto模块
├── enums         --  枚举模块
├── exception     --  自定义异常模块
├── handler       --  处理器模块（扩展Security过滤器，自定义Security提示信息,执行定时任务等）
├── mapper        --  框架核心模块
├── pojo          --  实体类模块
├── result        --  统一结果返回模块
├── service       --  服务模块
├── strategy      --  策略模块（用于扩展第三方登录，搜索模式，上传文件模式等策略）
├── utils         --  工具类模块
└── vo            --  vo模块
```

## 项目特点

- 前台参考"Hexo"的"Butterfly"设计，美观简洁，响应式体验好。
- 后台参考"element-admin"设计，侧边栏，历史标签，面包屑自动生成。
- 采用Markdown编辑器，写法简单。
- 评论支持表情输入回复等，样式参考Valine。
- 添加音乐播放器，支持在线搜索歌曲。
- 前后端分离部署，适应当前潮流。
- 接入第三方登录，减少注册成本。
- 留言采用弹幕墙，更加炫酷。
- 支持代码高亮和复制，图片预览，深色模式等功能，提升用户体验。
- 搜索文章支持高亮分词，响应速度快。
- 新增文章目录、推荐文章等功能，优化用户体验。
- 新增aop注解实现日志管理。  
- 支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新。
- 后台管理支持修改背景图片，博客配置等信息，操作简单。
- 代码支持多种搜索模式（Elasticsearch或MYSQL），支持多种上传模式（本地或生产），可支持配置。
- 代码遵循阿里巴巴'大部分'开发规范，利于开发者学习。

## 技术介绍

**前端：** vue + vuex + vue-router + axios + vuetify + element + echarts

**后端：** SpringBoot + nginx + docker + SpringSecurity + Swagger2 + MyBatisPlus + Mysql + Redis + elasticsearch + RabbitMQ + MaxWell + knife4j + hutool

**其他：** 接入QQ第三方登录，接入腾讯云人机验证

## 运行环境

**服务器：** 腾讯云2核2G  华为云2核4G  CentOS7.5

**CDN ：** 暂无

**对象存储**：本地存储

**最低配置：** 1核2G服务器（关闭ElasticSearch）

## 开发环境

|开发工具|说明|
|-|-|
|IDEA|Java开发工具IDE|
|VSCode|Vue开发工具IDE|
|Navicat|MySQL远程连接工具|
|Redis Desktop Manager|Redis远程连接工具|
|X-shell|Linux远程连接工具|
|Xftp|Linux文件上传工具|

|开发环境|版本|
|-|-|
|JDK|1.8|
|MySQL|8.0.26|
|Redis|5.0.5|
|Elasticsearch|7.14.0|
|RabbitMQ|3.9.11|

## 项目截图

![QQ截图20210320171133.png](https://static.ran-feiran.cn/articles/180c552afe115c0f668c3e9dae492dcb.jpg)

![QQ截图20210320235519 1.jpg](https://static.ran-feiran.cn/articles/26aad4df83d1999028fcc8bbcb22ce9b.jpg)

![QQ截图20210320171338.png](https://static.ran-feiran.cn/articles/18bd3222c5f9c7db65f62f48acb1da89.png)

![QQ截图20210320171401.png](https://static.ran-feiran.cn/articles/ceb391bc04df4d99cdbaaccf70ebf1cf.png)

## 快速开始

### 项目环境安装

详见文章[Docker安装运行环境](https://www.ran-feiran.cn/articles/21)

### 项目配置

详见文章[项目配置教程](https://www.ran-feiran.cn/articles/22)

### Docker部署项目

详见文章[项目部署教程](https://www.ran-feiran.cn/articles/25)

## 注意事项

- 项目拉下来运行后，可到后台管理页面网站配置处修改博客相关信息.
- 邮箱配置，第三方授权配置需要自己申请。
- ElasticSearch需要自己先创建索引，项目运行环境教程中有介绍。

## 项目总结

博客作为新手入门项目是十分不错的，项目所用的技术栈覆盖的也比较广，适合初学者学习。主要难点在于权限管理、第三方登录、文件策略上传这块。做的不好的地方请大家见谅，有问题的或者有好的建议可以私聊联系我。

## 交流群







