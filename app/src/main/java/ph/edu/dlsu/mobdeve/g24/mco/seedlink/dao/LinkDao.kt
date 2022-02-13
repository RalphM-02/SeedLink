package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import java.util.ArrayList


interface LinkDao {
    fun addLink(id: Int, link: String ): Long
    fun getLinks(id: Int): ArrayList<String>?
    fun deleteLink(id:Int, link: String): Int
}

