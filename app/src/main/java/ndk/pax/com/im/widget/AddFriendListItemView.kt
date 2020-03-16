package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.view_add_friend_item.view.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.EMCallBackAdapter
import ndk.pax.com.im.data.AddFriendListItem
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

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
        if(addFriendListItem.add){
            add.isEnabled=false
            add.text=context.getString(R.string.already_added)
        }else{
            add.isEnabled=true
            add.text=context.getString(R.string.add)
        }
        //获取当前item的用户名
        userName.text=addFriendListItem.username
        timestamp.text=addFriendListItem.timestamp

        add.setOnClickListener {
            addFriend(addFriendListItem.username)
        }
    }

    private fun addFriend(username: String) {
        //参数为要添加的好友的username和添加理由
        EMClient.getInstance().contactManager().
                aysncAddContact(username, null,object :EMCallBackAdapter(){
                    override fun onSuccess() {
                        context.runOnUiThread {
                            toast(R.string.send_add_friend_success)
                        }
                    }
                        override fun onError(p0: Int, p1: String?) {
                            context.runOnUiThread {
                                toast(R.string.send_add_friend_failed)
                            }
                        }
                });


    }


}