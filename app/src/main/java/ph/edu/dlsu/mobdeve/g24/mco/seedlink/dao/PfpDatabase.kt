package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PfpDatabase (context: Context?) :
    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION)
    {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_PFP_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLEPFP)
            onCreate(db)
        }

        companion object {
        private const val DATABASENAME = "pfpdb.db"
        private const val DATABASEVERSION = 1


        //column names
        const val TABLEPFP = "pfp_table"
        const val PFP = "pfp"
        const val USERID = "user_id"
        private const val CREATE_PFP_TABLE = ("create table " + TABLEPFP + " ( "
                + USERID + " integer primary key autoincrement, "
                + PFP + " BLOB); ")

    }

}

