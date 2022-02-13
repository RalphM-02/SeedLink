package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityCreatePostBinding

class CreatePostActivity : AppCompatActivity() {
    var binding : ActivityCreatePostBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding!!.ivPostImage.setImageURI(it)
            }
        )

        binding!!.btnUploadImage.setOnClickListener {
            getImage.launch("image/*")
        }


    }
}