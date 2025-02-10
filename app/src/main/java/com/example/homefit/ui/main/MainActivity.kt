package com.example.homefit.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homefit.R
import com.example.homefit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // Skapa en instans av ViewBinding för att länka layoutkomponenterna
    private lateinit var binding: ActivityMainBinding

    // Deklarera en NavController för att hantera navigeringen mellan fragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ViewBinding för att binda layouten till aktivitetens vy
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sätt Toolbar som ActionBar för att visa navigationselement
        setSupportActionBar(binding.toolbar)

        // Hämta NavHostFragment från layouten där fragmenten ska bytas ut
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Få navController från NavHostFragment för att kunna hantera navigationen
        navController = navHostFragment.navController

        // Koppla ActionBar till navigation controller
        setupActionBarWithNavController(navController)
    }

    // Hantera navigeringen när användaren trycker på skärmen
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
