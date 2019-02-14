package mec_kon.ledstripapp.storage

import android.app.Activity
import android.content.Context
import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Storage(private val context: Activity) {

    private val gson = Gson()
    private val filename = "config.txt"
    val file = File(context.filesDir, filename)

    var fileContent: String
        get() {
            if (file.exists()) {
                return file.readText()
            } else {
                return ""
            }
        }
        set(value) {
            context.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(value.toByteArray())
            }
        }


    var settings: Settings
        get() {
            if(fileContent != ""){
                return gson.fromJson(fileContent, object: TypeToken<Settings>() {}.type)
            }
            else {
                return Settings("127.0.0.1", 9999)
            }
        }
        set(value) {
            fileContent = gson.toJson(value)
        }
}
