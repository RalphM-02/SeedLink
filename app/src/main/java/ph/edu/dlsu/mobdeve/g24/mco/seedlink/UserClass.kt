package ph.edu.dlsu.mobdeve.g24.mco.seedlink
//data class UserClass(val username: String, var password: String, val links: ArrayList<String>, val posts: ArrayList<PostClass>)
data class UserClass(
    var id: Int,
    var username: String,
    var pass: String)
{
    constructor() :this(-1,"","")
    constructor(username: String, pass: String): this(-1,username,pass)



}