package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import ndk.pax.com.im.R
import ndk.pax.com.im.data.AddFriendListItem

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:19:42
 *
 */

class AddFriendListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs){


    init {
        View.inflate(context, R.layout.view_add_friend_item,this)
    }


    fun bindView(addFriendListItem: AddFriendListItem) {
        //获取当前item的用户名
        userName.text=addFriendListItem.username
        timestamp.text=addFriendListItem.timestamp
    }

}