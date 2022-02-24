package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.text.trimmedLength
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.*
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityEditBinding
import android.graphics.BitmapFactory

import android.graphics.Bitmap






class EditProfileActivity : AppCompatActivity() {

    private lateinit var adapter: EditProfileAdapter
    private lateinit var binding: ActivityEditBinding
    private lateinit var addLinkViewModel: AddLinkViewModel
    private var pendingLinks: ArrayList<String> = arrayListOf()
    private var linkList: MutableList<String> = mutableListOf()
    lateinit var linkDao: LinkDao
    lateinit var userDao: UserDao
    lateinit var pfpDao: PfpDao
    lateinit var userOld: UserClass


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        linkDao = LinkDaoDatabase(applicationContext)
        userDao = UserDaoDatabase(applicationContext)
        pfpDao = PfpDaoDatabase(applicationContext)
        setContentView(binding.root)



        var bundle = intent.extras
        var id = bundle!!.getInt("id_bundle")

        //Stores previous info
        if( userDao.getUser(id) != null)
            userOld = userDao.getUser(id)!!
        else
            Toast.makeText(
                this,
                "Can't Find User Profile.",
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


        binding.editPasswordLayout.hint = "Old Password: " + userOld.pass
        binding.editUsernameLayout.hint = "Old Username: " + userOld.username



        if(linkDao.getLinks(userOld.id) == null)
            linkList = mutableListOf()
        else
            linkList = linkDao.getLinks(userOld.id)!!

        setRecyclerView(userOld.username)


        binding.addbtn.setOnClickListener(){
            if (userOld.username.length > 0) {

                AddLinkDialog().show(supportFragmentManager, AddLinkDialog.TAG)

                addLinkViewModel = ViewModelProvider(this).get(AddLinkViewModel::class.java)

                addLinkViewModel.link.observe(this, Observer {
                    if(it.toString()!= "" && it.toString().length > 0) {
                        pendingLinks.add(it.toString())
                        adapter.addLink(it)
                        addLinkViewModel.sendLink("")
                    }
            })
            }else
        Toast.makeText(this, "Link Addition Unsuccessful: UserDB Error ", Toast.LENGTH_SHORT).show()




    }
        binding.savebtn.setOnClickListener() {

            //Input
            var user = binding.editUsername.text.toString()
            var pass = binding.editPassword.text.toString()



                //add new links
            for (x in pendingLinks) {
                if (x.equals("") && x.length == 0)
                    Toast.makeText(
                        this,
                        "Empty String",
                        Toast.LENGTH_SHORT
                    ).show()
                else {

                    val res = linkDao.addLink(userOld.id, x)

                    if (res > 0) {
                        Toast.makeText(
                            this,
                            "Link Addition Successful.",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else
                        Toast.makeText(
                            this,
                            "Link Addition Unsuccessful: LinkDB Error",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }

            var user_changed = true
            var pw_changed = true
                //Save Changes to db
                if (user.equals("")||user.length==0){
                    user_changed = false
                    user = userOld.username
                }

                if (pass.equals("")|| user.length ==0){
                    pw_changed = false
                    pass = userOld.pass
                }

            if (checkUsername(user,user_changed) && checkPassword(pass)) {

                if (user_changed || pw_changed) {
                    var temp = UserClass(userOld!!.id, user, pass)
                    userDao.updateUser(temp)
                }


                //Prep for next activity
                var editbundle = Bundle()
                editbundle.putInt("id_bundle", userOld.id)
                var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)

                gotoProfileActivity.putExtras(editbundle)

                startActivity(gotoProfileActivity)
                finish()
            }else
                Toast.makeText(
                    this,
                    "Save Unsuccessful",
                    Toast.LENGTH_SHORT
                ).show()
        }

        binding.editPic.setOnClickListener(){
            var editbundle2 = Bundle()
            if (userOld != null) {
                editbundle2.putInt("id_bundle", userOld.id)
                val gotoProfilePicActivity = Intent(applicationContext, ProfilePicActivity::class.java)
                gotoProfilePicActivity.putExtras(editbundle2)
                startActivity(gotoProfilePicActivity)
                finish()
            }
            else
                Toast.makeText(this, "Can't Edit Pfp: UserDB Error", Toast.LENGTH_SHORT).show()



        }



    }



    private fun setRecyclerView(username: String) {
        adapter = EditProfileAdapter(this,linkList, userOld.id)
        binding.editLinkList.adapter = adapter
        binding.editLinkList.layoutManager = LinearLayoutManager(this)
    }


    private fun checkUsername(username: String, changed : Boolean ): Boolean {
        var b = true

        //usernameS: MIN: 8; MAX:15
        if(username.trimmedLength() < 8 || username.trimmedLength() > 15)
        {
            Toast.makeText(this, "Usernames must have a minimum of 8 characters and a maximum of 15.", Toast.LENGTH_SHORT).show()
            b=false
        }

        //Check for spaces
        var result = username.filter { it.isWhitespace() }
        if(result.isNotBlank())
        {
            Toast.makeText(this, "Usernames must not contain any white spaces.", Toast.LENGTH_SHORT).show();
            b= false
        }


        if(changed){
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

