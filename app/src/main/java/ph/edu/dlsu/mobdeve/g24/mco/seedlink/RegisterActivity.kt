package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.trimmedLength
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

                //TODO: Check input if it passed restrictions


                if(checkUsername(username) && checkPassword(password)) {
                    var temp: UserClass
                    temp = UserClass(username, password, links)
                    var bundle = Bundle()
                    bundle.putString("user_bundle", temp.username)
                    bundle.putString("pass_bundle", temp.pass)
                    bundle.putStringArrayList("links_bundle", temp.links)
                    var gotoProfileActivity =
                        Intent(applicationContext, ProfileActivity::class.java)

                    gotoProfileActivity.putExtras(bundle)

                    startActivity(gotoProfileActivity)
                }
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


    private fun checkUsername(username: String ): Boolean {
        var b = true

        //usernameS: MIN: 8; MAX:15
        if(username.trimmedLength() < 8 || username.trimmedLength() > 15)
        {
            Toast.makeText(this, "Usernames must have a minimum of 8 characters and a maximum of 15.", Toast.LENGTH_SHORT).show();
            b=false
        }

        //Check for spaces
        var result = username.filter { it.isWhitespace() }
        if(result.isNotBlank())
        {
            Toast.makeText(this, "Passwords must not contain any white spaces.", Toast.LENGTH_SHORT).show();
            b= false
        }
        //Check if username is already taken

        return b
    }

    private fun checkPassword(password: String): Boolean {
        var b = true

        //passwords: MIN: 10; MAX: 15
        if(password.trimmedLength() < 10 || password.trimmedLength() > 15)
        {
            Toast.makeText(this, "Passwords must have a minimum of 10 characters and a maximum of 15.", Toast.LENGTH_SHORT).show();
            b= false
        }
        //Must contain numbers
        var result1 = password.filter { it.isDigit() }
        if(result1.isBlank() || result1.length < 4)
        {
            Toast.makeText(this, "Passwords must contain at least 4 numbers.", Toast.LENGTH_SHORT).show();
            b= false
        }

        //Check for spaces
        var result2 = password.filter { it.isWhitespace() }
        if(result2.isNotBlank())
        {
            Toast.makeText(this, "Passwords must not contain any white spaces.", Toast.LENGTH_SHORT).show();
            b= false
        }
        return b
    }
}