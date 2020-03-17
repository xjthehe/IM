# IM即时通讯
这个项目就是一个基本即时通讯项目
## 1.1项目介绍

## 1.2IM

## 1.3MVC MVP MVVM

## 1.4版本配置
### 1.配置Project的build.gradle
    "buildscript {
        ext.compile_sdk_version=28
        ext.build_tool_version="27.0.3"
        ext.min_sdk_version=15
        ext.target_sdk_version=28
        ext.support_version="27.0.1"
        ext.kotlin_version = '1.2.30'
        ext.anko_version = '0.10.0'

        repositories {
            google()
            jcenter()
        }"
 ### 2.配置APP模块下的build.gradle.
            implementation "org.jetbrains.anko:anko-commons:$anko_version"//Anko通用库
            implementation "org.jetbrains.anko:anko-sqlite:$anko_version"//Anko sqlite库
            implementation "com.android.support:appcompat-v7:$support_version"
            implementation "com.android.support:cardview-v7:$support_version"
                implementation ('com.roughike:bottom-bar:2.3.1'){
                    exclude module: 'design'
                }
## 1.5资源拷贝
![Image text](
https://github.com/xjthehe/IM/blob/master/app/src/main/res/mipmap-hdpi/zy.jpg
)

## 2.功能需求
### 1.如果没有登录，延时2s，跳转登录界面
### 2.如果跳转主界面登录，


## MVP实现
### 2.1 View层实现
    interface SplashContract{

        interface Presenter:BasePresenter{
            fun checkLoginState();//检查登录状态
        }

        interface View{
            fun onNotLoggedIn();//没有登陆UI处理
            fun onLoggedIn();//已经登录UI处理
        }
    }
### 2.2 Presenter层实现
    class SplashPresenter(val view:SplashContract.View):SplashContract.Presenter{
        override fun checkLoginState() {
            if(isLoggedIn())view.onLoggedIn()else view.onNotLoggedIn();
        }

        private fun isLoggedIn(): Boolean {
            return false;
        }
    }
