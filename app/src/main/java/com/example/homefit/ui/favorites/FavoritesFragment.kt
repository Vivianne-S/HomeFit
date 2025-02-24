package com.example.homefit.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homefit.R
import com.example.homefit.ui.data.WorkoutData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var tvNoFavorites: TextView
    private val favoritesList: MutableList<WorkoutData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflaterar layouten för fragmentet
        val rootView = inflater.inflate(R.layout.fragment_favorites, container, false)

        // Hämta referenser till RecyclerView och TextView för "Inga favoriter"
        recyclerView = rootView.findViewById(R.id.recyclerViewFavorites)
        tvNoFavorites = rootView.findViewById(R.id.tvNoFavorites)

        // Ställer in LayoutManager och Adapter för RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        favoritesAdapter = FavoritesAdapter(favoritesList) { workout ->
            removeFavorite(workout) // Sätt en klicklistener för att ta bort favoriten
        }
        recyclerView.adapter = favoritesAdapter

        // Hämtar favoriter från Firestore
        fetchFavorites()

        return rootView
    }

    private fun fetchFavorites() {
        // Hämta användarens ID från Firebase Auth
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Log.e("FavoritesFragment", "User not logged in.")
            return
        }

        val db = FirebaseFirestore.getInstance()

        // Hämta favoriter från Firestore under användarens "favorites"-samling
        db.collection("users").document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { documents ->
                favoritesList.clear() // Töm listan innan vi fyller på med nya favoriter
                var firstItemInserted = false
                // Iterera genom dokumenten som vi hämtade från Firestore
                for (document in documents) {
                    // Hämta värdena från dokumentet
                    val name = document.getString("name") ?: continue
                    val description = document.getString("description") ?: ""
                    val calories = document.getLong("caloriesBurned")?.toInt() ?: 0
                    val imageUrl = document.getString("imageUrl") ?: "" // Om imageUrl finns

                    // Skapa ett WorkoutData-objekt med hämtad information
                    val workoutData = WorkoutData(name, description, calories, imageUrl)

                    // Lägg till övningen i listan
                    favoritesList.add(workoutData)

                    // Om det är första objektet som läggs till, informera adaptern om att en ny post har lagts till
                    if (!firstItemInserted) {
                        favoritesAdapter.notifyItemInserted(0)
                        firstItemInserted = true
                    } else {
                        // För efterföljande objekt, informera adaptern om den senaste posten
                        favoritesAdapter.notifyItemInserted(favoritesList.size - 1)
                    }
                }
                updateUI() // Uppdatera UI (visa/dölj meddelandet "Inga favoriter")
                Log.d("FavoritesFragment", "Fetched favorites: $favoritesList")
            }
            .addOnFailureListener { e ->
                // Om det blev ett fel vid hämtning av favoriter, logga felet
                Log.e("FavoritesFragment", "Error fetching favorites", e)
            }
    }

    private fun updateUI() {
        // Om listan är tom, visa "Inga favoriter" och göm RecyclerView
        if (favoritesList.isEmpty()) {
            tvNoFavorites.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Om det finns favoriter, visa RecyclerView och göm meddelandet
            tvNoFavorites.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun removeFavorite(workoutData: WorkoutData) {
        // Hämta användarens ID från Firebase Auth
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            // Om användaren inte är inloggad, logga ett fel och returnera
            Log.e("FavoritesFragment", "User not logged in.")
            return
        }

        val db = FirebaseFirestore.getInstance()

        // Ta bort favoriten från Firestore
        db.collection("users").document(userId)
            .collection("favorites")
            .document(workoutData.name)
            .delete()
            .addOnSuccessListener {
                // Hitta positionen för den borttagna favoriten i listan
                val position = favoritesList.indexOf(workoutData)
                if (position >= 0) {
                    // Ta bort övningen från listan och meddela adaptern om förändringen
                    favoritesList.removeAt(position)
                    favoritesAdapter.notifyItemRemoved(position)
                    updateUI() // Uppdatera UI (visa/dölj meddelandet "Inga favoriter")
                    Log.d("FavoritesFragment", "Removed favorite: ${workoutData.name}")
                }
            }
            .addOnFailureListener { e ->
                // Om det blev ett fel vid borttagning av favorit, logga felet
                Log.e("FavoritesFragment", "Error removing favorite", e)
            }
    }
}
