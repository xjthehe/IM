package ndk.pax.com.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMConversation
import ndk.pax.com.im.ui.activity.ChatActivity
import ndk.pax.com.im.widget.ConversationListItemView
import org.jetbrains.anko.startActivity

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:20:43
 *
 */

class ConversationListAdapter(val context: Context, val conversations: MutableList<EMConversation>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ConversationListItemViewHolder(ConversationListItemView(context))
    }

    override fun getItemCount(): Int =conversations.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val conversationListItemView = holder?.itemView as ConversationListItemView
        conversationListItemView.bindView(conversations[position])
        //会话界面item点击事件
        conversationListItemView.setOnClickListener {
            context.startActivity<ChatActivity>("username" to conversations[position].conversationId())
        }

    }

    class ConversationListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }


}