## 3.登录界面实现
### 3.1环信集成
[1.注册并创建应用](http://docs.easemob.com/cs/start)

[2.下载SDK](https://www.easemob.com/download/im)

[3.Android SDK导入](http://docs-im.easemob.com/im/android/sdk/import)

[4.SDK初始化](http://docs-im.easemob.com/im/android/sdk/basic)
### 3.2登录界面MVP协议
    interface LoginContract {

        interface Presenter : BasePresenter {
            fun login(userName: String, password: String);
        }

        interface View {
            fun onUserNameError();
            fun onPasswordError();
            fun onStartLogin();
            fun onLoginSuccees()
            fun onLoginFaile()
        }
    }
### 3.3登录界面View层实现
    interface View {
            fun onUserNameError();
            fun onPasswordError();
            fun onStartLogin();
            fun onLoginSuccees()
            fun onLoginFaile()
        }

### 3.4登录界面Presenter层实现

#### 用户输入校验
    1.用户名长度必须是3-20位，首字母必须是英文字母，其他字符除英文外还可以是数字或者下划线
    2.密码必须是3-20位数字。
    fun String.isVaildName():Boolean=this.matches(kotlin.text.Regex("^[a-zA-Z]\\w{2,19}$"));
    fun String.isValidPassword(): Boolean = this.matches(Regex("^[0-9]{3,20}$"));

#### p层实现

    class LoginPresenter(val view:LoginContract.View):LoginContract.Presenter{
        override fun login(userName: String, password: String) {
                  if(userName.isVaildName()){
                      //用户名合法
                      if(password.isValidPassword()){
                          //密码合法,开始登陆
                          view.onStartLogin();
                          //登录到环信
                          loginEaseMob(userName,password);
                      }else view.onPasswordError();
                  }else view.onUserNameError();
        }

### 3.5登录界面Model层实现
    登录只需要登录到环信服务器
            登录成功后需要调用EMClient.getInstance().chatManager().loadAllConversations();和EMClient.getInstance().groupManager().loadAllGroups();。
            以上两个方法是为了保证进入主页面后本地会话和群组都 load 完毕
         EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Log.d("main", "登录聊天服务器成功！");
                        //在子线程通知view
                            uiThread {
                                view.onLoginSuccees();
                            }

                    }
    隐藏软键盘
     val inputMethodManager by lazy {
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }

    fun hideSoftKeyboard(){
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken,0)
        }

    动态权限申请

 if(hasWriteExternalStoragePermission()){
            val userNameString=userName.text.trim().toString();
            val passwordString=password.text.trim().toString();
            presenter.login(userNameString,passwordString);
        }else{
            applyWriteExternalStoragePermission();
        }

         //请求权限
            private fun applyWriteExternalStoragePermission() {
                val permissions= arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(this,permissions,0);
                //多次拒绝后，勾选不提示框，下面直接走else
            }

            //requestPermissions 回调函数
            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    login();
                }else toast(R.string.permission_denied);
            }

            //检查磁盘是否有权限
            private fun hasWriteExternalStoragePermission(): Boolean {
                val result=ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                return result==PackageManager.PERMISSION_GRANTED;
            }





## 4.注册界面功能需求
    1.如果注册成功，跳转登录界面
    2.如果注册失败，提示错误信息
    3.注册先注册到Bmob，然后到环信后台。
    ### 1.注册界面MVP协议
    ### 2.注册界面P层协议
    ### 3.注册界面V层协议
    ### 4.注册界面Model层协议

### Bmob集成
[Bmob开发文档](http://doc.bmob.cn/data/android/index.html)

    1.注册并创建应用
    2.下载SDK

        1.在 Project 的 build.gradle 文件中添加 Bmob的maven仓库地址：
        allprojects {
            repositories {
                google()
                jcenter()
                maven { url "https://raw.github.com/bmob/bmob-android-sdk/master" }
            }
        }

        2.在app的build.gradle文件中添加依赖文件：
          ndk {
                 // 设置支持的 SO 库构架，注意这里要根据你的实际情况来设置
                    abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a', 'x86'
              }
             useLibrary 'org.apache.http.legacy'


            implementation 'cn.bmob.android:bmob-sdk:3.6.6'

       3.初始化Bmob的SDK
        //Bmob
        Bmob.initialize(applicationContext,"2ee4708569a21a8922b2020ab5a365b7");

### [注册用户到Bmob](http://doc.bmob.cn/data/android/index.html#_4)
         private fun registerBmob(userName: String, password: String) {
                var bu=BmobUser();
                bu.username=userName;
                bu.setPassword(password);
                bu.email="sendi@1632.com";
                bu.signUp<BmobUser>(object : SaveListener<BmobUser>() {
                    override fun done(p0: BmobUser?, e: BmobException?) {
                            if(e==null){
                                //注册成功
                                    Log.e("signUp","Bmob 注册成功")
                                //注册到环信
                                registerEaseMob(userName,password);
                            }else{
                                //注册失败
                                Log.e("signUp","Bmob 注册失败"+e.errorCode)

                                view.onRegisterFaile();
                            }

                    }
                })
            }

### [注册用户到环信](http://docs-im.easemob.com/im/android/sdk/basic#%E6%B3%A8%E5%86%8C)

          private fun registerEaseMob(userName: String, password: String) {
                Log.e("registerEaseMob","环信注册")

                doAsync {
                    try {
                        EMClient.getInstance().createAccount(userName, password);//同步
                        uiThread { view.onRegisterSuccees() }
                    }catch (e: HyphenateException){
                        uiThread { view.onRegisterFaile() }
                    }
                    //注册失败会抛出HyphenateException
                }
            }

### 用户注册已重复处理
    if(e.errorCode==202) view.onUserExit()

## 5.主界面
 ### 5.1主界面布局以及包重构
    ui
     --activity
     --fragment
 ### 5.2 fragment切换
    bottomBar.setOnTabSelectListener { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction();
            FragmentFactory.instance.getInstatance(tabId)?.let { beginTransaction.replace(R.id.fragment_frame, it) };
        }

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
## 6.动态界面
### 6.1 退出登录
      fun logout(){
            EMClient.getInstance().logout(true, object : EMCallBackAdapter() {
                override fun onSuccess() {
                    context?.runOnUiThread {
                        context?.toast(R.string.logout_success)
                        context?.startActivity<LoginActivity>()
                        activity?.finish();
                    }
                }
                override fun onError(p0: Int, p1: String?) {
                    //切换主线程
                    context?.runOnUiThread {
                        toast(R.string.logout_failed)
                    }
                }
            })
        }

## 7.联系人界面

### 7.1 联系人布局初始化
class ContactFragment:BaseFragment(), ContactContract.View{

    override fun getLayoutId(): Int = R.layout.fragment_contacts;


  override fun init() {
        super.init()
        initHeader()
        initSwipRefreshLayout()
        initRecycleView()
        //联系人状态监听器
        EMClient.getInstance().contactManager().setContactListener(contactListtener)
        initSliderBar()
        //加载联系人 P层触发
        contactPresnter.loadContracts()
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
}

### 7.2 联系人条目布局
widget
    ---ContractListItemView 模块化思想，自定义控件

class ContractListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.view_contact_item,this)
    }
}

