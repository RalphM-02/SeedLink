package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.*
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityProfileBinding


class ProfileActivity: AppCompatActivity() {

    private lateinit var adapter: ProfileAdapter
    private lateinit var binding: ActivityProfileBinding
    private var linkList: MutableList<String> = mutableListOf()
    lateinit var linkDao: LinkDao
    lateinit var userDao: UserDao
    lateinit var pfpDao: PfpDao
    private lateinit var addLinkViewModel: AddLinkViewModel
    private lateinit var user: UserClass



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linkDao = LinkDaoDatabase(applicationContext)
        userDao = UserDaoDatabase(applicationContext)
        pfpDao = PfpDaoDatabase(applicationContext)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var bundle = intent.extras
        var id = bundle!!.getInt("id_bundle")

        //Stores previous info
        if( userDao.getUser(id) != null)
            user = userDao.getUser(id)!!
        else
            Toast.makeText(
                this,
                "Can't Find USer Profile.",
                Toast.LENGTH_SHORT
            )
                .show()
        
        var img = pfpDao.getPfp(id)

        if(img.size == 0 )
            binding.viewPic.setImageResource(R.drawable.ic_profile_pic)
        else{
            //bytearray to new risized bitmap factory
            val resize: Bitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
            binding.viewPic.setImageBitmap(Bitmap.createScaledBitmap(resize, 120, 120, false));

        }


        binding.viewUsername.text = user.username
        binding.viewPassword.text = user.pass

        if(linkDao.getLinks(user.id) == null){
            binding.addLinkProfile.visibility = View.VISIBLE
        }

        else
            linkList = linkDao.getLinks(user.id)!!

        setRecyclerView()

        binding.logoutbtn.setOnClickListener {
            var gotoMainActivity = Intent(applicationContext,MainActivity::class.java)
            startActivity(gotoMainActivity)
            finish()
        }

        binding.editbtn.setOnClickListener{
            var editbundle = Bundle()
            editbundle.putInt("id_bundle", user.id)

            var gotoEditProfileActivity = Intent(applicationContext,EditProfileActivity::class.java)

            gotoEditProfileActivity.putExtras(bundle)

            startActivity(gotoEditProfileActivity)
            finish()


        }


        binding.addLinkProfile.setOnClickListener{
            if (user.username != null) {

                AddLinkDialog().show(supportFragmentManager, AddLinkDialog.TAG)

                addLinkViewModel = ViewModelProvider(this).get(AddLinkViewModel::class.java)
                addLinkViewModel.link.observe(this, Observer {

                    if(it.equals(""))
                        Toast.makeText(this, "Link Addition Unsuccessful: Empty String", Toast.LENGTH_SHORT).show()
                    else {
                        val res = linkDao.addLink(user.id, it)

                        if (res > 0) {
                            Toast.makeText(
                                this,
                                "Link Addition Successful.",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            linkList= linkDao.getLinks(user.id)!!
                            adapter.notifyDataSetChanged()
                        }
                        else
                            Toast.makeText(
                                this,
                                "Link Addition Unsuccessful: LinkDB Error",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                })
            }else
                Toast.makeText(this, "Link Addition Unsuccessful: UserDB Error ", Toast.LENGTH_SHORT).show()


        }

        binding.btnCreatePost.setOnClickListener {
            var b = Bundle()
            b.putString("username_bundle", user.username)
            b.putInt("userid_bundle", user.id)

            val gotoCreatePostActivity = Intent(applicationContext, CreatePostActivity::class.java)
            gotoCreatePostActivity.putExtras(b)
            startActivity(gotoCreatePostActivity)
            finish()
        }

        binding.btnGotoHome.setOnClickListener {
            var bHome = Bundle()
            bHome.putInt("userid_bundle", user.id)
            bHome.putString("username_bundle", user.username)

            val gotoTimelineActivity = Intent(applicationContext, TimelineActivity::class.java)
            gotoTimelineActivity.putExtras(bHome)
            startActivity(gotoTimelineActivity)
        }

    }



    private fun setRecyclerView() {
        adapter = ProfileAdapter(this,linkList)
        binding.viewLinkList.adapter = adapter
        binding.viewLinkList.layoutManager = LinearLayoutManager(this)
    }



}

