package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_contact_item.view.*
import ndk.pax.com.im.R
import ndk.pax.com.im.data.ContactListItem

/**
 * User：Rowen
 * Description:自定义控件 说白了就是适配器item布局 写成一个自定义控件(布局)
 * 时间: 2020/3/15:13:58
 *
 */

class ContractListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_contact_item,this)
    }



    fun bindView(contactListItem: ContactListItem) {
        //是否显示首字母
            if(contactListItem.showFirstLetter){
                firstLetter.visibility=View.VISIBLE
                firstLetter.text= contactListItem.firstLetter.toString()
            }else firstLetter.visibility=View.GONE

            userName.text=contactListItem.username.toString()
    }

}