### 7.3 联系人RecyclerView的初始化

   private fun initRecycleView() {
        //apply语句
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
            contactPresnter.contactItems.clear()
            adapter = ContractListAdapter(context)
        }
    }

    //适配器创建
    class ContractListAdapter(val context: Context, val contactItems: MutableList<ContactListItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
            return ContractItemViewHolder(ContractListItemView(context))
        }

        override fun getItemCount(): Int =30

        //暂时无数据 不绑定
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        }

        class ContractItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        }

    }

## 8.联系人界面

### 8.1 联系人界面MVP协议
    interface ContactContract{
        interface Presenter:BasePresenter{
            fun loadContracts()
        }

        interface View{
            fun onLoadContractSuccess()
            fun onLoadContractFailed()
        }
    }

### 8.2 联系人界面View层协议
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

### 8.3 联系人界面Presenter层和Model层的实现
class ContactPresenter(val view:ContactContract.View):ContactContract.Presenter{

    val contactItems= mutableListOf<ContactListItem>()

    //加载联系人
    override fun loadContracts() {
        doAsync {
            //再次添加时候，先删除集合
            contactItems.clear()
//            //清空数据库
//            IMDatabase.instance.deleteAllContact()

                try {
                    //获取当前用户名好友列表，注意，默认新用户没有好友，可以去环信后台自行添加好友
                    val username= EMClient.getInstance().contactManager().allContactsFromServer
                    Log.e("username","username"+username.size)

                    //username根据首字母排序
                    username.sortBy { it[0] }

                    username.forEachIndexed { index, s ->
                        //判断是否显示首字母,当前首字母和前一个数据首字母不相同,则需要显示首字母
                        val showFirstLetter=index==0||s[0]!=username[index-1][0]
                        val contactItem=ContactListItem(s,s[0].toUpperCase(),showFirstLetter)
                        contactItems.add(contactItem)
                        //添加到数据库
//                        val contact=Contact(mutableMapOf("name" to s))
//                        IMDatabase.instance.saveContact(contact)

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


### 8.4 联系人列表的刷新

data
    ------
        data class ContactListItem(val username:String,val firstLetter:Char,val showFirstLetter:Boolean)


              //获取当前用户名好友列表，注意，默认新用户没有好友，可以去环信后台自行添加好友
                    val username= EMClient.getInstance().contactManager().allContactsFromServer
                    Log.e("username","username"+username.size)

                    //username根据首字母排序
                    username.sortBy { it[0] }

                    username.forEachIndexed { index, s ->
                        //判断是否显示首字母,当前首字母和前一个数据首字母不相同,则需要显示首字母
                        val showFirstLetter=index==0||s[0]!=username[index-1][0]
                        val contactItem=ContactListItem(s,s[0].toUpperCase(),showFirstLetter)
                        contactItems.add(contactItem)



           override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
               val contractListItemView = holder?.itemView as ContractListItemView
               contractListItemView.bindView(contactItems[position])
           }

    //ContractListItemView
    class ContractListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {

        init {
            View.inflate(context, R.layout.view_contact_item,this)
        }

        fun bindView(contactListItem: ContactListItem) {
            //是否显示首字母
                if(contactListItem.showFirstLetter){
                    firstLetter.visibility=View.VISIBLE
                    firstLetter.text= contactListItem.firstLetter.toString()
                }else firstLetter.visibility=View.GONE

                userName.text=contactListItem.username.toString()
        }

    }
## 9联系人逻辑跳转

### 9.1 点击联系人跳转聊天界面

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val contractListItemView = holder?.itemView as ContractListItemView
        contractListItemView.bindView(contactItems[position])
        //获取当前item的用户名
        val username=contactItems.get(position).username
        //添加绑定事件【点击事件】
        contractListItemView.setOnClickListener {
            context.startActivity<ChatActivity>("username" to username)
        }
    }


### 9.2 长按联系人，删除

        contractListItemView.setOnLongClickListener {
            val message= String.format(context.getString(R.string.delete_friend_message),username)
            AlertDialog.Builder(context)
                    .setTitle(R.string.delete_friend_title)
                    .setMessage(message)
                    .setNegativeButton(R.string.cancel,null)
                    .setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialog, which ->
                        deleteFriend(username)
                    }).show()
                true
        }

### 9.3 删除好友

    //删除长按的好友
    private fun deleteFriend(username: String) {
            EMClient.getInstance().contactManager().aysncDeleteContact(username,object :EMCallBackAdapter(){
                override fun onSuccess() {
                    context.runOnUiThread {
                        toast(R.string.delete_friend_success)
                        //刷新列表

                    }
                }

                override fun onError(p0: Int, p1: String?) {
                    context.runOnUiThread {
                        toast(R.string.delete_friend_failed)
                    }
                }
            })

        }

### 9.3 删除好友后刷新联系人列表
  //初始化
    override fun init() {
        super.init()
        。。。。
        //联系人状态监听器，当联系人被删除，会监听回调
        EMClient.getInstance().contactManager().setContactListener(contactListtener)
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
### 9.4 SlideBar自定义控件
widget
    ----SlideBar

    class SlideBar(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

        var sectionHeight=0f
        val paint=Paint()
        var textBaseline=0f
        var onSectionChangeListener:OnSectionChangeListener?=null
        companion object {
            private val SECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                    "K", "L","M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        }

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            Log.e("onMeasure","onMeasure")
        }

        //获取每个字母所占领域高度
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            sectionHeight=h*1.0f/ SECTIONS.size;

            val fontMetrics = paint.fontMetrics
            //计算文本高度
            val textHeight=fontMetrics.descent-fontMetrics.ascent
            //计算基准线************************************************
            textBaseline =sectionHeight/2+(textHeight/2-fontMetrics.descent)
        }

        init {
            paint.apply {
                color=resources.getColor(R.color.material_grey_100)
                textSize= sp(12).toFloat()
                textAlign=Paint.Align.CENTER
            }
        }

        override fun onDraw(canvas: Canvas?) {
            //绘制所有字母
            val x=width*1.0f/2;//每个字母宽度
            var baseline=textBaseline;

            SECTIONS.forEach {
                canvas?.drawText(it,x,baseline,paint)
                baseline+=sectionHeight
            }
        }

    //    View的三大核心方法onMeasure、onLayout、onDraw
    //    onMeasure：用于测量视图的大小；
    //    onLayout：用于给视图进行布局；
    //    onDraw：用于对视图进行绘制；

        override fun onTouchEvent(event: MotionEvent): Boolean {
            when(event.action){
                MotionEvent.ACTION_DOWN->{

                    setBackgroundColor(R.drawable.bg_slide_bar)
                    //点击时候 找到点击字母
                    val index=getTouchIndex(event)
                    val firstletter= SECTIONS[index]
                    println(firstletter)
                    onSectionChangeListener?.onSectionChange(firstletter)
                }

                MotionEvent.ACTION_MOVE->{
                    //点击时候 找到点击字母
                    val index=getTouchIndex(event)
                    val firstletter= SECTIONS[index]
                    println(firstletter)
                    onSectionChangeListener?.onSectionChange(firstletter)
                }

                MotionEvent.ACTION_UP->{
                    setBackgroundColor(Color.TRANSPARENT)
                    onSectionChangeListener?.onSlideFinish()
                }
            }
            return true
        }
            //获取点击位置的字母的下标
        private fun getTouchIndex(event: MotionEvent): Int {
                var x=event.x
                Log.e("x", x.toString())
                var index=(event.y/sectionHeight).toInt()
                //越界检查
                if(index<0){
                    index=0
                }else if(index>= SECTIONS.size){
                    index= SECTIONS.size-1
                }
                return index
        }


        interface OnSectionChangeListener{
            fun onSectionChange(firstletter:String)//按下或者滑动
            fun onSlideFinish()//滑动结束
        }
    }

    测量 布局 绘制
 ## 10

 ### 10.1 绘制文本居中
        【【【【fontMetrics   baseline】】】
        //获取每个字母所占领域高度
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            sectionHeight=h*1.0f/ SECTIONS.size;

            val fontMetrics = paint.fontMetrics
            //计算文本高度
            val textHeight=fontMetrics.descent-fontMetrics.ascent
            //计算基准线************************************************
            textBaseline =sectionHeight/2+(textHeight/2-fontMetrics.descent)
        }

