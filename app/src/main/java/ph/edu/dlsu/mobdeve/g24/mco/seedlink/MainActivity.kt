package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityMainBinding
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityRegisterBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding!!.loginbtn.setOnClickListener {
            var password = binding.loginPassword.text.toString()
            var username = binding.loginUsername.text.toString()
            var links = ArrayList<String>()
            populateList(links)

            //TODO: ADD POSTS
                var temp: UserClass
                temp = UserClass(username, password, links)
                var bundle = Bundle()
                bundle.putString("user_bundle", temp.username)
                bundle.putString("pass_bundle", temp.pass)
                bundle.putStringArrayList("links_bundle", temp.links)
                var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)

                gotoProfileActivity.putExtras(bundle)

                startActivity(gotoProfileActivity)


        }


        binding!!.signupbtn.setOnClickListener {

            //Go to profile activity
            val gotoProfileActivity = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(gotoProfileActivity)

        }
    }
    private fun populateList(temp: ArrayList<String>) {

        temp.add("www.website/sampleLink1");
        temp.add("www.website/sampleLink2");
        temp.add("www.website/sampleLink3");
        temp.add("www.website/sampleLink4");
        temp.add("www.website/sampleLink5");
        temp.add("www.website/sampleLink6");

    }

}