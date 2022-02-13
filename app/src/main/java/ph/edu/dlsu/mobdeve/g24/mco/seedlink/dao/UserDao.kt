package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import ph.edu.dlsu.mobdeve.g24.mco.seedlink.UserClass
import java.util.ArrayList

interface UserDao {
    fun addUser(user: UserClass?): Long
    fun getUsers(): ArrayList<UserClass?>?
    fun getUser(id:Int): UserClass?
    fun getUser(username:String): UserClass?
    fun updateUser(user: UserClass?): Int
}