package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.trimmedLength
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityEditBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var adapter: ProfileAdapter
    private lateinit var binding: ActivityEditBinding
    private var linkList: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)




        var bundle = intent.extras
        var username = bundle!!.getString("user_bundle")
        var password = bundle!!.getString("pass_bundle")
        binding.editPasswordLayout.hint = "Old Password: " + password
        binding.editUsernameLayout.hint = "Old Username: " + username

        linkList = bundle!!.getStringArrayList("links_bundle")!!
        setRecyclerView()


        binding.savebtn.setOnClickListener(){


            //Input
            val user = binding.editUsername.text.toString()
            val pass = binding.editPassword.text.toString()


            //Copy the contents of previous bundle
            var editbundle = Bundle()
            editbundle.putString("user_bundle",username )
            editbundle.putString("pass_bundle", password)
            editbundle.putStringArrayList("links_bundle", bundle!!.getStringArrayList("links_bundle"))

            if(user.isNotBlank()) //changes in username
            {
                editbundle.putString("user_bundle",user)
            }

            if(pass.isNotBlank()) //changes in password
            {
                editbundle.putString("user_bundle",pass)
            }

            if(checkUsername(user) && checkPassword(pass)) {
                var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)

                gotoProfileActivity.putExtras(editbundle)

                startActivity(gotoProfileActivity)
            }

        }
        //TODO: Upload Pic
//        binding.editPic.setOnClickListener(){
//            val gotoProfilePicActivity = Intent(applicationContext, ProfilePicActivity::class.java)
//            startActivity(gotoProfilePicActivity)
//
//        }

    }

    private fun populateList() {
        this.linkList.add("www.website/sampleLink1");
        this.linkList.add("www.website/sampleLink2");
        this.linkList.add("www.website/sampleLink3");
        this.linkList.add("www.website/sampleLink4");
        this.linkList.add("www.website/sampleLink5");
        this.linkList.add("www.website/sampleLink6");




    }

    private fun setRecyclerView() {
        adapter = ProfileAdapter(this,linkList)
        binding.editLinkList.adapter = adapter
        binding.editLinkList.layoutManager = LinearLayoutManager(this)
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