package calender

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
import java.util.Calendar
import com.example.homefit.R


class CalenderFragment : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var editTextExercise: EditText
    private lateinit var btnSave: Button
    private lateinit var textViewExercises: TextView

    private val exerciseMap = mutableMapOf<Long, String>() // Sparar övningar per datum
    private var selectedDate: Long = System.currentTimeMillis() // Förvalt till dagens datum

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

        // Lyssnar på datumändring
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis

            editTextExercise.setText(exerciseMap[selectedDate] ?: "")
            textViewExercises.text = "Dagens övning: ${exerciseMap[selectedDate] ?: "Ingen än"}"
        }

        // Sparar övningen
        btnSave.setOnClickListener {
            val exercise = editTextExercise.text.toString()
            if (exercise.isNotEmpty()) {
                exerciseMap[selectedDate] = exercise
                textViewExercises.text = "Dagens övning: $exercise"
                editTextExercise.text.clear()
                Toast.makeText(requireContext(), "Övning sparad!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Skriv in en övning först", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
