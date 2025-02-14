package com.example.homefit.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.homefit.R
import com.example.homefit.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    // Bindings för att hantera layouten och navigationskontrollen för appens fragment
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflaterar layouten och binder den till aktiviteten
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hämta DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Sätt upp Toolbar som ActionBar för att visa navigationsknappar
        setSupportActionBar(binding.toolbar)

        // Hitta NavHostFragment som kommer att hålla fragmenten som byts ut
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Få NavController från NavHostFragment för att kunna hantera navigation
        navController = navHostFragment.navController

        // Koppla ActionBar till navigation controller
        setupActionBarWithNavController(navController)


        // Koppla Navigation Drawer till NavController
        val navigationView: NavigationView = binding.navView
        navigationView.setupWithNavController(navController)

        // Lägg till en "hamburgare"-knapp för att öppna/stänga menyn
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Koppla NavigationView till navigationen
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

    }

    // Hantera navigeringen när användaren trycker på tillbaka-knappen i ActionBar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}




