package ndk.pax.com.im.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
import ndk.pax.com.im.adapter.EMMessageListenerAdapter
import ndk.pax.com.im.adapter.MessageListAdapter
import ndk.pax.com.im.contract.ChatContract
import ndk.pax.com.im.presenter.ChatPresenter
import org.jetbrains.anko.toast

/**
 * User：Rowen
 * Description:聊天界面
 * 时间: 2020/3/15:15:59
 *
 */

class ChatActivity:BaseActivity(),ChatContract.View{
    override fun onMessageLoaded() {
        recyclerView.adapter.notifyDataSetChanged()
        scrollToBottom()
    }

    val presenter by lazy {
        ChatPresenter(this)
    }
    lateinit var username:String

    val msgListener=object : EMMessageListenerAdapter() {

        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            presenter.addMessage(username,p0)
            runOnUiThread {
                recyclerView.adapter.notifyDataSetChanged()
                scrollToBottom()
            }
        }

    }





    override fun onStartSendMessage() {
        //通知recyleview刷新列表
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onSendMessageSuccess() {
        recyclerView.adapter.notifyDataSetChanged()
        toast(R.string.send_message_success)
        //清空编辑框
        edit.text.clear()

        //滑动到底部
        scrollToBottom()
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition(presenter.messages.size-1)
    }

    override fun onSendMessageFail() {
        toast(R.string.send_message_failed)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun getLayoutResId(): Int= R.layout.activity_chat

    //初始化
    override fun init() {
        super.init()
        initHeader()
        initEditext()
        initRecycleView()
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
        send.setOnClickListener {
            send()
        }

        //刚进入聊天界面初始化聊天信息
        presenter.loadMessage(username)
    }

    private fun initRecycleView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(context)
            adapter=MessageListAdapter(context,presenter.messages )
            //滑动监听
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    //recycleview是一个空闲时候
                    //检查是否滑到顶部
                    if(newState==RecyclerView.SCROLL_STATE_IDLE){
                        //如果第一个可见条目为0，就是到顶部了
                        val linearLayoutManager = layoutManager as LinearLayoutManager
                            if(linearLayoutManager.findFirstVisibleItemPosition()==0){
                                //到顶部了，加载更多数据
                                presenter.loadMoreMessages(username)
                            }

                    }
                }
            })
        }
    }

    override fun onMoreMessageLoaded(size: Int) {
        recyclerView.adapter.notifyDataSetChanged()
        recyclerView.scrollToPosition(size)
    }
    //点击按钮 发送消息
    fun send(){
        hideSoftKeyboard()
        val message = edit.text.trim().toString()
        presenter.sendMessage(username,message)
    }

    private fun initEditext() {
        edit.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                //如果输入文本长度大于0，发送按钮enable
                send.isEnabled=!s.isNullOrEmpty()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        edit.setOnEditorActionListener { v, actionId, event ->
            send()
            true
        }
    }

    private fun initHeader() {
        back.visibility= View.VISIBLE
        back.setOnClickListener { finish() }

        //获取聊天名字
         username= intent.getStringExtra("username")
        val titleString= String.format(getString(R.string.chat_title),username)
        headerTitle.text=titleString

        //Editext输入情况
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(msgListener)
    }
}