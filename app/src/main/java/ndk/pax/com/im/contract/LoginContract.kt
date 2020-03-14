package ndk.pax.com.im.contract

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:12:25
 *
 */

interface LoginContract{

    interface Presenter:BasePresenter{
        fun login(userName:String,password:String);
    }

    interface View{
        fun onUserNameError();
        fun onPasswordError();
        fun onStartLogin();
        fun onLoginSuccees()
        fun onLoginFaile()




    }

}