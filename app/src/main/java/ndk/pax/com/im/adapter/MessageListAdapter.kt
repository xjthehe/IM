package ndk.pax.com.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils
import ndk.pax.com.im.widget.ReceiveMessageItemView
import ndk.pax.com.im.widget.SendMessageItemView

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:14:07
 */

open class MessageListAdapter(val context: Context, val messages: List<EMMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            }else return ReceiveMessageViewHolder(ReceiveMessageItemView(context))
    }

    override fun getItemCount(): Int =messages.size
//    override fun getItemCount(): Int =addfriendItem.size

    //暂时无数据 不绑定
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
                val showTimestamp=isShowTimestamp(position)
                if(getItemViewType(position)== ITEM_TYPE_SEND_MESSAGE){
                    val sendMessageItemview=holder?.itemView as SendMessageItemView
                    sendMessageItemview.bindView(messages[position],showTimestamp)
                }else{
                    val receiveMessageItemview=holder?.itemView as ReceiveMessageItemView
                    receiveMessageItemview.bindView(messages[position],showTimestamp)
                }
    }
            //时间间隔太短不显示
            private fun isShowTimestamp(position:Int):  Boolean {
                //如果是第一条消息或者前一个消息间隔时间长
                var showTimestamp=true
                if(position>0){
                    showTimestamp=!DateUtils.isCloseEnough(messages[position].msgTime,messages[position-1].msgTime)
                }
                return showTimestamp
            }

    class SendMessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    }

    class ReceiveMessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {


    }
}