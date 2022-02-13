package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDatabase.Companion.TABLELINKS
import java.util.ArrayList

class LinkDaoDatabase: LinkDao  {

    private var linkdatabase: LinkDatabase? = null
    private var database: SQLiteDatabase? = null

    constructor(context: Context?){
        linkdatabase = LinkDatabase(context)
    }

    override fun addLink(id: Int, link: String): Long {
        val values = ContentValues()

        values.put(LinkDatabase.USERID, id)
        values.put(LinkDatabase.LINK,link)
        database = linkdatabase!!.writableDatabase

        val result: Long = database!!.insert(
            TABLELINKS,
            null,
            values
        )

        if(database != null){
            linkdatabase!!.close()
        }

        return result
    }

    override fun getLinks(id :Int): ArrayList<String>? {
        database = linkdatabase!!.writableDatabase
        val result = ArrayList<String>()
        val columns = arrayOf<String>(
            LinkDatabase.LINK
        )
        val args = arrayOf(id.toString())

        if(database==null) {
            return null
        }else {
            val cursor = database!!.query(
                TABLELINKS,
                columns,
                LinkDatabase.USERID +" = ?",
                args,
                null,
                null,
                null
            )
            cursor!!.moveToFirst()

            while (!cursor!!.isAfterLast) {
                result.add(cursor!!.getString(0))
                cursor!!.moveToNext()
            }

            if (cursor != null) {
                cursor.close()
            }

            if (database != null) {
                linkdatabase!!.close()
            }
            return result
        }
    }

    override fun deleteLink(id: Int, link: String): Int {
        database = linkdatabase!!.writableDatabase
        val args = arrayOf(id.toString(), link)

        val result: Int =  database!!.delete(TABLELINKS,LinkDatabase.USERID +" = ? AND link = ?", args )

        return result
    }

}