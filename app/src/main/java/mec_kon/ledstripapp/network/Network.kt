package mec_kon.ledstripapp.network

import android.os.AsyncTask
import io.github.rybalkinsd.kohttp.dsl.httpPost
import okhttp3.Response
import java.lang.Exception

class Network {


    fun createJsonString(colorRed: Int, colorGreen: Int, colorBlue: Int) :String {
        return "{\"color_array\":[{\"color_red\":$colorRed,\"color_green\":$colorGreen,\"color_blue\":$colorBlue}],\"time\":0,\"mode\":\"oneColor\",\"number_of_colors\":1}"
    }

    fun postJsonString(jsonString: String, address: String, port: Int){

        doHttpPost().execute(address, port, jsonString)
    }

    private inner class doHttpPost: AsyncTask<Any, Void, String>(){

        override fun doInBackground(vararg params: Any): String {
            try {
                val response: Response = httpPost {
                    host = params[0] as String
                    port = params[1] as Int

                    path = "/colors.json"

                    body {
                        json(params[2] as String)
                    }
                }
                return response.message()
            }
            catch (e: Exception){
                return "http error !"
            }

        }

        override fun onPostExecute(result: String) {
            println(result)
        }
    }


}