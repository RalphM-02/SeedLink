package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.EditLinkItemBinding

class EditProfileAdapter (private val context: Context, private val LinkList:MutableList<String>)
    : RecyclerView.Adapter<EditProfileAdapter.ProfileEditHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileEditHolder {
        val binding = EditLinkItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfileEditHolder(binding)

    }

    override fun getItemCount(): Int {
        return LinkList.size
    }

    inner class ProfileEditHolder(
        layoutBinding: EditLinkItemBinding
    ) : RecyclerView.ViewHolder(layoutBinding.root) {

        private val binding = layoutBinding

        fun bind(link: String, pos: Int) {
            binding.textLinkEdit.text = link

        }


    }

    override fun onBindViewHolder(holder: EditProfileAdapter.ProfileEditHolder, position: Int) {
        val link = LinkList[position]
        holder.bind(link, position)
    }



    fun deleteLink(pos: Int) {
        LinkList.removeAt(pos)
        notifyDataSetChanged()
    }



}