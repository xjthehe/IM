package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import ndk.pax.com.im.R

/**
 * User：Rowen
 * Description:收消息布局
 * 时间: 2020/3/16:10:58
 *
 */

class ReceiveMessageItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_receive_message_item,this)
    }
}