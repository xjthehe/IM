package ndk.pax.com.im.presenter

import android.util.Log
import com.hyphenate.chat.EMChatManager
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import ndk.pax.com.im.contract.BasePresenter
import ndk.pax.com.im.contract.ContactContract
import ndk.pax.com.im.data.ContactListItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:14:32
 *
 */

class ContactPresenter(val view:ContactContract.View):ContactContract.Presenter{

    val contactItems= mutableListOf<ContactListItem>()

    //加载联系人
    override fun loadContracts() {
        doAsync {
                try {
                    //获取当前用户名好友列表，注意，默认新用户没有好友，可以去环信后台自行添加好友
                    val username= EMClient.getInstance().contactManager().allContactsFromServer
                    Log.e("username","username"+username.size)
                    username.forEach {
                        val contactItem=ContactListItem(it,it[0].toUpperCase())
                        contactItems.add(contactItem)
                    }

                    uiThread {
                        view.onLoadContractSuccess()
                    }
                }catch ( e: HyphenateException){
                    uiThread {
                        view.onLoadContractFailed()
                    }
                }
        }


    }


}