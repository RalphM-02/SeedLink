package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.trimmedLength
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var userDao: UserDao
    lateinit var linkDao: LinkDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDao= UserDaoDatabase(applicationContext)
        linkDao = LinkDaoDatabase(applicationContext)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //TODO: click on sign up confirm btn when enter is pressed
        binding.signupConfirmbtn.setOnClickListener {
            val confirmPassword = binding.registerConfirmPassword.text.toString()
            val password = binding.registerPassword.text.toString()
            val username = binding.registerUsername.text.toString()



            if (confirmPassword.equals(password)) {
                if(checkUsername(username) && checkPassword(password)) {
                    userDao.addUser(UserClass(username,password))
                    var temp = userDao.getUser(username)
                    var bundle = Bundle()
                    bundle.putInt("id_bundle", temp!!.id)
                    var gotoProfileActivity =
                        Intent(applicationContext, ProfileActivity::class.java)

                    gotoProfileActivity.putExtras(bundle)

                    startActivity(gotoProfileActivity)
                    finish()
                }
            } else
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();


        }

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
            Toast.makeText(this, "Usernames must not contain any white spaces.", Toast.LENGTH_SHORT).show();
            b= false
        }

        //Check if username is already taken
        val temp = userDao.getUsers()
        if(temp!=null) {
            for (i in 0 until temp.size) {

                if (username.equals(temp.get(i)?.username)) {
                    Toast.makeText(this, "Username already taken.", Toast.LENGTH_SHORT).show();

                    b = false
                }
            }
        }

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