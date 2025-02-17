package com.example.homefit.ui.main

import android.os.Bundle
import android.content.SharedPreferences
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
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bind layouten
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hämta DrawerLayout
        drawerLayout = binding.drawerLayout

        // Sätt upp Toolbar som ActionBar
        setSupportActionBar(binding.toolbar)

        // Lägg till en "hamburgare"-knapp för att öppna/stänga menyn
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        // Lägger till lyssnare för att synkronisera tillståndet för DrawerLayout och toolbar
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Hitta NavHostFragment och hämta NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Koppla NavigationView till NavController
        val navigationView: NavigationView = binding.navView
        navigationView.setupWithNavController(navController)

        // Hantera när en menyval väljs i NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.categoriesFragment) // Navigera till CategoriesFragment
                }
                R.id.nav_favorites -> {
                    // TODO: Navigera till FavoritesFragment
                }
                R.id.nav_profile -> {
                    navController.navigate(R.id.profileFragment) // Navigera till ProfileFragment
                }
                R.id.nav_calendar -> {
                    // TODO: Navigera till CalendarFragment
                }
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    sharedPreferences.edit().putBoolean("isLoggedIn", false).apply() // Uppdatera SharedPreferences vid utloggning
                    navController.navigate(R.id.signInFragment) // Navigera till LoginFragment
                }
            }
            true
        }

        // Kontrollera om användaren är inloggad och upplåsa menyn om det är fallet
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isUserLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isUserLoggedIn) {
            // Om användaren är inloggad, upplås menyn
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else {
            // Om användaren inte är inloggad, lås menyn
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        // Sätt en listener på hamburgarikonen för att öppna menyn
        binding.toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(binding.navView)  // Öppna menyn manuellt
        }
    }

    // Hantera navigering när användaren trycker på tillbaka-knappen i ActionBar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
