package ndk.pax.com.im.presenter

import ndk.pax.com.im.contract.ChatContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage.ChatType
import com.hyphenate.chat.EMMessage
import ndk.pax.com.im.adapter.EMCallBackAdapter


/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:11:10
 *
 */

class ChatPresenter(val view:ChatContract.View):ChatContract.Presenter{
    val messages= mutableListOf<EMMessage>()

    override fun sendMessage(contact: String, message: String) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        val message = EMMessage.createTxtSendMessage(message, contact)
        //消息回调
        message.setMessageStatusCallback(object : EMCallBackAdapter() {
            override fun onSuccess() {
               uiThread {
                   view.onSendMessageSuccess()
               }
            }

            override fun onError(p0: Int, p1: String?) {
                uiThread { view.onSendMessageFail() }
            }
        })
        messages.add(message)
        view.onStartSendMessage()
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message)
    }

}