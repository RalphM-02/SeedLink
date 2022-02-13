package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.UserClass
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDatabase.Companion.TABLEUSERS
import java.util.ArrayList

class UserDaoDatabase: UserDao {


    private var userdatabase: UserDatabase? = null
    private var database: SQLiteDatabase? = null

    constructor(context: Context?) {
        userdatabase = UserDatabase(context)
    }

    override fun addUser(user: UserClass?): Long {
        val values = ContentValues()

        values.put(UserDatabase.USERSNAME, user!!.username)
        values.put(UserDatabase.USERSPASS, user!!.pass)
        database = userdatabase!!.writableDatabase

        val result: Long = database!!.insert(
            TABLEUSERS,
            null,
            values
        )

        if (database != null) {
            userdatabase!!.close()
        }

        return result


    }

    override fun getUsers(): ArrayList<UserClass?>? {
        val result = ArrayList<UserClass?>()
        val columns = arrayOf<String>(
            UserDatabase.USERSID,
            UserDatabase.USERSNAME,
            UserDatabase.USERSPASS
        )

        database = userdatabase!!.readableDatabase

        val cursor = database!!.query(
            UserDatabase.TABLEUSERS,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        cursor!!.moveToFirst()
        while (!cursor!!.isAfterLast) {
            val temp = UserClass()
            temp.id = cursor!!.getInt(0)
            temp.username = cursor!!.getString(1)
            temp.pass = cursor!!.getString(2)
            result.add(temp)
            cursor!!.moveToNext()
        }

        if (cursor != null) {
            cursor.close()
        }

        if (database != null) {
            userdatabase!!.close()
        }
        return result
    }

    override fun getUser(id :Int): UserClass? {
        var temp: UserClass?
        temp = UserClass()
        val columns = arrayOf<String>(
            UserDatabase.USERSID,
            UserDatabase.USERSNAME,
            UserDatabase.USERSPASS
        )


        database = userdatabase!!.readableDatabase


        val args = arrayOf(id.toString())
        val cursor = database!!.query(
            TABLEUSERS,
            columns,
            "id = ?",
            args,
            null,
            null,
            null
        )


        cursor!!.moveToFirst()
        while (!cursor!!.isAfterLast) {
            temp = UserClass()
            temp.id = cursor!!.getInt(0)
            temp.username = cursor!!.getString(1)
            temp.pass = cursor!!.getString(2)
            cursor!!.moveToNext()
        }

        if (cursor != null) {
            cursor.close()
        }

        if (database != null) {
            userdatabase!!.close()
        }
        return temp
    }
    override fun getUser(username :String): UserClass? {
        var temp: UserClass?
        temp = UserClass()
        val columns = arrayOf<String>(
            UserDatabase.USERSID,
            UserDatabase.USERSNAME,
            UserDatabase.USERSPASS
        )


        database = userdatabase!!.readableDatabase


        val args = arrayOf(username)
        val cursor = database!!.query(
            TABLEUSERS,
            columns,
            "username = ?",
            args,
            null,
            null,
            null
        )


        cursor!!.moveToFirst()
        while (!cursor!!.isAfterLast) {
            temp = UserClass()
            temp.id = cursor!!.getInt(0)
            temp.username = cursor!!.getString(1)
            temp.pass = cursor!!.getString(2)
            cursor!!.moveToNext()
        }

        if (cursor != null) {
            cursor.close()
        }

        if (database != null) {
            userdatabase!!.close()
        }
        return temp
    }

    override fun updateUser(user: UserClass?): Int {

        database = userdatabase!!.readableDatabase

        val values = ContentValues()

        values.put(UserDatabase.USERSNAME, user!!.username)
        values.put(UserDatabase.USERSPASS, user!!.pass)

        val result: Int = database!!.update(
            TABLEUSERS,
            values,
            "id = ?" ,
            arrayOf(user.id.toString()))


        if (database != null) {
            userdatabase!!.close()
        }

        return result;
    }
}