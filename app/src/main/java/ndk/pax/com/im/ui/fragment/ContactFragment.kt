package ndk.pax.com.im.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.ContractListAdapter
import ndk.pax.com.im.contract.ContactContract
import ndk.pax.com.im.presenter.ContactPresenter
import org.jetbrains.anko.toast

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

   //加载成功
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

    override fun init() {
        super.init()
        headerTitle.text=getString(R.string.contact)
        add.visibility= View.VISIBLE

        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.qq_blue)
            isRefreshing=true
        }
        //recycleView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
            adapter=ContractListAdapter(context,contactPresnter.contactItems)
        }


        //加载联系人 P层触发
        contactPresnter.loadContracts()
    }

    
}