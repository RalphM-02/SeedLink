package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityTimelineBinding

class TimelineActivity : AppCompatActivity() {
    var binding: ActivityTimelineBinding? = null
    var timelineAdapter: TimelineAdapter? = null
    lateinit var postDao: PostDao
    var postList: ArrayList<PostClass?> = ArrayList<PostClass?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimelineBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        postDao = PostDaoDatabase(applicationContext)
        var bundle = intent.extras
        var username = bundle!!.getString("username_bundle")
        var userid = bundle.getInt("userid_bundle")

        populateList()
        timelineAdapter = TimelineAdapter(applicationContext, postList)

        binding!!.rvPostList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        binding!!.rvPostList.adapter = timelineAdapter

        
        binding!!.btnGotoProfile.setOnClickListener {
            var b = Bundle()
            b.putInt("id_bundle", userid)
            val gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)
            gotoProfileActivity.putExtras(b)
            startActivity(gotoProfileActivity)
        }

        binding!!.btnCreatePost.setOnClickListener {
            var bCreate = Bundle()
            bCreate.putString("username_bundle", username)
            val gotoCreatePostActivity = Intent(applicationContext, CreatePostActivity::class.java)
            gotoCreatePostActivity.putExtras(bCreate)
            startActivity(gotoCreatePostActivity)
            finish()
        }

    }

    fun populateList(){
        postList = postDao.getPosts()
    }

    fun viewProfile(id: Int){
        var bundle = Bundle()
        bundle.putInt("id_bundle", id)

        val gotoProfileActivity = Intent(this, ProfileActivity::class.java)
        gotoProfileActivity.putExtras(bundle)
        startActivity(gotoProfileActivity)
    }
}