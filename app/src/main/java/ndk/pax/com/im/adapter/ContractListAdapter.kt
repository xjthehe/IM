package ndk.pax.com.im.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMClient
import ndk.pax.com.im.R
import ndk.pax.com.im.data.ContactListItem
import ndk.pax.com.im.ui.activity.ChatActivity
import ndk.pax.com.im.ui.activity.MainActivity
import ndk.pax.com.im.widget.ContractListItemView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:14:07
 *
 */

class ContractListAdapter(val context: Context, val contactItems: MutableList<ContactListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ContractItemViewHolder(ContractListItemView(context))
    }

    override fun getItemCount(): Int =contactItems.size

    //暂时无数据 不绑定
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val contractListItemView = holder?.itemView as ContractListItemView
        contractListItemView.bindView(contactItems[position])
        //获取当前item的用户名
        val username=contactItems.get(position).username
        //添加绑定事件【点击事件】
        contractListItemView.setOnClickListener {
            context.startActivity<ChatActivity>("username" to username)
        }

        //长按删除
        contractListItemView.setOnLongClickListener {
            val message= String.format(context.getString(R.string.delete_friend_message),username)

            AlertDialog.Builder(context)
                    .setTitle(R.string.delete_friend_title)
                    .setMessage(message)
                    .setNegativeButton(R.string.cancel,null)
                    .setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialog, which ->
                        deleteFriend(username)
                    }).show()
                true
        }
    }
    //删除长按的好友
    private fun deleteFriend(username: String) {
            EMClient.getInstance().contactManager().aysncDeleteContact(username,object :EMCallBackAdapter(){
                override fun onSuccess() {
                    context.runOnUiThread {
                        toast(R.string.delete_friend_success)
                        //刷新列表

                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    context.runOnUiThread {
                        toast(R.string.delete_friend_failed)
                    }
                }
            })

        }



    class ContractItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}