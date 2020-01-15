package com.example.forecastapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.forecastapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // to instantiate the fragments and where they live
        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        //this takes us to the fragments selected by the menu
        // menu items and fragments id have to the same name to navigate when they are tapped
        bottom_nav.setupWithNavController(navController)

        // to give the control of the toolbar to the navController
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    // upNavigation controls the arrow that appears on top of the view and lets us to
    // return to current weather
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}
