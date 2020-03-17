package ndk.pax.com.im.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.ContractListAdapter
import ndk.pax.com.im.contract.ContactContract
import ndk.pax.com.im.presenter.ContactPresenter
import org.jetbrains.anko.toast
import com.hyphenate.EMContactListener
import com.hyphenate.chat.EMClient
import ndk.pax.com.im.adapter.EMContactListenerAdapter
import ndk.pax.com.im.ui.activity.AddFriendActivity
import ndk.pax.com.im.ui.activity.MainActivity
import ndk.pax.com.im.widget.SlideBar
import org.jetbrains.anko.startActivity


/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:10:38
 *
 */

class ContactFragment:BaseFragment(), ContactContract.View{
    val contactPresnter by lazy {
        ContactPresenter(this)
    }
    val contactListtener=object : EMContactListenerAdapter() {
        override fun onContactDeleted(p0: String?) {
            //重新获取联系人
            contactPresnter.loadContracts()
        }

        //添加好友成功
        override fun onContactAdded(p0: String?) {
            //重新获取联系人
            contactPresnter.loadContracts()
        }
    }



   //view层实现  加载成功
    override fun onLoadContractSuccess() {
        swipeRefreshLayout.isRefreshing=false
        recyclerView.adapter.notifyDataSetChanged()
    }
    //加载失败
    override fun onLoadContractFailed() {
        swipeRefreshLayout.isRefreshing=false
        context?.toast(R.string.load_contacts_failed)
    }

    override fun getLayoutId(): Int = R.layout.fragment_contacts;

    //初始化
    override fun init() {
        super.init()
        initHeader()
        initSwipRefreshLayout()
        initRecycleView()
        //联系人状态监听器，当联系人被删除，会监听回调
        EMClient.getInstance().contactManager().setContactListener(contactListtener)
        initSliderBar()
        //加载联系人 P层触发
        contactPresnter.loadContracts()
    }
    //sliderbar监听回调 字母显示 隐藏
    private fun initSliderBar() {
        slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
            override fun onSectionChange(firstletter: String) {
                section.visibility = View.VISIBLE
                section.text = firstletter
                //recyclerView滑动到字母对应的下标
                Log.e("letter", firstletter)
                recyclerView.smoothScrollToPosition(getPosition(firstletter))
            }

            override fun onSlideFinish() {
                section.visibility = View.GONE
            }
        }
    }

    private fun initRecycleView() {
        //apply语句
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
            contactPresnter.contactItems.clear()
            adapter = ContractListAdapter(context, contactPresnter.contactItems)
        }
    }

    private fun initSwipRefreshLayout() {
        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.qq_blue)
            isRefreshing = true
            setOnRefreshListener { contactPresnter.loadContracts() }
        }
    }

    private fun initHeader() {
        headerTitle.text = getString(R.string.contact)
        add.visibility = View.VISIBLE

        //添加好友右上角*****************
        add.setOnClickListener {
            context?.startActivity<AddFriendActivity>()
        }
    }

    private fun getPosition(firstletter: String): Int =
                contactPresnter.contactItems.binarySearch {
                    contactListItem ->contactListItem.firstLetter.minus(firstletter[0])
                }


    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().contactManager().
                removeContactListener(contactListtener)
    }
}