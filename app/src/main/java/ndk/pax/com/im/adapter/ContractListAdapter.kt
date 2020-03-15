package ndk.pax.com.im.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ndk.pax.com.im.widget.ContractListItemView

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:14:07
 *
 */

class ContractListAdapter(val context:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ContractItemViewHolder(ContractListItemView(context))
    }

    override fun getItemCount(): Int =30

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

    }

    class ContractItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}