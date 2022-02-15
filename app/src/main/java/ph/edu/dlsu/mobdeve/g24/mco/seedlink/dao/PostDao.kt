package ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao

import ph.edu.dlsu.mobdeve.g24.mco.seedlink.PostClass

interface PostDao {
    fun addPost(id: Int, author: String, photo: ByteArray) :Long
    fun getPosts(): ArrayList<PostClass?>?
    fun getProfilePosts(username: String): ArrayList<PostClass?>?
}