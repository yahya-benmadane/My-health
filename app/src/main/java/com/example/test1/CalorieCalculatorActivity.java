package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CalorieCalculatorActivity extends AppCompatActivity {

    EditText ageInput, heightInput, weightInput;
    RadioGroup genderGroup;
    Spinner activitySpinner;
    TextView resultView;
    TextView carbsText, proteinText, fatText;
    LinearLayout macrosLayout;
    Button calculateBtn, btnExercises;  // Déclaration du bouton btnExercises

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calorie_activity);

        // Initialisation des vues
        ageInput = findViewById(R.id.ageInput);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        genderGroup = findViewById(R.id.genderGroup);
        activitySpinner = findViewById(R.id.activityLevel);
        resultView = findViewById(R.id.resultView);
        carbsText = findViewById(R.id.carbsText);
        proteinText = findViewById(R.id.proteinText);
        fatText = findViewById(R.id.fatText);
        macrosLayout = findViewById(R.id.macrosLayout);
        calculateBtn = findViewById(R.id.btnCalculate);
        btnExercises = findViewById(R.id.btnExercises); // Initialisation du bouton btnExercises

        // Adapter pour le Spinner
        ArrayAdapter<CharSequence> activityAdapter = ArrayAdapter.createFromResource(
                this, R.array.activity_array, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activitySpinner.setAdapter(activityAdapter);

        // Bouton de calcul
        calculateBtn.setOnClickListener(v -> {
            Log.d("CALCUL", "Bouton cliqué");
            calculateCalories();
        });

        // Gestion du bouton btnExercises pour accéder à la nouvelle activité
        btnExercises.setOnClickListener(v -> {
            Intent intent = new Intent(CalorieCalculatorActivity.this, ExerciseActivity.class);
            startActivity(intent);
        });
    }

    private void calculateCalories() {
        // Vérification des champs obligatoires
        if (ageInput.getText().toString().isEmpty() ||
                heightInput.getText().toString().isEmpty() ||
                weightInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedGenderId = genderGroup.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            Toast.makeText(this, "Veuillez sélectionner votre sexe", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Récupération des entrées
            int age = Integer.parseInt(ageInput.getText().toString());
            float height = Float.parseFloat(heightInput.getText().toString());
            float weight = Float.parseFloat(weightInput.getText().toString());

            String gender = (selectedGenderId == R.id.male) ? "Homme" : "Femme";
            String activity = activitySpinner.getSelectedItem().toString();

            // Calcul du BMR
            double bmr = (gender.equals("Homme")) ?
                    10 * weight + 6.25 * height - 5 * age + 5 :
                    10 * weight + 6.25 * height - 5 * age - 161;

            // Multiplicateur d'activité
            double multiplier;
            switch (activity) {
                case "Légère":
                    multiplier = 1.375;
                    break;
                case "Modérée":
                    multiplier = 1.55;
                    break;
                case "Intense":
                    multiplier = 1.725;
                    break;
                default:
                    multiplier = 1.2;
            }

            // Calcul des résultats
            double calories = bmr * multiplier;
            int carbs = (int) (calories * 0.5 / 4);    // 50% glucides
            int protein = (int) (weight * 1.8);  // 30% protéines
            int fat = (int) (calories * 0.2 / 9);      // 20% lipides

            // Affichage des résultats
            resultView.setText(String.format("Calories journalières : %.0f kcal", calories));
            carbsText.setText("Glucides : " + carbs + " g");
            proteinText.setText("Protéines : " + protein + " g");
            fatText.setText("Lipides : " + fat + " g");

            resultView.setVisibility(View.VISIBLE);
            macrosLayout.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer des valeurs numériques valides", Toast.LENGTH_SHORT).show();
        }
    }
}
