package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.databinding.LinkItemBinding

class ProfileAdapter (private val context: Context, private val LinkList:MutableList<String>)
: RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = LinkItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProfileViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ProfileViewHolder, pos: Int) {
        val link = LinkList[pos]
        holder.bind(link, pos)
    }

    override fun getItemCount(): Int {
        return LinkList.size
    }

    inner class ProfileViewHolder(
        layoutBinding: LinkItemBinding
    ) : RecyclerView.ViewHolder(layoutBinding.root) {

        private val binding = layoutBinding

        fun bind(link: String, pos: Int) {
            binding.textLinkView.text = link

        }


    }
}