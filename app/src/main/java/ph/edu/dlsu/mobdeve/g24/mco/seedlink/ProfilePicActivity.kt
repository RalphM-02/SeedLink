package ph.edu.dlsu.mobdeve.g24.mco.seedlink


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PfpDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PfpDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityProfilePicBinding
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class ProfilePicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePicBinding
    lateinit var pfpDao: PfpDao
    lateinit var pfp: ByteArray
    val CAMERA_CODE = 1034

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pfpDao = PfpDaoDatabase(applicationContext)


        var bundle = intent.extras
        var id = bundle!!.getInt("id_bundle")

        var img = pfpDao.getPfp(id)
        if (img.size == 0)
            binding.viewPic.setImageResource(R.drawable.ic_profile_pic)
        else {
            //bytearray to new risized bitmap factory
            val resize: Bitmap = BitmapFactory.decodeByteArray(img, 0, img.size)
            binding.viewPic.setImageBitmap(Bitmap.createScaledBitmap(resize, 120, 120, false));

        }




        val galleryLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {

                if (it == null) {
                    Toast.makeText(this, "Please choose a picture", Toast.LENGTH_SHORT).show();

                } else {
                    binding!!.viewPic.setImageURI(it)
                    pfp = URItoBytes(applicationContext, it)!!
                    val resize: Bitmap = BitmapFactory.decodeByteArray(pfp, 0, pfp.size)
                    binding.viewPic.setImageBitmap(Bitmap.createScaledBitmap(resize, 120, 120, false));
                }
            }
        )


        binding.changebtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Profile Picture")
            builder.setMessage("Get picture from")

            builder.setPositiveButton("Gallery") { dialog, which ->
                dialog.dismiss()
                galleryLauncher.launch("image/*")

            }

            builder.setNegativeButton("Camera") { dialog, which ->
                dialog.dismiss()
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                        takepictureintent ->
                    takepictureintent.resolveActivity(packageManager)?.also{
                        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                        if(permission != PackageManager.PERMISSION_DENIED){
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),1)

                        }else{
                            startActivityForResult(takepictureintent, CAMERA_CODE)
                        }
                    }
                }

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }


        binding.savebtn.setOnClickListener {
            var res = -1
            if(pfpDao.getPfp(id).size != 0){
                Toast.makeText(this, "Updated Pfp", Toast.LENGTH_SHORT).show()
                pfpDao.updatePfp(id, pfp)}
            else{
                Toast.makeText(this, "Added Pfp", Toast.LENGTH_SHORT).show()
                pfpDao.addPfp(id,pfp)}


                var editpfpbundle = Bundle()
                editpfpbundle.putInt("id_bundle", id)
                var gotoEditProfileActivity =
                    Intent(applicationContext, EditProfileActivity::class.java)


                gotoEditProfileActivity.putExtras(editpfpbundle)

                startActivity(gotoEditProfileActivity)
                finish()



        }
    }
    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK && data != null) {

            binding.viewPic.setImageBitmap(data.extras?.get("data") as Bitmap)

            //convert bitmap to bytearray
            val bm = data.extras?.get("data") as Bitmap
            val byteStream = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.PNG, 90, byteStream)
            pfp = byteStream.toByteArray()

            //bytearray to new risized bitmap factory
            val resize: Bitmap = BitmapFactory.decodeByteArray(pfp, 0, pfp.size)
            binding.viewPic.setImageBitmap(Bitmap.createScaledBitmap(resize, 120, 120, false));

        } else
            Toast.makeText(this, "Error Uploading Picture", Toast.LENGTH_SHORT).show()
    }

    private fun URItoBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }



}