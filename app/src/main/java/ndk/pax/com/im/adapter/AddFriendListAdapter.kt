package ndk.pax.com.im.adapter

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hyphenate.chat.EMClient
import ndk.pax.com.im.R
import ndk.pax.com.im.data.AddFriendListItem
import ndk.pax.com.im.data.ContactListItem
import ndk.pax.com.im.ui.activity.ChatActivity
import ndk.pax.com.im.ui.activity.MainActivity
import ndk.pax.com.im.widget.AddFriendListItemView
import ndk.pax.com.im.widget.ContractListItemView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:14:07
 */

class AddFriendListAdapter(val context: Context, val addfriendItem: MutableList<AddFriendListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return AddFriendItemViewHolder(AddFriendListItemView(context))
    }

    override fun getItemCount(): Int =addfriendItem.size
//    override fun getItemCount(): Int =addfriendItem.size

    //暂时无数据 不绑定
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val addfirendListItemView = holder?.itemView as AddFriendListItemView
        addfirendListItemView.bindView(addfriendItem[position])

    }

    class AddFriendItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}