package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.UserDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPrefUtility: SharePrefUtility
    lateinit var userDao: UserDao
    lateinit var linkDao: LinkDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDao= UserDaoDatabase(applicationContext)
        linkDao = LinkDaoDatabase(applicationContext)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding!!.loginbtn.setOnClickListener {
            var password = binding.loginPassword.text.toString()
            var username = binding.loginUsername.text.toString()


            var loginTemp = userDao.getUser(username)
            if (loginTemp != null) {
                if(loginTemp.username.isEmpty()){
                    Toast.makeText(this, "Cannot Find User", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals(loginTemp.pass)) {


                    var bundle = Bundle()

                    bundle.putInt("id_bundle", loginTemp.id)

                    var gotoProfileActivity = Intent(applicationContext, ProfileActivity::class.java)

                    gotoProfileActivity.putExtras(bundle)
                    sharedPrefUtility.saveStringPreferences("user_username", username)
                    startActivity(gotoProfileActivity)

                }else{
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Cannot Find User", Toast.LENGTH_SHORT).show();

            }
        }

        binding!!.signupbtn.setOnClickListener {

            //Go to profile activity
            val gotoRegisterActivity = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(gotoRegisterActivity)
            finish()

        }
        initPrefs()
        Toast.makeText(
            applicationContext,
            sharedPrefUtility.getStringPreferences("app_loaded").toString()
                    + "\n - \n"
                    + sharedPrefUtility.getStringPreferences("app_closed"),
            Toast.LENGTH_LONG
        ).show()
    }

    fun initPrefs(){
        sharedPrefUtility = SharePrefUtility(this)
    }

    override fun onResume() {
        super.onResume()
        sharedPrefUtility.saveStringPreferences("app_loaded", Date().toString())
    }

    override fun onPause() {
        super.onPause()
        sharedPrefUtility.saveStringPreferences("app_closed", Date().toString())
    }


    override fun onBackPressed() {
        val builder = android.app.AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Exit App")
        //set message for alert dialog
        builder.setMessage("Click yes to close app")
        builder.setIcon(R.drawable.ic_warning)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
           super.onBackPressed()
            exitProcess(0)
            finish()
        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(this,"Exit Cancelled",Toast.LENGTH_LONG).show()
        }
        val alertDialog: android.app.AlertDialog = builder.create()


        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}
