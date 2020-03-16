package ndk.pax.com.im.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.header.*
import ndk.pax.com.im.R
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

    val presenter by lazy {
        ChatPresenter(this)
    }
    lateinit var username:String

    override fun onStartSendMessage() {
        //通知recyleview刷新列表
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onSendMessageSuccess() {
        recyclerView.adapter.notifyDataSetChanged()
        toast(R.string.send_message_success)
        //清空编辑框
        edit.text.clear()
    }

    override fun onSendMessageFail() {
        toast(R.string.send_message_failed)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun getLayoutResId(): Int= R.layout.activity_chat
    override fun init() {
        super.init()
        initHeader()
        initEditext()
        initRecycleView()
        send.setOnClickListener {
            send()
        }
    }

    private fun initRecycleView() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(context)
            adapter=MessageListAdapter(context,presenter.messages )
        }
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
}