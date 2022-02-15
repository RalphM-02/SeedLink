package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.PostClass
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDatabase.Companion.TABLEPOSTS

class PostDaoDatabase: PostDao {
    private var postDatabase: PostDatabase? = null
    private var database: SQLiteDatabase? = null

    constructor(context: Context){
        postDatabase = PostDatabase(context)
    }

    override fun addPost(author: String, photo: ByteArray, caption: String): Long {
        database = postDatabase!!.writableDatabase
        val values = ContentValues()

        values.put(PostDatabase.AUTHOR, author)
        values.put(PostDatabase.IMAGE, photo)
        values.put(PostDatabase.CAPTION, caption)

        val result: Long = database!!.insert(TABLEPOSTS, null, values)

        if (database != null) {
            postDatabase!!.close()
        }

        return result

    }

    override fun getPosts(): ArrayList<PostClass?> {
        val result = ArrayList<PostClass?>()
        val columns = arrayOf<String>(
            PostDatabase.AUTHOR,
            PostDatabase.IMAGE,
            PostDatabase.CAPTION
        )

        database = postDatabase!!.readableDatabase

        val cursor = database!!.query(
            PostDatabase.TABLEPOSTS,
            columns,
            null,
            null,
            null,
            null,
            null,
        )

        cursor!!.moveToFirst()
        while (!cursor.isAfterLast){
            val temp = PostClass()
            temp.author = cursor.getString(0)
            temp.image = cursor.getBlob(1)
            temp.caption = cursor.getString(2)
            result.add(temp)
            cursor.moveToNext()
        }

        if(cursor != null){
            cursor.close()
        }

        if(database != null){
            postDatabase!!.close()
        }

        return result

    }

    override fun getProfilePosts(username: String): ArrayList<PostClass?>? {
        val result = ArrayList<PostClass?>()
        val columns = arrayOf<String>(
            PostDatabase.AUTHOR,
            PostDatabase.IMAGE,
            PostDatabase.CAPTION
        )

        database = postDatabase!!.readableDatabase

        val args = arrayOf(username)
        val cursor = database!!.query(
            TABLEPOSTS,
            columns,
            "author = ?",
            args,
            null,
            null,
            null
        )

        cursor!!.moveToFirst()
        while(!cursor.isAfterLast){
            val temp = PostClass()
            temp.author = cursor.getString(0)
            temp.image = cursor.getBlob(1)
            temp.caption = cursor.getString(2)
            result.add(temp)
            cursor.moveToNext()
        }

        if(cursor != null){
            cursor.close()
        }

        if(database != null){
            postDatabase!!.close()
        }

        return result
    }

}