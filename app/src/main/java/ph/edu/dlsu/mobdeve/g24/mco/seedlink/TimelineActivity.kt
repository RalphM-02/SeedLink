package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityTimelineBinding
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog

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

    fun sharePost(post: PostClass){
        val image = BitmapFactory.decodeByteArray(post.image, 0, post.image.size)
        val photo = SharePhoto.Builder().setBitmap(image).build()
        val content = SharePhotoContent.Builder().addPhoto(photo).build()
        ShareDialog.show(this@TimelineActivity, content)
    }
}