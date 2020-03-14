package ndk.pax.com.im.contract

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:21:18
 *
 */

interface RegisterContract{

    interface Presenter:BasePresenter{
        fun register(userName:String,password:String);
    }

    interface View{
        fun onUserNameError();
        fun onPasswordError();
        fun onConfirmPasswordError();
        fun onStartRegister();
        fun onRegisterSuccees()
        fun onRegisterFaile()
    }





}