package ph.edu.dlsu.mobdeve.g24.mco.seedlink


import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharePrefUtility {

    private var appPreferences: SharedPreferences? = null
    private val PREFS = "appPreferences"

    constructor(context: Context) {
        appPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun saveStringPreferences(key: String?, value: String?) {
        val prefsEditor = appPreferences!!.edit()
        prefsEditor.putString(key, value).commit()
    }

    fun getStringPreferences(key: String?): String?
            = appPreferences!!.getString(key, "Nothing Saved")

    fun removeStringPreferences(key: String?){
        val prefsEditor = appPreferences!!.edit()
        prefsEditor.remove(key).commit()
    }

    fun removeAllPreferences(){
        appPreferences!!.edit().clear().commit()
    }
}