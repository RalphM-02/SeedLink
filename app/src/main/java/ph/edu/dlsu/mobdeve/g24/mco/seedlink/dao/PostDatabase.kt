package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PostDatabase(context: Context?):
    SQLiteOpenHelper(context, DATABASENAME, null, DATABSEVERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_POST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLEPOSTS)
        onCreate(db)
    }

    companion object{
        private const val DATABASENAME = "postDatabase.db"
        private val DATABSEVERSION = 1

        //cols
        const val TABLEPOSTS = "posts"
        const val AUTHOR = "author"
        const val IMAGE = "image"
        const val CAPTION = "caption"

        private const val CREATE_POST_TABLE = (
                "create table " + TABLEPOSTS + " ( " +
                AUTHOR + " text, " +
                IMAGE + " blob, " +
                CAPTION + " text); ")
    }

}