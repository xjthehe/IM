package ndk.pax.com.im.presenter

import ndk.pax.com.im.contract.AddFriendContract
import com.hyphenate.chat.a.e
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import ndk.pax.com.im.data.AddFriendListItem
import ndk.pax.com.im.data.db.IMDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * User：Rowen
 * Description:查询添加好友接口来自Bmob
 * 时间: 2020/3/15:21:39
 *
 */

class AddFriendPresenter(val view:AddFriendContract.View) :AddFriendContract.Presenter{

   val addFriendItems= mutableListOf<AddFriendListItem>()

    override fun searchFriend(key: String) {
        //Bmob 查询好友 key=name
         queryBmob(key)
    }

    private fun queryBmob(key: String) {
        val query = BmobQuery<BmobUser>()
        query.addWhereEqualTo("username", key)
                .addWhereNotEqualTo("username", EMClient.getInstance().currentUser)

        query.findObjects(object : FindListener<BmobUser>() {
            override fun done(p0: MutableList<BmobUser>?, p1: BmobException?) {
                if(p1==null){
                    //先清空之前数组内容
                    addFriendItems.clear()
                    //处理数据
                    //创建AddFriendItem集合
                    val allContacts=IMDatabase.instance.getAllContacts()
                    doAsync {
//                            view.searchFriendSuccess() 必须在数据处理完之后回调成功接口
                            p0?.forEach {
                                //比对是否已经添加过好友
                                var isAdd=false
                                for (contact in allContacts){
                                    if(contact.name ==it.username){
                                        isAdd=true
                                    }
                                }

                                val addFriendItem=AddFriendListItem(it.username,it.createdAt,isAdd)
                                addFriendItems.add(addFriendItem)

                            }
                        uiThread { view.searchFriendSuccess() }
                    }
                }
                else view.searchFriendFail()
            }
        })

    }

}