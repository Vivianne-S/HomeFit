package com.example.homefit.ui.main

import android.os.Bundle
import android.content.SharedPreferences
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

        // Hämta Toolbar-knapparna
        val backButton = findViewById<ImageButton>(R.id.back_button)
        val menuButton = findViewById<ImageButton>(R.id.menu_button)

        // Sätt upp Toolbar som ActionBar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Hitta NavHostFragment och hämta NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment, R.id.signUpFragment, R.id.splashFragment, R.id.forgotPasswordFragment -> {
                    // Dölj menyn och tillbaka-knappen på dessa sidor
                    supportActionBar?.hide()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    // Visa menyn och tillbaka-knappen på andra sidor
                    supportActionBar?.show()
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }

        // Koppla NavigationView till NavController
        val navigationView: NavigationView = binding.navView
        navigationView.setupWithNavController(navController)

        // Tillbaka-knappen navigerar bakåt
        backButton.setOnClickListener {
            if (!navController.popBackStack()) {
                finish() // Stänger appen om det inte finns fler steg att gå tillbaka
            }
        }

        // Hamburgermenyn öppnar Navigation Drawer (från höger)
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

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

        // Meny-knapp (öppnar navigation drawer)
        binding.menuButton.setOnClickListener {
            drawerLayout.openDrawer(binding.navView)  // Öppnar sidomenyn
        }
    }

    // Hantera navigering när användaren trycker på tillbaka-knappen i ActionBar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
