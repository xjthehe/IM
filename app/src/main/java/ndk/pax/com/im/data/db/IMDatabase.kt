package ndk.pax.com.im.data.db

import ndk.pax.com.im.extentions.toVarargArray
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:8:33
 *
 */

class IMDatabase{

    companion object {
        val databaseHelper=DatabaseHelper()
        val instance=IMDatabase()
    }

    //保存联系人
    fun saveContact(contact:Contact){
        databaseHelper.use {
//            insert(ContactTable.NAME,)
            insert(ContactTable.NAME,*contact.map.toVarargArray())
        }
    }

    fun getAllContacts():List<Contact> = databaseHelper.use {
            select(ContactTable.NAME).parseList(object :MapRowParser<Contact>{
                override fun parseRow(columns: Map<String, Any?>): Contact=Contact(columns.toMutableMap())
            })
        }

    fun deleteAllContact(){
        databaseHelper.use {
            delete(ContactTable.NAME,null,null);
        }


    }


}