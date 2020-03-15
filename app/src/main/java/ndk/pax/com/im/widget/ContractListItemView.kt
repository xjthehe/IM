package ndk.pax.com.im.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import ndk.pax.com.im.R

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


}
