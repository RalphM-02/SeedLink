package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.ActivityEditBinding
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.AddLinkDialogBinding

class AddLinkDialog: DialogFragment() {

    private lateinit var binding: AddLinkDialogBinding
    private lateinit var viewModel: AddLinkViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.rounded_corner);

        return inflater.inflate(R.layout.add_link_dialog, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AddLinkDialogBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(AddLinkViewModel::class.java)
        setListeners(view);

    }

    private fun setListeners(view: View) {
        binding.addLinkBtn.setOnClickListener {
            viewModel.sendLink(binding.addLinkEditText.text.toString())
            dismiss()
        }
        binding.cancelLinkBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    companion object{
        const val TAG = "Add Link"
        fun dialogInstance(username: String): AddLinkDialog{
            val args = Bundle()
            args.putString("username", username)
            val fragment = AddLinkDialog()
            fragment.arguments = args
            return fragment
        }


    }




}