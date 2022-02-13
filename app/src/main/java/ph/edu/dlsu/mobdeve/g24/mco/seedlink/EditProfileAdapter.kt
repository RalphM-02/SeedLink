package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDaoDatabase
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.EditItemBinding

class EditProfileAdapter (private val context: Context, private val LinkList:MutableList<String>, val id: Int)
    : RecyclerView.Adapter<EditProfileAdapter.ProfileEditHolder>() {

    lateinit var linkDao: LinkDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileEditHolder {
        val binding = EditItemBinding.inflate(LayoutInflater.from(context), parent, false)
        linkDao = LinkDaoDatabase(context)
        return ProfileEditHolder(binding)

    }

    override fun getItemCount(): Int {
        return LinkList.size
    }

    inner class ProfileEditHolder(
        layoutBinding: EditItemBinding
    ) : RecyclerView.ViewHolder(layoutBinding.root) {

        private val binding = layoutBinding

        fun bind(link: String, pos: Int) {
            binding.textLinkEdit.text = link

            binding.deleteButton.setOnClickListener{
                deleteLink(pos)
            }



        }






    }

    override fun onBindViewHolder(holder: EditProfileAdapter.ProfileEditHolder, position: Int) {
        val link = LinkList[position]
        holder.bind(link, position)

    }



    fun deleteLink(pos: Int) {
        val builder = AlertDialog.Builder(this.context)
        //set title for alert dialog
        builder.setTitle("Delete Link")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to delete this link?\nDeleted links can't be recovered.")
        builder.setIcon(R.drawable.ic_warning)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            val link = LinkList.get(pos)
            linkDao.deleteLink(id,link)
        LinkList.removeAt(pos)
            notifyDataSetChanged()
            Toast.makeText(this.context,"Delete Successful",Toast.LENGTH_LONG).show()
        }

        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
        Toast.makeText(this.context,"Delete Cancelled",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    fun addLink(link: String){
        LinkList.add(link)
        notifyDataSetChanged()
    }



}