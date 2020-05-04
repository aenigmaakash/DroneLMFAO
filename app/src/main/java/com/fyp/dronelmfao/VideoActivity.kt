package com.fyp.dronelmfao

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.http.HttpResponseCache
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_video.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.Exception

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        startStreamBtn.setOnClickListener {
            startStreaming()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mv.isActive())
            mv.stopStream()
    }

    override fun onPause() {
        super.onPause()
        if(mv.isActive())
            mv.stopStream()
    }

    private fun startStreaming(){
        var url = "http://"
        val connectivityManager = applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityManager
            .getNetworkCapabilities(connectivityManager.activeNetwork)
            .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        if(ip1st.text.isNotEmpty() && ip2nd.text.isNotEmpty() && ip3rd.text.isNotEmpty() && ip4th.text.isNotEmpty()){
            url = url +  ip1st.text + "." + ip2nd.text + "." + ip3rd.text + "." + ip4th.text + ":"
            url = if(ipPort.text.isNotEmpty()){
                url + ipPort.text + "/"
            } else
                url + ipPort.hint + "/"
            if(commandText.text.isNotEmpty()){
                url += commandText.text
            }
            else
                url += commandText.hint

        }
        else{
            url += "192.168.1.102:8081/stream.mjpg"
        }
        Log.i("URL being used", url)
        if(wifi){
            try{
                if(mv.isActive())
                    mv.stopStream()
                mv.setMode(MjpegView.MODE_FIT_WIDTH)
                mv.isAdjustHeight = true
                mv.setUrl(url)
                mv.startStream()
                Toast.makeText(this, "Starting stream", Toast.LENGTH_SHORT).show()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
        else
            Toast.makeText(this, "Please connect to local wifi network", Toast.LENGTH_SHORT).show()
    }
}
