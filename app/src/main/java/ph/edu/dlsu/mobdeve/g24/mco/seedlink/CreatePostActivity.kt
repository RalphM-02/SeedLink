package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.PostDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {
    var binding : ActivityCreatePostBinding? = null
    lateinit var postImage: ByteArray
    lateinit var postDao: PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        var bundle = intent.extras
        var author = bundle!!.getString("username_bundle")
        postDao = PostDaoDatabase(applicationContext)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding!!.ivPostImage.setImageURI(it)
                postImage = URItoBytes(applicationContext, it)!!
            }
        )

        binding!!.btnUploadImage.setOnClickListener {
            getImage.launch("image/*")
        }

        binding!!.btnPostImage.setOnClickListener {
            var caption = binding!!.etEnterCaption.text.toString()
            postDao.addPost(author!!, postImage, caption)
        }


    }

    private fun URItoBytes(context: Context, uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }
}