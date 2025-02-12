package com.example.homefit.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homefit.R
import com.example.homefit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Bindings för att hantera layouten och navigationskontrollen för appens fragment
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflaterar layouten och binder den till aktiviteten
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sätt upp Toolbar som ActionBar för att visa navigationsknappar
        setSupportActionBar(binding.toolbar)

        // Hitta NavHostFragment som kommer att hålla fragmenten som byts ut
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Få NavController från NavHostFragment för att kunna hantera navigation
        navController = navHostFragment.navController

        // Koppla ActionBar till navigation controller
        setupActionBarWithNavController(navController)
    }

    // Hantera navigeringen när användaren trycker på tillbaka-knappen i ActionBar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
