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
### 2.如果跳转主界面登录，


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
## 3.登录界面实现
### 3.1环信集成
[1.注册并创建应用](http://docs.easemob.com/cs/start)

[2.下载SDK](https://www.easemob.com/download/im)

[3.Android SDK导入](http://docs-im.easemob.com/im/android/sdk/import)

[4.SDK初始化](http://docs-im.easemob.com/im/android/sdk/basic)
### 3.2登录界面MVP协议
    interface LoginContract {

        interface Presenter : BasePresenter {
            fun login(userName: String, password: String);
        }

        interface View {
            fun onUserNameError();
            fun onPasswordError();
            fun onStartLogin();
            fun onLoginSuccees()
            fun onLoginFaile()
        }
    }
### 3.3登录界面View层实现
    interface View {
            fun onUserNameError();
            fun onPasswordError();
            fun onStartLogin();
            fun onLoginSuccees()
            fun onLoginFaile()
        }

### 3.4登录界面Presenter层实现

#### 用户输入校验
    1.用户名长度必须是3-20位，首字母必须是英文字母，其他字符除英文外还可以是数字或者下划线
    2.密码必须是3-20位数字。
    fun String.isVaildName():Boolean=this.matches(kotlin.text.Regex("^[a-zA-Z]\\w{2,19}$"));
    fun String.isValidPassword(): Boolean = this.matches(Regex("^[0-9]{3,20}$"));

#### p层实现

    class LoginPresenter(val view:LoginContract.View):LoginContract.Presenter{
        override fun login(userName: String, password: String) {
                  if(userName.isVaildName()){
                      //用户名合法
                      if(password.isValidPassword()){
                          //密码合法,开始登陆
                          view.onStartLogin();
                          //登录到环信
                          loginEaseMob(userName,password);
                      }else view.onPasswordError();
                  }else view.onUserNameError();
        }

### 3.5登录界面Model层实现
    #### 登录只需要登录到环信服务器
            登录成功后需要调用EMClient.getInstance().chatManager().loadAllConversations();和EMClient.getInstance().groupManager().loadAllGroups();。
            以上两个方法是为了保证进入主页面后本地会话和群组都 load 完毕
         EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        //在子线程通知view
                            uiThread {
                                view.onLoginSuccees();
                            }

                    }
    #### 隐藏软键盘
     val inputMethodManager by lazy {
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }

    fun hideSoftKeyboard(){
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }

    #### 动态权限申请

 if(hasWriteExternalStoragePermission()){
            val userNameString=userName.text.trim().toString();
            val passwordString=password.text.trim().toString();
            presenter.login(userNameString,passwordString);
        }else{
            applyWriteExternalStoragePermission();
        }

         //请求权限
            private fun applyWriteExternalStoragePermission() {
                val permissions= arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(this,permissions,0);
                //多次拒绝后，勾选不提示框，下面直接走else
            }

            //requestPermissions 回调函数
            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    login();
                }else toast(R.string.permission_denied);
            }

            //检查磁盘是否有权限
            private fun hasWriteExternalStoragePermission(): Boolean {
                val result=ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                return result==PackageManager.PERMISSION_GRANTED;
            }





## 4.注册界面功能需求
### 1.如果没有登录，延时2s，跳转登录界面
### 2.如果跳转主界面登录，