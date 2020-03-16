package ndk.pax.com.im.data.db

/**
 * User：Rowen
 * Description:联系人实体类
 * 时间: 2020/3/16:8:31
 *
 */

data class Contact(val map:MutableMap<String,Any?>){

    val _id by map
    val name by map

}