package ndk.pax.com.im.presenter

import ndk.pax.com.im.contract.AddFriendContract
import com.hyphenate.chat.a.e
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.FindListener
import com.hyphenate.chat.EMClient
import ndk.pax.com.im.data.AddFriendListItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


/**
 * User：Rowen
 * Description:
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
                    //处理数据
                    //创建AddFriendItem集合
                    doAsync {
//                            view.searchFriendSuccess() 必须在数据处理完之后回调成功接口
                            p0?.forEach {
                                val addFriendItem=AddFriendListItem(it.username,it.createdAt)
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