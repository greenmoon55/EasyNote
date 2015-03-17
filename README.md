# EasyNote

## 简介

EasyNote 是一个简单的记笔记的应用，数据存储在 [LeanCloud](http://leancloud.cn)，需要微博登录。

本应用用到了 Leancloud 增删改查、ACL 权限控制和 SNS 接入的功能。


## 效果截图

<img src="https://raw.githubusercontent.com/greenmoon55/easynote/master/doc/images/1.png" width="400" />

<img src="https://raw.githubusercontent.com/greenmoon55/easynote/master/doc/images/2.png" width="400" />

<img src="https://raw.githubusercontent.com/greenmoon55/easynote/master/doc/images/3.png" width="400" />

## 部署

### 创建应用

注册并登录 [LeanCloud](http://leancloud.cn)，创建一个新应用，并记下 appId 和 appKey。

### 部署 Android 客户端

请按照以下步骤进行初始化：


#### 修改 appId 和 appKey

请修改 `com.greenmoon55.easynote.AVService.AVInit` 方法中初始化 `AVOSCloud` 部分，使用自己的 appId 和 appKey：

   ```
   AVOSCloud.initialize(ctx, <appId>, <appKey>); 
   ```

#### 配置微博

在后台选择 `管理/社交` 配置微博的 应用 ID 与 应用 Secret Key, 在成功保存以后，页面上能够得到相应的回调 URL 和登录 URL。

请修改 `com.greenmoon55.easynote.AuthActivity.onCreate`, 将 `SNS.setupPlatform` 里的地址配置成 `登录 URL`，同时请将回调 URL 填写到对应平台的 App 管理中心（比如新浪开放平台）。


## 开发相关

### 相关文档

* [Android 开发指南](https://leancloud.cn/docs/android_guide.html)
* [SNS 组件开发指南](https://leancloud.cn/docs/sns.html)

### 相关项目

本项目是我用来练手的，参考了下面两个应用

* [android-todolist](https://github.com/leancloud/android-todolist)
* [android-sns-demo](https://github.com/leancloud/android-sns-demo)

## Todo

- [ ] 本地缓存
- [ ] 账户登出

## 其他

* 不准备支持 Android 5.0 之前的版本
