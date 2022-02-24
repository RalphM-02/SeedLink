package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class LinkDatabase (context: Context?) :
    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_LINK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLELINKS)
        onCreate(db)
    }

    companion object {
        private const val DATABASENAME = "linksdb.db"
        private const val DATABASEVERSION = 1

        //column names
        const val TABLELINKS = "links"
        const val LINK = "link"
        const val USERID = "user_id"
        private const val CREATE_LINK_TABLE = ("create table " + TABLELINKS + " ( "
                + USERID+ " integer, "
                + LINK + " text ); ")

    }
}