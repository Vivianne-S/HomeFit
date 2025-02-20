import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homefit.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class CalenderFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var editTextExercise: EditText
    private lateinit var btnSave: Button
    private lateinit var textViewExercises: TextView

    private val db = FirebaseFirestore.getInstance()
    private var selectedDate: String = getFormattedDate(System.currentTimeMillis()) // Förvalt till dagens datum

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = view.findViewById(R.id.calendarView)
        editTextExercise = view.findViewById(R.id.editTextExercise)
        btnSave = view.findViewById(R.id.btnSave)
        textViewExercises = view.findViewById(R.id.textViewExercises)

        loadExercise(selectedDate) // Laddar dagens övning

        // Lyssnar på datumändring
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, 0, 0, 0)
            selectedDate = getFormattedDate(calendar.timeInMillis)

            loadExercise(selectedDate) // Ladda övning för valt datum
        }

        // Sparar övningen i Firestore
        btnSave.setOnClickListener {
            val exercise = editTextExercise.text.toString()
            if (exercise.isNotEmpty()) {
                saveExercise(selectedDate, exercise)
            } else {
                Toast.makeText(requireContext(), "Skriv in en övning först", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getFormattedDate(timeInMillis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date(timeInMillis))
    }

    private fun saveExercise(date: String, exercise: String) {
        val exerciseData = hashMapOf("exercise" to exercise)

        db.collection("exercises").document(date)
            .set(exerciseData)
            .addOnSuccessListener {
                textViewExercises.text = "Dagens övning: $exercise"
                editTextExercise.text.clear()
                Toast.makeText(requireContext(), "Övning sparad!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Misslyckades att spara", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadExercise(date: String) {
        db.collection("exercises").document(date)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val exercise = document.getString("exercise") ?: ""
                    textViewExercises.text = "Dagens övning: $exercise"
                    editTextExercise.setText(exercise)
                } else {
                    textViewExercises.text = "Dagens övning: Ingen än"
                    editTextExercise.setText("")
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Misslyckades att ladda övning", Toast.LENGTH_SHORT).show()
            }
    }
}
