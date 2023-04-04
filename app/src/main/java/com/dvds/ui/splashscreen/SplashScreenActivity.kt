package com.dvds.ui.splashscreen

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dvds.MainActivity
import com.dvds.R
import com.dvds.helpers.startNewActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)


//        val splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
//        splashViewModel.liveData.observe(this, Observer {
//            when (it) {
//                is SplashState.MainActivity -> {
//                    val SPLASH_TIME_OUT = 7000 //7 seconds
//
//
//                    Handler().postDelayed({
//            //Do some stuff here, like implement deep linking
//                        goToMainActivity()
//
//        }, SPLASH_TIME_OUT.toLong())
//                }
//            }
//        })

        val SPLASH_TIME_OUT = 7000 //7 seconds

        Handler().postDelayed({
            //Do some stuff here, like implement deep linking
           startNewActivity(MainActivity::class.java)
        }, SPLASH_TIME_OUT.toLong())


    }


    private fun goToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

}