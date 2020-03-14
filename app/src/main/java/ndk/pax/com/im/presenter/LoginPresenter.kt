package ndk.pax.com.im.presenter

import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import ndk.pax.com.im.adapter.EMCallBackAdapter
import ndk.pax.com.im.contract.LoginContract
import ndk.pax.com.im.extentions.isVaildName
import ndk.pax.com.im.extentions.isValidPassword

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:13:43
 *
 */

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

    //环信登陆
    private fun loginEaseMob(userName: String, password: String) {

        EMClient.getInstance().login(userName,password,object: EMCallBackAdapter() {
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                //在子线程通知view
                    uiThread {
                        view.onLoginSuccees();
                    }

            }

            override fun onError(p0: Int, p1: String?) {
                Log.d("main", "登录聊天服务器失败！");
                uiThread {
                    view.onLoginFaile();
                }
            }
        })


    }

}