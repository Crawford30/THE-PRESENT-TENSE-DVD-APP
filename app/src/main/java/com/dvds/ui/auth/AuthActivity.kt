package com.dvds.ui.auth

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.dvds.MainActivity
import com.dvds.R
import com.dvds.data.responses.user.UserPreferences
import com.dvds.helpers.startNewActivity
import com.dvds.ui.splashscreen.SplashScreenActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)




        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
            if (it == null) {
               startNewActivity( AuthActivity::class.java)
            }else{
                startNewActivity( SplashScreenActivity::class.java)
            }

        })
    }
}