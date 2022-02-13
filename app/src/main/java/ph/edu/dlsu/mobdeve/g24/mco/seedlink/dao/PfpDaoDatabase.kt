package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.UserClass
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PfpDatabase.Companion.TABLEPFP

class PfpDaoDatabase: PfpDao {
    private var pfpdatabase: PfpDatabase? = null
    private var database: SQLiteDatabase? = null

    constructor(context: Context?){
        pfpdatabase = PfpDatabase(context)
    }

    override fun addPfp(id: Int, pfp: ByteArray): Long {
        database = pfpdatabase!!.writableDatabase

        val values = ContentValues()

        values.put(PfpDatabase.USERID, id)
        values.put(PfpDatabase.PFP, pfp)

        val result: Long = database!!.insert(
            TABLEPFP,
            null,
            values)

        if (database != null) {
            pfpdatabase!!.close()
        }

        return result
    }

    override fun getPfp(id: Int): ByteArray {
        var pfp: ByteArray = byteArrayOf()
        val columns = arrayOf<String>(
            PfpDatabase.USERID,
            PfpDatabase.PFP
        )

        database = pfpdatabase!!.readableDatabase


        val args = arrayOf(id.toString())
        val cursor = database!!.query(
            TABLEPFP,
            columns,
            PfpDatabase.USERID+ " = ?",
            args,
            null,
            null,
            null
        )

        cursor!!.moveToFirst()
        while (!cursor!!.isAfterLast) {
            var id= cursor!!.getInt(0)
             pfp = cursor!!.getBlob(1)
            cursor!!.moveToNext()
        }

        if (cursor != null) {
            cursor.close()
        }

        if (database != null) {
            pfpdatabase!!.close()
        }

        return pfp
    }


    override  fun updatePfp(id: Int, pfp: ByteArray): Int{

        database = pfpdatabase!!.readableDatabase

        val values = ContentValues()

        values.put(PfpDatabase.PFP, pfp)

        val result: Int = database!!.update(
            UserDatabase.TABLEUSERS,
            values,
            PfpDatabase.USERID+ " = ?" ,
            arrayOf(id.toString()))


        if (database != null) {
            pfpdatabase!!.close()
        }

        return result
    }



    override  fun deletePfp(id: Int): Int{

        database = pfpdatabase!!.readableDatabase

        val result: Int = database!!.delete(
            TABLEPFP,
            PfpDatabase.USERID+ " = ?" ,
            arrayOf(id.toString()))


        if (database != null) {
            pfpdatabase!!.close()
        }

        return result

    }

}


