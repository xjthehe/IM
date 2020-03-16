package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.view_receive_message_item.view.*
import ndk.pax.com.im.R
import java.util.*

/**
 * User：Rowen
 * Description:收消息布局
 * 时间: 2020/3/16:10:58
 *
 */

class ReceiveMessageItemView(context: Context?, attrs: AttributeSet?=null):RelativeLayout(context, attrs) {
    init {
        View.inflate(context, R.layout.view_receive_message_item,this)
    }

    fun bindView(emMessage: EMMessage, showTimestamp: Boolean) {
        updateTimestap(emMessage,showTimestamp)
        updateMessage(emMessage)
    }

    private fun updateMessage(emMessage: EMMessage) {
        if(emMessage.type==EMMessage.Type.TXT){
            receiveMessage.text=(emMessage.body as EMTextMessageBody).message
        }else{
            receiveMessage.text=context.getString(R.string.no_text_message)
        }
    }

    //更新时间戳
    private fun updateTimestap(emMessage: EMMessage, showTimestamp: Boolean) {
        if(showTimestamp) {
            timestamp.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
        }else timestamp.visibility=View.GONE
    }
}