         override fun onDraw(canvas: Canvas?) {
                    //绘制所有字母
                    val x=width*1.0f/2;//每个字母宽度
                    var baseline=textBaseline;

                    SECTIONS.forEach {
                        canvas?.drawText(it,x,baseline,paint)
                        baseline+=sectionHeight
                    }
                }
 ### 10.2 背景色处理
     override fun onTouchEvent(event: MotionEvent): Boolean {
         when(event.action){
             MotionEvent.ACTION_DOWN->{

                 setBackgroundColor(R.drawable.bg_slide_bar)
//                 //点击时候 找到点击字母
//                 val index=getTouchIndex(event)
//                 val firstletter= SECTIONS[index]
//                 println(firstletter)
//                 onSectionChangeListener?.onSectionChange(firstletter)
             }

//             MotionEvent.ACTION_MOVE->{
//                 //点击时候 找到点击字母
//                 val index=getTouchIndex(event)
//                 val firstletter= SECTIONS[index]
//                 println(firstletter)
//                 onSectionChangeListener?.onSectionChange(firstletter)
//             }

             MotionEvent.ACTION_UP->{
                 setBackgroundColor(Color.TRANSPARENT)
//                 onSectionChangeListener?.onSlideFinish()
             }
         }
         return true
     }
 ### 10.3 点击sliderBar 获取字母
 override fun onTouchEvent(event: MotionEvent): Boolean {
          when(event.action){
              MotionEvent.ACTION_DOWN->{

                  setBackgroundColor(R.drawable.bg_slide_bar)
                  //点击时候 找到点击字母
                  val index=getTouchIndex(event)
                  val firstletter= SECTIONS[index]
                  println(firstletter)
                 // onSectionChangeListener?.onSectionChange(firstletter)
              }

              MotionEvent.ACTION_MOVE->{
                  //点击时候 找到点击字母
                  val index=getTouchIndex(event)
                  val firstletter= SECTIONS[index]
                  println(firstletter)
 //                 onSectionChangeListener?.onSectionChange(firstletter)
              }

              MotionEvent.ACTION_UP->{
                  setBackgroundColor(Color.TRANSPARENT)
 //                 onSectionChangeListener?.onSlideFinish()
              }
          }
          return true
      }

