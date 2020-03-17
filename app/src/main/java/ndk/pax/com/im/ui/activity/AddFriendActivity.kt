package ndk.pax.com.im.ui.activity

import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.view.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.AddFriendListAdapter
import ndk.pax.com.im.contract.AddFriendContract
import ndk.pax.com.im.data.AddFriendListItem
import ndk.pax.com.im.presenter.AddFriendPresenter
import ndk.pax.com.im.widget.AddFriendListItemView
import org.jetbrains.anko.toast

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:19:29
 *
 */

class AddFriendActivity :BaseActivity(),AddFriendContract.View{
    val presenter by lazy { AddFriendPresenter(this) }

    //view层实现 搜索朋友成功
    override fun searchFriendSuccess() {
        dismissProgress()
        toast(R.string.send_add_friend_success)
        recyclerView.adapter.notifyDataSetChanged()
    }
    //view层实现 搜索失败
    override fun searchFriendFail() {
            dismissProgress()
            toast(R.string.send_add_friend_failed)
    }

    override fun getLayoutResId(): Int= R.layout.activity_add_friend
    override fun init(){
        super.init()
        headerTitle.text=getString(R.string.add_friend)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
            adapter=AddFriendListAdapter(context,presenter.addFriendItems)
        }

        search.setOnClickListener {
            search()
        }

        userName.setOnEditorActionListener { v, actionId, event ->
            search()
             true
        }
    }

        fun search(){
            hideSoftKeyboard()
            showProgress(getString(R.string.searching))
            val string = userName.text.trim().toString()
            presenter.searchFriend(string)
        }



}