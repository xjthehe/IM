package ndk.pax.com.im.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.fragment_conversation.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.ConversationListAdapter
import ndk.pax.com.im.adapter.EMMessageListenerAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * User：Rowen
 * Description:消息界面
 * 时间: 2020/3/15:10:38
 */

class ConversationFragment:BaseFragment(){

    val conversations= mutableListOf<EMConversation>()

    override fun getLayoutId(): Int = R.layout.fragment_conversation;

    val messageListener=object :EMMessageListenerAdapter(){
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            loadConversation()
        }
    }
    override fun init() {
        super.init()
        headerTitle.text=getString(R.string.message)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
            adapter=ConversationListAdapter(context,conversations)
        }
        EMClient.getInstance().chatManager().addMessageListener(messageListener)

//        loadConversation()
    }

    private fun loadConversation() {
        doAsync {
            conversations.clear()
            //获取当前用户所有的会话，是一个集合
            val allConversations = EMClient.getInstance().chatManager().getAllConversations();
            conversations.addAll(allConversations.values)

            uiThread {
                recyclerView.adapter.notifyDataSetChanged()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

    override fun onResume() {
        super.onResume()
        loadConversation()
    }
}