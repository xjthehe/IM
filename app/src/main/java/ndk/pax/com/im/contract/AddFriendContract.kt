package ndk.pax.com.im.contract

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/15:21:28
 *
 */


interface AddFriendContract{
    interface Presenter:BasePresenter{
            fun searchFriend(key:String)
    }

    interface View{
        fun searchFriendSuccess()
        fun searchFriendFail()
    }

}