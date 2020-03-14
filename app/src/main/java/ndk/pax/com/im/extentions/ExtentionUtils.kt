package ndk.pax.com.im.extentions


/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/14:13:48
 *
 */

fun String.isVaildName():Boolean=this.matches(kotlin.text.Regex("^[a-zA-Z]\\w{2,19}$"));
fun String.isValidPassword(): Boolean = this.matches(Regex("^[0-9]{3,20}$"));
