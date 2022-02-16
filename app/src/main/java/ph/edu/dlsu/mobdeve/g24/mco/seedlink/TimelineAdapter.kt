package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.TimelineItemBinding

class TimelineAdapter (private val context: Context, private var posts: ArrayList<PostClass?>): RecyclerView.Adapter<TimelineAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineAdapter.ViewHolder {
        val postBinding = TimelineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(postBinding)


    }

    override fun onBindViewHolder(holder: TimelineAdapter.ViewHolder, position: Int) {
        holder.bindPost(posts[position]!!)
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(private val itemBinding: TimelineItemBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bindPost(post: PostClass){
            itemBinding.btnPostAuthor.text = post.author
            itemBinding.ivImagePost.setImageBitmap(BitmapFactory.decodeByteArray(post.image, 0, post.image.size))
            itemBinding.tvPostCaption.text = post.caption


            itemBinding.btnPostAuthor.setOnClickListener{
                val userDao = UserDaoDatabase(itemBinding.root.context)
                var profileUser: UserClass? = userDao.getUser(itemBinding.btnPostAuthor.text.toString())

                (itemBinding.root.context as TimelineActivity).viewProfile(profileUser!!.id)
            }
        }

//        init {
//            itemBinding.root.setOnClickListener {
//                val userDao = UserDaoDatabase(itemBinding.root.context)
//                var profileUser: UserClass? = userDao.getUser(itemBinding.btnPostAuthor.text.toString())
//                var bundle = Bundle()
//                bundle.putInt("id_bundle", profileUser!!.id)
//
//
//                val gotoProfileActivity = Intent(itemBinding.root.context, ProfileActivity::class.java)
//                gotoProfileActivity.putExtras(bundle)
//                context.startActivity(gotoProfileActivity)
//            }
//        }
    }
}