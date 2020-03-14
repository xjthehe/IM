package ndk.pax.com.im

import android.os.Handler
import ndk.pax.com.im.contract.SplashContract
import ndk.pax.com.im.presenter.SplashPresenter
import org.jetbrains.anko.startActivity

/**
 * User:Rowen
 * Description:Splash界面布局
 * 时间: 2020/3/13:14:30
 */

class SplashActivity :BaseActivity(),SplashContract.View{
    val presenter=SplashPresenter(this);

    companion object {
        val DELAY=2000L;
    }

    val handler by lazy {
        Handler();
    }

    //延时2s,跳转到登录界面
    override fun onNotLoggedIn() {
        handler.postDelayed(object :Runnable{
            override fun run() {
                //anko库
                startActivity<LoginActivity>();
                finish();


            }
        },DELAY);
    }

    //登陆过,跳转到主界面
    override fun onLoggedIn() {
        startActivity<MainActivity>();
        finish();
    }

    override fun getLayoutResId(): Int =
         R.layout.activity_splash;


    override fun init() {
        presenter.checkLoginState();
    }


}