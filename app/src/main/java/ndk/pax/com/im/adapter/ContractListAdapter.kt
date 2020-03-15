package ndk.pax.com.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ndk.pax.com.im.data.ContactListItem
import ndk.pax.com.im.ui.activity.ChatActivity
import ndk.pax.com.im.ui.activity.MainActivity
import ndk.pax.com.im.widget.ContractListItemView
import org.jetbrains.anko.startActivity

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
        val ueusername=contactItems.get(position).username
        //添加绑定事件
        contractListItemView.setOnClickListener {
            context.startActivity<ChatActivity>("ueusername" to ueusername)
        }
    }

    class ContractItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}