package com.example.mausam.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mausam.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navHostFragment =supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController:NavController=navHostFragment.navController
        /*val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.currentLocationFragment,
                R.id.favoriteFragment,
                R.id.forecastFragment
            )
        )*/
        bottomNavigationView.setupWithNavController(navController)
        //setupActionBarWithNavController(navController,appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}