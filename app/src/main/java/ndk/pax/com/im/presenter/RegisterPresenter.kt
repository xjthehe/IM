package ndk.pax.com.im.presenter

import android.util.Log
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import ndk.pax.com.im.adapter.EMCallBackAdapter
import ndk.pax.com.im.contract.LoginContract
import ndk.pax.com.im.contract.RegisterContract
import ndk.pax.com.im.extentions.isVaildName
import ndk.pax.com.im.extentions.isValidPassword
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:13:43
 *
 */

class RegisterPresenter(val view:RegisterContract.View):RegisterContract.Presenter{
    override fun register(userName: String, password: String, confirmPassword: String) {
              if(userName.isVaildName()){
                  if(password.isValidPassword()){
                      if(password.equals(confirmPassword)){
                          //账号 密码 确认密码都正确
                          view.onStartRegister();
                           //开始注册Bmob
                            registerBmob(userName,password);

                      }else view.onConfirmPasswordError();
                  }else view.onPasswordError();
              }else view.onUserNameError();

    }

    private fun registerBmob(userName: String, password: String) {
        var bu=BmobUser();
        bu.username=userName;
        bu.setPassword(password);
//        bu.email="sendi@1632.com";
        bu.signUp<BmobUser>(object : SaveListener<BmobUser>() {
            override fun done(p0: BmobUser?, e: BmobException?) {
                    if(e==null){
                        //注册成功
                            Log.e("signUp","Bmob 注册成功")
                        //注册到环信
                        registerEaseMob(userName,password);
                    }else{
                        //注册失败
                        Log.e("signUp","Bmob 注册失败"+e.errorCode)
                        if(e.errorCode==202) view.onUserExit()
                        if(e.errorCode==203) view.onEmaiExit()

                        view.onRegisterFaile();
                    }

            }
        })
    }

    private fun registerEaseMob(userName: String, password: String) {
        Log.e("registerEaseMob","环信注册")
        doAsync {
            try {
                EMClient.getInstance().createAccount(userName, password);//同步
                uiThread { view.onRegisterSuccees() }
            }catch (e: HyphenateException){
                uiThread { view.onRegisterFaile() }
            }
            //注册失败会抛出HyphenateException
        }
    }

    //环信登陆
//    private fun loginEaseMob(userName: String, password: String) {
//
//        EMClient.getInstance().login(userName,password,object: EMCallBackAdapter() {
//            override fun onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//                Log.d("main", "登录聊天服务器成功！");
//                //在子线程通知view
//                    uiThread {
//                        view.onLoginSuccees();
//                    }
//
//            }
//
//            override fun onError(p0: Int, p1: String?) {
//                Log.d("main", "登录聊天服务器失败！");
//                uiThread {
//                    view.onLoginFaile();
//                }
//            }
//        })
//
//
//    }

}