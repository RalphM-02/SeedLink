package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //TODO: click on sign up confirm btn when enter is pressed
        binding.signupConfirmbtn.setOnClickListener {
            var confirmPassword = binding.registerConfirmPassword.text.toString()
            var password = binding.registerPassword.text.toString()
            var username = binding.registerUsername.text.toString()
            var links = ArrayList<String>()
            populateList(links)

            //TODO: ADD POSTS
            if (confirmPassword.equals(password)) {
                var temp: UserClass
                temp = UserClass(username, password, links)
                var bundle = Bundle()
                bundle.putString("user_bundle", temp.username)
                bundle.putString("pass_bundle", temp.pass)
                bundle.putStringArrayList("links_bundle", temp.links)
                var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)

                gotoProfileActivity.putExtras(bundle)

                startActivity(gotoProfileActivity)
            } else
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();


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