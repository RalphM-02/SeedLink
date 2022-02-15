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

    override fun getPosts(): ArrayList<PostClass?>? {
        TODO("Not yet implemented")
    }

    override fun getProfilePosts(username: String): ArrayList<PostClass?>? {
        TODO("Not yet implemented")
    }

}