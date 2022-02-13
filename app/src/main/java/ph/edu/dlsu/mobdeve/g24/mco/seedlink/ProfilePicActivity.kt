package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PfpDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PfpDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityProfilePicBinding
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException


class ProfilePicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePicBinding
    lateinit var pfpDao: PfpDao
    val REQUEST_GALLERY = 132
    val REQUEST_CAMERA = 142
    lateinit var pfp: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pfpDao = PfpDaoDatabase(applicationContext)


        var bundle = intent.extras
        var id = bundle!!.getInt("id_bundle")

        var img = pfpDao.getPfp(id)

        if(img != null) {
            var bm = BitmapFactory.decodeByteArray(img, 0, img.size)

            binding.viewPic.setImageBitmap(bm)
        }
        binding.changebtn.setOnClickListener{
           val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Profile Picture")
            builder.setMessage("Get picture from")

            builder.setPositiveButton("Gallery"){
                dialog, which ->
                dialog.dismiss()

                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/"
                startActivityForResult(intent, REQUEST_GALLERY)

            }
            builder.setNegativeButton("Camera"){
                    dialog, which ->
                dialog.dismiss()
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                    takepictureintent ->
                    takepictureintent.resolveActivity(packageManager)?.also{
                        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        if(permission != PackageManager.PERMISSION_DENIED){
                            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),1)

                        }else{
                            startActivityForResult(takepictureintent, REQUEST_CAMERA)
                        }
                    }
                }

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()


        }

        binding.savebtn.setOnClickListener{
            var res = pfpDao.addPfp(id,pfp)
            if(res<0)
                Toast.makeText(this, "Error Saving Picture", Toast.LENGTH_SHORT).show()
            else{
                var editpfpbundle = Bundle()
                editpfpbundle.putInt("id_bundle", id)
                var gotoEditProfileActivity = Intent(applicationContext, EditProfileActivity::class.java)

                gotoEditProfileActivity.putExtras(editpfpbundle)

                startActivity(gotoEditProfileActivity)

            }

        }
    }


    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== REQUEST_GALLERY && resultCode == RESULT_OK &&data != null){

            binding.viewPic.setImageURI(data.data)
            pfp= data.data?.let { URItoBytes(this, it) }!!

        }else if(requestCode== REQUEST_CAMERA && resultCode == RESULT_OK &&data != null){

            binding.viewPic.setImageBitmap(data.extras?.get("data")as Bitmap)
            pfp = data.extras?.get("data")as ByteArray

        }else
            Toast.makeText(this, "Error Uploading Picture", Toast.LENGTH_SHORT).show()

    }

    private fun URItoBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
}
