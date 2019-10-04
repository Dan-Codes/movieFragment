package com.example.homework3_moviefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val appBar = supportActionBar

//        appBar?.setDisplayHomeAsUpEnabled(true)
        //setupSideNavigationMenu(navController)
        //actionBar.hide()
        NavigationUI.setupActionBarWithNavController(this,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return super.onOptionsItemSelected(item)
//        when (item?.itemId)
//        {
//            android.R.id.home -> {
//                Log.d("debug", "back button pressed")
//                navController.navigateUp()
//                return true
//            }
//            R.id.aboutMe -> {
//                val appBar = supportActionBar
//                appBar?.setDisplayHomeAsUpEnabled(true)
//            }
//        }
//    }


}