     //获取点击位置的字母的下标
    private fun getTouchIndex(event: MotionEvent): Int {
            var x=event.x
            Log.e("x", x.toString())
            var index=(event.y/sectionHeight).toInt()
            //越界检查
            if(index<0){
                index=0
            }else if(index>= SECTIONS.size){
                index= SECTIONS.size-1
            }
            return index
    }
 ### 10.4 点击sliderBar 接口回调
    interface OnSectionChangeListener{
        fun onSectionChange(firstletter:String)//按下或者滑动
        fun onSlideFinish()//滑动结束
    }’


     override fun onTouchEvent(event: MotionEvent): Boolean {
              when(event.action){
                  MotionEvent.ACTION_DOWN->{

                      setBackgroundColor(R.drawable.bg_slide_bar)
                      //点击时候 找到点击字母
                      val index=getTouchIndex(event)
                      val firstletter= SECTIONS[index]
                      println(firstletter)
                      onSectionChangeListener?.onSectionChange(firstletter)
                  }

                  MotionEvent.ACTION_MOVE->{
                      //点击时候 找到点击字母
                      val index=getTouchIndex(event)
                      val firstletter= SECTIONS[index]
                      println(firstletter)
                      onSectionChangeListener?.onSectionChange(firstletter)
                  }

                  MotionEvent.ACTION_UP->{
                      setBackgroundColor(Color.TRANSPARENT)
                      onSectionChangeListener?.onSlideFinish()
                  }
              }
              return true
          }

