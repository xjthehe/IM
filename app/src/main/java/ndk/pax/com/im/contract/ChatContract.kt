package ndk.pax.com.im.contract

import com.hyphenate.chat.EMMessage

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:11:04
 *
 */

interface ChatContract {
    interface Presenter:BasePresenter {
        fun sendMessage(contact: String, message: String)
        fun addMessage(username: String, p0: MutableList<EMMessage>?)
        fun loadMessage(username: String)
        fun loadMoreMessages(username: String)
    }

    interface View{
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFail()
        fun onMessageLoaded()
        fun onMoreMessageLoaded(size: Int)
    }

}