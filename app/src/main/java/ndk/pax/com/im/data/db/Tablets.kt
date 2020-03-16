package ndk.pax.com.im.data.db

/**
 * User：Rowen
 * Description:联系人数据库表
 * 时间: 2020/3/16:8:12
 *
 */
//1.定义一个ContactTable类
//2.创建ContactTable实例，通过类名直接访问实例，也是实现单例一种方式

object ContactTable{
    val NAME="contact"

    //定义字段
    val ID="_id"
    val CONTACT="name"
}