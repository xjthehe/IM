package ndk.pax.com.im.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import ndk.pax.com.im.R
import ndk.pax.com.im.data.AddFriendListItem
import ndk.pax.com.im.data.ContactListItem
import ndk.pax.com.im.ui.activity.ChatActivity
import ndk.pax.com.im.ui.activity.MainActivity
import ndk.pax.com.im.widget.AddFriendListItemView
import ndk.pax.com.im.widget.ContractListItemView
import ndk.pax.com.im.widget.SendMessageItemView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:14:07
 */

class MessageListAdapter(val context: Context, val messages: List<EMMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val ITEM_TYPE_SEND_MESSAGE=0
        val ITEM_TYPE_RECEIVE_MESSAGE=1
    }

    override fun getItemViewType(position: Int): Int{
        if(messages[position].direct()==EMMessage.Direct.SEND){
            return ITEM_TYPE_SEND_MESSAGE
        }else{
            return ITEM_TYPE_RECEIVE_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            if(viewType== ITEM_TYPE_SEND_MESSAGE){
                return SendMessageViewHolder(SendMessageItemView(context))
            }else return ReceiveMessageViewHolder(SendMessageItemView(context))
    }

    override fun getItemCount(): Int =messages.size
//    override fun getItemCount(): Int =addfriendItem.size

    //暂时无数据 不绑定
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
                if(getItemViewType(position)== ITEM_TYPE_SEND_MESSAGE){
                    val sendMessageItemview=holder?.itemView as SendMessageItemView
                    sendMessageItemview.bindView(messages[position])
                }
    }

    class SendMessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    }

    class ReceiveMessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    }
}