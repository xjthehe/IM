package ndk.pax.com.im.contract;

/**
 * User：Rowen
 * Description: 协议类 MVP splash协议类
 * 时间: 2020/3/13:16:53
 */

 interface SplashContract{

    interface Presenter:BasePresenter{
        fun checkLoginState();//检查登录状态
    }


    interface View{
        fun onNotLoggedIn();//没有登陆UI处理---延迟2S,去LoginActivity
        fun onLoggedIn();//已经登录UI处理---MainActivity
    }
}
