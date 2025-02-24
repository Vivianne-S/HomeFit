package com.example.homefit.ui.workout

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homefit.R
import com.example.homefit.databinding.FragmentWorkoutBinding
import com.example.homefit.ui.data.WorkoutData

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    val args : WorkoutFragmentArgs by navArgs()


    companion object {
        fun newInstance() = WorkoutFragment()
    }

    // ViewModel-instans
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        
        val imgView = binding.imageViewWorkout
        val wrkDescription = binding.workoutDescription
        val wrkName = binding.workoutName
        val workoutNr = args.Workout


        if(workoutNr == 1){
            imgView.setImageResource(R.drawable.dips2)
            wrkName.text = "Chair Dips"
            wrkDescription.text = "1.Sit on the edge of a bench or chair, hands supporting your weight. \n" +
                    "2.Position your feet away from the bench, legs straight and heels on the floor. \n" +
                    "3.Lower yourself until your upper arms are parallel to the ground, then push back up.\n" +
                    "\nContinue for the desired number of reps or time"

        }else if (workoutNr == 2){
            imgView.setImageResource(R.drawable.armcirlces1)
            wrkName.text = "Arm Circles"
            wrkDescription.text = "1.Stand up straight, with your feet shoulder-width apart and your hands out and parallel to the floor.\n" +
                    "2.Make small circles using your whole arm, being sure to keep your back straight.\n" +
                    "3.Start making larger circles with your arm, keeping your movement controlled.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 3){
            imgView.setImageResource(R.drawable.chatauranga2)
            wrkName.text = "Chaturanga"
            wrkDescription.text = "1.Start in a high plank with shoulders over wrists, hips aligned, and legs straight. Inhale, shift forward on toes, and engage your core. \n" +
                    "2.Lower down by bending elbows to 90°, keeping chest and hips aligned. \n" +
                    "3.Exhale, press palms to lift chest and hips, straightening your arms.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 4){
            imgView.setImageResource(R.drawable.wallangel2)
            wrkName.text = "Wall Angels"
            wrkDescription.text = "1.Stand tall with your back against a wall and feet slightly forward. \n" +
                    "2.Place the backs of your hands and arms against the wall at head height. \n" +
                    "3.Slide your arms up, then back down to the starting position.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 5){
            imgView.setImageResource(R.drawable.armlateralraises2)
            wrkName.text = "Arm Lateral Raises"
            wrkDescription.text = "1.Stand tall with your feet shoulder-width apart, knees slightly bent. Let your arms hang naturally by your sides with your palms facing your body.\n" +
                    "2.Engage your core muscles and maintain a slight bend in your elbows throughout the exercise.\n" +
                    "3.Keeping your back straight and your shoulders relaxed, lift both arms out to the sides until they are parallel to the floor.\n" +
                    "4.Focus on using your shoulder muscles to perform the movement, rather than relying on momentum or swinging your body.\n" +
                    "5.Pause for a moment at the top of the movement, feeling the contraction in your shoulder muscles.\n" +
                    "6.Inhale and slowly lower your arms back down to the starting position, controlling the descent.\n" +
                    "\nContinue for the desired number of repetitions."
        }else if(workoutNr == 6){
            imgView.setImageResource(R.drawable.squat)
            wrkName.text = "Squats"
            wrkDescription.text = "1.Start by standing with your feet just about shoulder width\n" +
                    "2.Begin by pushing your hips back while bending at the knee, as if you were about to sit down.\n" +
                    "3.Squat down until your thighs are at least parallel with the floor, or below if you can.\n" +
                    "4.Return to standing by pushing the ground away through your feet, " +
                    "making sure to push through both the whole foot and not just the heels.\n" +
                    "\nContinue for the desired number of reps or time"
        }else if (workoutNr == 7){
            imgView.setImageResource(R.drawable.splitsquats2)
            wrkName.text = "Split Squats"
            wrkDescription.text = "1.Stand with your feet hip-width apart and take a big step forward with your right foot.\n" +
                    "2.Lower your body down until your right thigh is parallel to the ground and your left knee is hovering just above the floor.\n" +
                    "3.Push through your right heel to stand back up to the starting position.\n" +
                    "4.Repeat on the other side by taking a big step forward with your left foot.\n" +
                    "\nContinue alternating legs for the desired number of reps or time."
        }else if (workoutNr == 8){
            imgView.setImageResource(R.drawable.glutebridge2)
            wrkName.text = "Glute Bridge"
            wrkDescription.text = "1.Start by lying on your back on a mat or bench with your knees bent and feet flat on the ground.\n" +
                    "2.Engage your glutes and lift your hips up towards the ceiling, keeping your feet and shoulders on the ground.\n" +
                    "3.Pause at the top of the movement, squeezing your glutes tightly.\n" +
                    "4.Lower your hips back down to the starting position, but do not let the barbell touch the ground.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 9){
            imgView.setImageResource(R.drawable.sidelunge)
            wrkName.text = "Side Lunge"
            wrkDescription.text = "1.Stand with your feet hip-width apart and your hands on your hips.\n" +
                    "2.Take a big step to the right with your right foot, keeping your left foot in place.\n" +
                    "3.Bend your right knee and push your hips back as you lower your body into a lunge position.\n" +
                    "4.Make sure your right knee is directly above your ankle and your left leg is straight.\n" +
                    "5.Push off with your right foot and return to the starting position.\n" +
                    "6.Repeat on the other side by stepping to the left with your left foot.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 10){
            imgView.setImageResource(R.drawable.calfraises2)
            wrkName.text = "Calf Raises"
            wrkDescription.text = "1.Stand on an flat or elevated surface such as a step with your heels hanging off.\n" +
                    "2.Hold onto a stable object such as a railing or wall for balance.\n" +
                    "3.Raise your heels up as high as possible, squeezing your calf muscles at the top of the movement.\n" +
                    "4.Lower your heels back down below the level of the elevated surface, feeling a stretch in your calves.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 11){
            imgView.setImageResource(R.drawable.pushups2)
            wrkName.text = "Push-Ups"
            wrkDescription.text = "1.Start in a high plank position. Place your hands on the ground and position them shoulder width apart (or slightly wider).\n" +
                    "2.With control, bend your elbows to lower your entire body toward the ground.\n" +
                    "3.Lower as far as you can while keeping your body in one straight line.\n" +
                    "4.Pause at the bottom.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 12){
            imgView.setImageResource(R.drawable.widepushups2)
            wrkName.text = "Wide Push-Ups"
            wrkDescription.text ="1.Start in plank position with your hands wider than your shoulders. \n" +
                    "2.Face your fingers forward or slightly to the outside. \n" +
                    "3.Slowly bend your elbows out to the side as you lower your body toward the floor. \n" +
                    "4.Pause when your chest is just below your elbows.\n" +
                    "5.Push up to the starting position.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 13){
            imgView.setImageResource(R.drawable.burpees2)
            wrkName.text = "Burpees"
            wrkDescription.text = "1.Begin in a standing position. Position your feet shoulder-width apart.\n" +
                    "2.Drop into a squat.\n" +
                    "3.Kick your legs back into a high plank position.\n" +
                    "4.Lower toward the ground.\n" +
                    "5.Rise back to high plank.\n" +
                    "6.Return to a squat position.\n" +
                    "7.Jump!\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 14){
            imgView.setImageResource(R.drawable.inclinepushup2)
            wrkName.text = "Incline Push-Ups"
            wrkDescription.text = "1.Perform the incline push-up with your hands on a bench, countertop, stability ball, or other elevated surface. \n" +
                    "2.Hold your body in a high plank position, keeping your back and legs in a straight line. \n" +
                    "3.Bend your elbows, and lower your body with the same movement pattern you would use for a traditional push-up." +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 15){
            imgView.setImageResource(R.drawable.declinepushup2)
            wrkName.text = "Decline Push-Ups"
            wrkDescription.text = "1.Kneel down with your back to the bench. Put your hands on the floor, shoulders over your wrists and elbows at 45 degrees. Place your feet on top of the bench.\n" +
                    "2.Brace your core, glutes, and quads. Bend your elbows and lower your chest to the floor, keeping your back and neck straight.\n" +
                    "3.Push into the floor to return to starting position, extending your elbows.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 16){
            imgView.setImageResource(R.drawable.superman2)
            wrkName.text = "Superman"
            wrkDescription.text = "1.Lie on the floor face down, with your legs straight and your arms extended in front of you.\n" +
                    "2.Keeping your head in a neutral position, slowly lift your arms and legs off the floor until you feel your lower back muscles contracting. \n" +
                    "3.Aim to lift your belly button slightly off the floor to contract your abs. A good way to picture this is to imagine you’re Superman flying in the air.\n" +
                    "4.Hold this position for 2–3 seconds. breathing the entire time.\n" +
                    "5.Lower your arms, legs, and belly back to the floor. \n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 17){
            imgView.setImageResource(R.drawable.goodmorning2)
            wrkName.text = "Good Morning"
            wrkDescription.text = "1.With feet hip-width apart, stand upright, knees slightly bent, and place your hands at the back of your head, elbows opened wide.\n" +
                    "2.Engage your abdominal muscles by pulling them into your spine. Keeping your spine neutral and pressing your rear backward, bend forward at the hips and continue doing so until your back is nearly parallel to the floor.\n" +
                    "3.Slowly return to standing, engaging your core and squeezing your glutes at the top of the movement. \n" +
                    "\nContinue for the desired number of reps or time"
        }else if (workoutNr == 18){
            imgView.setImageResource(R.drawable.reverseplank)
            wrkName.text = "Reverse Plank"
            wrkDescription.text = "1.Start by sitting on the floor with your legs extended in front of you.\n" +
                    "2.Place your hands on the floor behind you, with your fingers pointing towards your feet.\n" +
                    "3.Press into your hands and lift your hips off the ground, coming into a reverse tabletop position.\n" +
                    "4.Extend your legs out straight and engage your core to lift your hips higher.\n" +
                    "5.Hold the pose while breathing deeply.\n" +
                    "6.To release, lower your hips back down to the ground and come back into a seated position.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 19){
            imgView.setImageResource(R.drawable.cat)
            wrkName.text = "Cat Cow"
            wrkDescription.text = "1.Start on your hands and knees with your wrists directly under your shoulders and your knees directly under your hips.\n" +
                    "2.Inhale and arch your back, lifting your head and tailbone towards the ceiling. This is the “cat” position.\n" +
                    "3.Exhale and round your spine, tucking your chin to your chest and bringing your tailbone towards your knees. This is the “cow” position.\n" +
                    "4.Repeat the cat and cow positions for several breaths, moving smoothly and slowly between the two positions.\n" +
                    "5.Focus on the movement of your spine and the stretch in your back.\n" +
                    "6.Finish by returning to a neutral spine position.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 20){
            imgView.setImageResource(R.drawable.reverseflys2)
            wrkName.text = "Reverse Flys"
            wrkDescription.text = "1.Stand with your legs shoulder-width apart.\n" +
                    "2.Place your arms just next to your sides.\n" +
                    "3.Bend your body at the waist until your upper body gets parallel to the ground.\n" +
                    "4.Slowly lift your arms straight to each side to shoulder height, with elbows slightly bent.\n" +
                    "5.Slowly get back to the starting position.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 21){
            imgView.setImageResource(R.drawable.plank)
            wrkName.text = "Plank"
            wrkDescription.text = "1.Push into your forearms to lift your body, forming a straight line from head to feet. \n" +
                    "2.Start facedown, complete the movement, and repeat.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 22){
            imgView.setImageResource(R.drawable.crunches2)
            wrkName.text = "Crunches"
            wrkDescription.text = "1.Lie on your back with bent legs and a stable lower body. \n" +
                    "2.Cross your hands to your shoulders or place them behind your ears. \n" +
                    "3.Lift your head and shoulders, then lower back down to the start.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 23){
            imgView.setImageResource(R.drawable.bcrunches2)
            wrkName.text = "Bicycle Crunches"
            wrkDescription.text = "1.Engage your core, hold your head gently, and lift your knees to a 90° angle. \n" +
                    "2.Exhale and pedal your legs, bringing one knee toward your armpit while straightening the other. \n" +
                    "3.Twist your torso to touch your elbow to the opposite knee. \n" +
                    "\nContinue alternating sides for the desired number of reps or time."
        }else if (workoutNr == 24){
            imgView.setImageResource(R.drawable.legraises2)
            wrkName.text = "Leg Raises"
            wrkDescription.text = "1.Lie flat on your back on a mat with legs extended and arms by your sides, hands pressed into the floor or holding a fixed object. \n" +
                    "2.Engage your core by pulling your belly button in and exhale as you lift your legs to 90° from the floor. Stop when your lower back begins to lift.\n" +
                    "3.Then inhale as you lower your legs while keeping your lower back on the ground and feet hovering above the floor. Maintain core engagement throughout.\n" +
                    "\nContinue for the desired number of reps or time."
        }else if (workoutNr == 25){
            imgView.setImageResource(R.drawable.heeltap1)
            wrkName.text = "Heel Taps"
            wrkDescription.text = "1.Engage your abs and obliques as you inhale and lift your shoulder blades and head. \n" +
                    "2.Bend to the right, reaching your right hand to tap your right heel while exhaling. Keep the movement controlled, tuck your chin, and engage your torso, using your core instead of your neck. \n" +
                    "3.Squeeze your glutes to maintain a horizontal lower body. \n" +
                    "4.Return to the starting position, pause, then repeat on the left side, tapping your left heel with your left hand to complete one rep.\n" +
                    "\nContinue for the desired number of reps or time."
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiera ViewModel
        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        // Observera favoriteStatus LiveData för att uppdatera UI
        workoutViewModel.favoriteStatus.observe(viewLifecycleOwner) { status ->
            Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
        }

        // Hämta träningsnamn och beskrivning från användargränssnittet
        val workoutName = binding.workoutName.text.toString()
        val workoutDescription = binding.workoutDescription.text.toString()

        // Sätt upp en click listener för favorit-knappen
        binding.imageButtonFavorite.setOnClickListener {
            // Spara övningen som favorit (utan bild)
            workoutViewModel.saveFavorite(WorkoutData(workoutName, workoutDescription, 0, ""))

            // Ändra hjärtikonen till röd när knappen trycks
            binding.imageButtonFavorite.setImageResource(R.drawable.baseline_favorite_24)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
