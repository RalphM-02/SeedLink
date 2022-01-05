package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityEditBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var adapter: ProfileAdapter
    private lateinit var binding: ActivityEditBinding
    private val LinkList: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateList()

        setRecyclerView()



        binding.savebtn.setOnClickListener(){
            //Save to user
            //Go to profile activity
            val gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)
            startActivity(gotoProfileActivity)
        }
        //TODO: Upload Pic
//        binding.editPic.setOnClickListener(){
//            val gotoProfilePicActivity = Intent(applicationContext, ProfilePicActivity::class.java)
//            startActivity(gotoProfilePicActivity)
//
//        }

    }

    private fun populateList() {
        this.LinkList.add("www.website/sampleLink1");
        this.LinkList.add("www.website/sampleLink2");
        this.LinkList.add("www.website/sampleLink3");
        this.LinkList.add("www.website/sampleLink4");
        this.LinkList.add("www.website/sampleLink5");
        this.LinkList.add("www.website/sampleLink6");




    }

    private fun setRecyclerView() {
        adapter = ProfileAdapter(this,LinkList)
        binding.editLinkList.adapter = adapter
        binding.editLinkList.layoutManager = LinearLayoutManager(this)
    }


    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}