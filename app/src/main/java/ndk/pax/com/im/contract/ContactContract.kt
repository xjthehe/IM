package ndk.pax.com.im.contract

/**
 * User：Rowen
 * Description:联系人层MVP协议
 * 时间: 2020/3/15:14:23
 *
 */

interface ContactContract{

    interface Presenter:BasePresenter{
        fun loadContracts()
    }

    interface View{
        fun onLoadContractSuccess()
        fun onLoadContractFailed()
    }
}