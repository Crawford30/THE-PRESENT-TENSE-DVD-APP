package com.dvds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

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

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,  R.id.presentTenseSongsFragment,R.id.godTenMajorSongsFragment,R.id.bigSongBookSongsFragment,R.id.praisesAndWorshipSongsFragment,R.id.variousTonguesSongsFragment), drawerLayout)

        toolbar.setupWithNavController(navController, appBarConfiguration)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)





//        userPreferences.authToken.asLiveData().observe(this, Observer {
//            val activity = if (it == null) AuthActivity::class.java else MainActivity::class.java
//            startNewActivity(activity)
//        })



    }
}