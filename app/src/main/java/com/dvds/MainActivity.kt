package com.dvds

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dvds.data.responses.user.UserPreferences
import com.dvds.helpers.startNewActivity
import com.dvds.ui.auth.AuthActivity
import com.dvds.ui.player.PlayerFragment.Companion.isLock
import com.dvds.ui.splashscreen.SplashScreenActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.custom_controller.*

class MainActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var drawerLayout: DrawerLayout
    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,  R.id.audioDVDSFragment,R.id.videoDVDSFragment,R.id.EPublicationsFragment,R.id.contactFragment), drawerLayout)

        toolbar.setupWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

//
//        AlertDialog.Builder(this@MainActivity)
//            .setTitle("Logout")
//            .setMessage("Are you sure you want to logout now?")
//            .setNegativeButton("NO", null)
//            .setPositiveButton("YES"
//            ) { arg0, arg1 ->
//                logUserOut()
//            }.create().show()



    }


      override  fun onBackPressed() {
        if(isLock) return
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            bt_fullscreen.performClick()
        }
       else super.onBackPressed()

    }







}