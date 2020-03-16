package ndk.pax.com.im.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ndk.pax.com.im.app.IMApplication
import org.jetbrains.anko.db.*

/**
 * User：Rowen
 * Description:
 * 时间: 2020/3/16:8:20
 *
 */

class DatabaseHelper(ctx: Context=IMApplication.instance) :
        ManagedSQLiteOpenHelper(ctx, NAME, null,VERSION) {

    companion object {
        val NAME="im.db"
        val VERSION=1
    }

    //数据库创建
    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(ContactTable.NAME,true,
                ContactTable.ID to INTEGER+ PRIMARY_KEY+ AUTOINCREMENT,
                ContactTable.CONTACT to TEXT)
    }
    //升级
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.dropTable(ContactTable.NAME,true)
            onCreate(db)
    }

}