package com.dvds.helpers

import android.content.Context
import android.net.ConnectivityManager

object DetectConnection  {
    fun checkInternetConnection(context: Context):Boolean {
        val con_manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return ((con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo()!!.isAvailable()
                && con_manager.getActiveNetworkInfo()!!.isConnected()))
    }
}