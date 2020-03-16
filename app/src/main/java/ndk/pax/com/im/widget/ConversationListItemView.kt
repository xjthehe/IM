package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.view_conversation_item.view.*
import ndk.pax.com.im.R
import java.util.*

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:20:38
 *
 */

class ConversationListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.view_conversation_item,this)
    }

    fun bindView(emConversation: EMConversation) {

            userName.text=emConversation.conversationId()
            //会话文本
            if(emConversation.lastMessage.type== EMMessage.Type.TXT){
                 val body = emConversation.lastMessage.body as EMTextMessageBody
                    lastMessage.text=body.message
             }else  lastMessage.text=context.getString(R.string.no_text_message)
            //时间戳
            val timestampString = DateUtils.getTimestampString(Date(emConversation.lastMessage.msgTime))
            timestamp.text=timestampString
            //当前会话未读数目
            if(emConversation.unreadMsgCount>0){
                unreadCount.visibility=View.VISIBLE
                unreadCount.text=emConversation.unreadMsgCount.toString()
            }else unreadCount.visibility=View.GONE
    }

}