         //获取点击位置的字母的下标
        private fun getTouchIndex(event: MotionEvent): Int {
                var x=event.x
                Log.e("x", x.toString())
                var index=(event.y/sectionHeight).toInt()
                //越界检查
                if(index<0){
                    index=0
                }else if(index>= SECTIONS.size){
                    index= SECTIONS.size-1
                }
                return index
        }


          //sliderbar监听回调 字母显示 隐藏
            private fun initSliderBar() {
                slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
                    override fun onSectionChange(firstletter: String) {
                        section.visibility = View.VISIBLE
                        section.text = firstletter
                        //recyclerView滑动到字母对应的下标
                        Log.e("letter", firstletter)
//                        recyclerView.smoothScrollToPosition(getPosition(firstletter))
                    }

                    override fun onSlideFinish() {
                        section.visibility = View.GONE
                    }
                }
            }



### 10.5 RecyclerView跟随滚动
   //sliderbar监听回调 字母显示 隐藏
            private fun initSliderBar() {
                slideBar.onSectionChangeListener = object : SlideBar.OnSectionChangeListener {
                    override fun onSectionChange(firstletter: String) {
                        section.visibility = View.VISIBLE
                        section.text = firstletter
                        //recyclerView滑动到字母对应的下标
                        Log.e("letter", firstletter)
                        //二分查找获取当前字母在集合所在下标
                        recyclerView.smoothScrollToPosition(getPosition(firstletter))
                    }

                    override fun onSlideFinish() {
                        section.visibility = View.GONE
                    }
                }
            }

    private fun getPosition(firstletter: String): Int =
                contactPresnter.contactItems.binarySearch {
                    contactListItem ->contactListItem.firstLetter.minus(firstletter[0])
                }


## 11 添加好友界面

### 11.1 添加好友界面实现

 //添加好友右上角*****************
        add.setOnClickListener {
            context?.startActivity<AddFriendActivity>()
        }

        class AddFriendActivity :BaseActivity(){
            override fun getLayoutResId(): Int= R.layout.activity_add_friend


            override fun init(){
                    super.init()
               headerTitle.text=getString(R.string.add_friend)
             }
}

### 11.2 添加好友界面RecyclerView布局实现
widget
    ----AddFriendListItemView
class AddFriendListItemView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs){
    init {
        View.inflate(context, R.layout.view_add_friend_item,this)
    }

    fun bindView(addFriendListItem: AddFriendListItem) {

    }

  }
//
     recyclerView.apply {
                setHasFixedSize(true)
                layoutManager=LinearLayoutManager(context)
                adapter=AddFriendListAdapter(context)
            }

//AddFriendListAdapter 创建
class AddFriendListAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return AddFriendItemViewHolder(AddFriendListItemView(context))
    }

    override fun getItemCount(): Int =30

    //暂时无数据 不绑定
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {


    }

    class AddFriendItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}

### 11.3 添加好友界面MVP协议
    interface AddFriendContract{
        interface Presenter:BasePresenter{
                fun searchFriend(key:String)
        }

        interface View{
            fun searchFriendSuccess()
            fun searchFriendFail()
        }
    }

 ### 11.4 添加好友界面View层实现
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


 ### 11.5 添加好友界面Presenter层实现
    查询好友只能使用Bmob查询
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
                     //创建AddFriendItem集合 数据库人员和后台返回比对
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
                                   //按钮显示已经添加
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




