package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDao
import ph.edu.dlsu.mobdeve.g24.mco.seedlink.dao.LinkDaoDatabase

class AddLinkViewModel:ViewModel (){


    val link = MutableLiveData<String>()

    fun sendLink(link:String){
        this.link.value = link
    }
}