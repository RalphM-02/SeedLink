package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityProfileBinding


class ProfileActivity: AppCompatActivity() {

    private lateinit var adapter: ProfileAdapter
    private lateinit var binding: ActivityProfileBinding
    private var linkList: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)




        var bundle = intent.extras
        var username = bundle!!.getString("user_bundle")
        var password = bundle!!.getString("pass_bundle")
        binding.viewUsername.text = username
        //TODO: Hide Password
        binding.viewPassword.text = password
        linkList = bundle!!.getStringArrayList("links_bundle")!!

        setRecyclerView()
        binding.editbtn.setOnClickListener{
            //Go to profile activity
            val gotoEditProfileActivity = Intent(applicationContext, EditProfileActivity::class.java)
            startActivity(gotoEditProfileActivity)
        }

    }

//    private fun populateList() {
//
//        //TODO: Change to user array
//        this.LinkList.add("www.website/sampleLink1");
//        this.LinkList.add("www.website/sampleLink2");
//        this.LinkList.add("www.website/sampleLink3");
//        this.LinkList.add("www.website/sampleLink4");
//        this.LinkList.add("www.website/sampleLink5");
//        this.LinkList.add("www.website/sampleLink6");
//
//    }

    private fun setRecyclerView() {
        adapter = ProfileAdapter(this,linkList)
        binding.viewLinkList.adapter = adapter
        binding.viewLinkList.layoutManager = LinearLayoutManager(this)
    }



}