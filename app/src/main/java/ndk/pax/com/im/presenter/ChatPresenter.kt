package ndk.pax.com.im.presenter

import ndk.pax.com.im.contract.ChatContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import ndk.pax.com.im.adapter.EMCallBackAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:11:10
 *
 */

class ChatPresenter(val view:ChatContract.View):ChatContract.Presenter{

    companion object {
        val PAGE_SIZE=10
    }

    //初始化，加载更多向下滑动recycleview时候加载更多数据。
    override fun loadMoreMessages(username: String) {

        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            val startMsgId=messages[0].msgId
            val messagesDb = conversation.loadMoreMsgFromDB(startMsgId, PAGE_SIZE)
            messages.addAll(0,messagesDb)
            uiThread {
                view.onMoreMessageLoaded(messagesDb.size)
            }
        }
    }

    override fun loadMessage(username: String) {
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            //获取此会话的所有消息
            //将加载消息标记为已读
            conversation.markAllMessagesAsRead()

            messages.addAll(conversation.allMessages)
            uiThread {
                view.onMessageLoaded()
            }

        }
    }

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


    override fun addMessage(username: String, p0: MutableList<EMMessage>?) {
        //加入到消息列表
        p0?.let { messages.addAll(it) }
        //更新消息为已读消息
        //获取联系人会话，然后标记
        val conversation = EMClient.getInstance().chatManager().getConversation(username)
        conversation.markAllMessagesAsRead()
    }
}