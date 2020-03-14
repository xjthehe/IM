package ndk.pax.com.im.presenter

import com.hyphenate.chat.EMClient
import ndk.pax.com.im.contract.SplashContract

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/13:18:36
 *
 */

class SplashPresenter(val view:SplashContract.View):SplashContract.Presenter{
    override fun checkLoginState() {
        if(isLoggedIn())view.onLoggedIn()else view.onNotLoggedIn();
    }

    //是否登录环信服务器
    private fun isLoggedIn(): Boolean {
        return EMClient.getInstance().isConnected
        &&EMClient.getInstance().isLoggedInBefore;
    }
}