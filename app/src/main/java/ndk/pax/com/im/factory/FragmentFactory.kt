package ndk.pax.com.im.factory

import android.support.v4.app.Fragment
import android.util.Log
import ndk.pax.com.im.R
import ndk.pax.com.im.ui.fragment.ContactFragment
import ndk.pax.com.im.ui.fragment.ConversationFragment
import ndk.pax.com.im.ui.fragment.DynamicFragment

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:10:44
 *
 */

class FragmentFactory private constructor(){

    val conversation by lazy {
        ConversationFragment()
    }

    val contact by lazy {
        ContactFragment()
    }

    val dynamic by lazy {
        DynamicFragment()
    }
    //私有构造方法+伴生对象 单例实现
    companion object {
        val instance=FragmentFactory();
    }

        fun getInstatance(tabId:Int):Fragment?{
            Log.e("tabId","tabId"+tabId);
            when(tabId){
                R.layout.fragment_conversation->return conversation;
                R.layout.fragment_contacts->return contact;
                R.layout.fragment_dynamic->return dynamic;
            }
            return null;
        }

}