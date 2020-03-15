package ndk.pax.com.im.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.ContractListAdapter

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:10:38
 *
 */

class ContactFragment:BaseFragment(){
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
            adapter=ContractListAdapter(context)
        }



    }

    
}