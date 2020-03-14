package ndk.pax.com.im.presenter

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

    private fun loginEaseMob(userName: String, password: String) {

    }

}