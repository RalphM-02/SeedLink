package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabase (context: Context?) :
    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLEUSERS)
        onCreate(db)
    }

    companion object {
        private const val DATABASENAME = "usersdb.db"
        private const val DATABASEVERSION = 1

        //column names
        const val TABLEUSERS = "users"
        const val USERSID = "id"
        const val USERSNAME = "username"
        const val USERSPASS = "userpass"
        private const val CREATE_USER_TABLE = ("create table " + TABLEUSERS + " ( "
                + USERSID + " integer primary key autoincrement, "
                + USERSNAME + " text, "
                + USERSPASS + " text ); ")

    }
}