package ndk.pax.com.im.contract

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:11:04
 *
 */

interface ChatContract {
    interface Presenter:BasePresenter{
        fun sendMessage(contact:String,message:String)
    }

    interface View{
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFail()
    }

}