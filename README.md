# IM即时通讯
这个项目就是一个基本即时通讯项目
## 1.1项目介绍

## 1.2IM

## 1.3MVC MVP MVVM

## 1.4版本配置
### 1.配置Project的build.gradle
    "buildscript {
        ext.compile_sdk_version=28
        ext.build_tool_version="27.0.3"
        ext.min_sdk_version=15
        ext.target_sdk_version=28
        ext.support_version="27.0.1"
        ext.kotlin_version = '1.2.30'
        ext.anko_version = '0.10.0'

        repositories {
            google()
            jcenter()
        }"
 ### 2.配置APP模块下的build.gradle.
            implementation "org.jetbrains.anko:anko-commons:$anko_version"//Anko通用库
            implementation "org.jetbrains.anko:anko-sqlite:$anko_version"//Anko sqlite库
            implementation "com.android.support:appcompat-v7:$support_version"
            implementation "com.android.support:cardview-v7:$support_version"
                implementation ('com.roughike:bottom-bar:2.3.1'){
                    exclude module: 'design'
                }
## 1.5资源拷贝
![Image text](
https://github.com/xjthehe/IM/blob/master/app/src/main/res/mipmap-hdpi/zy.jpg
)

## 2.功能需求
### 1.如果没有登录，延时2s，跳转登录界面
### 2.如果登录，跳转主界面


## MVP实现
### 2.1 View层实现
    interface SplashContract{

        interface Presenter:BasePresenter{
            fun checkLoginState();//检查登录状态
        }

        interface View{
            fun onNotLoggedIn();//没有登陆UI处理
            fun onLoggedIn();//已经登录UI处理
        }
    }
### 2.2 Presenter层实现
    class SplashPresenter(val view:SplashContract.View):SplashContract.Presenter{
        override fun checkLoginState() {
            if(isLoggedIn())view.onLoggedIn()else view.onNotLoggedIn();
        }

        private fun isLoggedIn(): Boolean {
            return false;
        }
    }
## 3.Model层实现
### 3.1环信集成
[1.注册并创建应用](http://docs.easemob.com/cs/start)

[2.下载SDK](https://www.easemob.com/download/im)

[3.Android SDK导入](http://docs-im.easemob.com/im/android/sdk/import)

[4.SDK初始化](http://docs-im.easemob.com/im/android/sdk/basic)


