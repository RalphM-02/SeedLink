package ph.edu.dlsu.mobdeve.g24.mco.seedlink

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddLinkViewModel:ViewModel(){


    val link = MutableLiveData<String>()

    fun sendLink(link:String){

      //  val final = MutableLiveData<String>().apply { postValue(link)}
        this.link.value =  link}

    fun getLink(): String{
        return link.toString()
    }

}
