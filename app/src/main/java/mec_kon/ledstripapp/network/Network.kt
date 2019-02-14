package mec_kon.ledstripapp.network

import android.util.Log
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity

class Network {

    val asyncHttpClient = AsyncHttpClient()

    fun createJsonString(colorRed: Int, colorGreen: Int, colorBlue: Int) :String {
        return "{\"color_array\":[{\"color_red\":$colorRed,\"color_green\":$colorGreen,\"color_blue\":$colorBlue}],\"time\":0,\"mode\":\"oneColor\",\"number_of_colors\":1}"
    }

    fun postJsonString(jsonString: String, address: String, port: Int){

        val entity = StringEntity(jsonString)
        val responseHandler = ResponseHandler()

        asyncHttpClient.post(null, "http://$address:$port/colors.json", entity, "application/json", responseHandler)
    }

    inner class ResponseHandler : AsyncHttpResponseHandler() {
        override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
            Log.e("Network", "Sending failed, response =  " + String(responseBody ?: "no response".toByteArray()))
        }

        override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
            Log.i("Network", "Success, response = " +  String(responseBody ?: "no response".toByteArray()))
        }
    }


}