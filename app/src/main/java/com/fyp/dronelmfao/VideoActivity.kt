package com.fyp.dronelmfao

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.http.HttpResponseCache
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_video.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.Exception

class VideoActivity : AppCompatActivity() {

    companion object{
        private const val url = "http://192.168.1.102:8081/stream.mjpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        if(wifi){
            try{
                mv.setMode(MjpegView.MODE_BEST_FIT)
                mv.setUrl(url)
                mv.startStream()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        mv.stopStream()
        super.onDestroy()
    }


}
