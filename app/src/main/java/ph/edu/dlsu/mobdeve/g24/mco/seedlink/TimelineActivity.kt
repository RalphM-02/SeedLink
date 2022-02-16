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
        //var username = bundle!!.getString("user_username")

        populateList()
        timelineAdapter = TimelineAdapter(applicationContext, postList)

        binding!!.rvPostList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        binding!!.rvPostList.adapter = timelineAdapter

        
        binding!!.btnGotoProfile.setOnClickListener {
            // TODO: 2/16/2022

        }

        binding!!.btnCreatePost.setOnClickListener {
            // TODO: 2/16/2022
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