package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import java.util.ArrayList

interface PfpDao {

    fun addPfp(id: Int, pfp: ByteArray): Long
    fun getPfp(id: Int): ByteArray
    fun updatePfp(id:Int, pfp: ByteArray ): Int
    fun deletePfp(id: Int):Int
}


