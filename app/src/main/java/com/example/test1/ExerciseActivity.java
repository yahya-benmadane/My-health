package com.example.test1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private Spinner muscleSpinner;
    private TextView exercisesText;
    private Button watchVideoBtn;
    private Button contactCoachBtn;
    private ProgressBar loadingProgressBar;
    private List<MuscleItem> muscleItems;
    private ImageView proteinImage, creatineImage, shakeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_activity);

        // Initialisation des vues
        muscleSpinner = findViewById(R.id.muscleSpinner);
        exercisesText = findViewById(R.id.exercisesText);
        watchVideoBtn = findViewById(R.id.watchVideoBtn);
        contactCoachBtn = findViewById(R.id.contactCoachBtn);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        // Initialisation des images produits
        proteinImage = findViewById(R.id.proteinImage);
        creatineImage = findViewById(R.id.creatineImage);
        shakeImage = findViewById(R.id.shakeImage);

        // Configuration des clics sur les produits
        setupProductClicks();
        ImageView goToCaloriesBtn = findViewById(R.id.goToCaloriesBtn);
        goToCaloriesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseActivity.this, CalorieCalculatorActivity.class);
            startActivity(intent);
        });

        // Action bouton "Contact Coach"
        contactCoachBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, CoachActivity.class);
            startActivity(intent);
        });

        // Préparer les données du spinner
        setupMuscleSpinner();

        // Action du bouton "voir la vidéo"
        watchVideoBtn.setOnClickListener(v -> {
            MuscleItem selectedItem = (MuscleItem) muscleSpinner.getSelectedItem();
            playVideoForMuscle(selectedItem.getName());
        });
    }

    private void setupProductClicks() {
        // URLs des produits (à remplacer par vos liens réels)
        String proteinUrl = "https://www.prodietnutrition.ma/fr/proteines/1389-isowhey-100-whey-protein-isolate-227kg-muscletech.html";
        String creatineUrl = "https://www.optigura.fr/product/100-creatine-monohydrate";
        String shakeUrl = "https://uk.ultimateperformance.com/products/up-basic-shaker-bottle-black";

        proteinImage.setOnClickListener(v -> openProductWebsite(proteinUrl));
        creatineImage.setOnClickListener(v -> openProductWebsite(creatineUrl));
        shakeImage.setOnClickListener(v -> openProductWebsite(shakeUrl));
    }

    private void openProductWebsite(String url) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception e) {
            showSnackbar("Impossible d'ouvrir le lien vers le produit");
        }
    }

    private void setupMuscleSpinner() {
        muscleItems = new ArrayList<>();
        muscleItems.add(new MuscleItem("Poitrine", R.drawable.ic_poitrine));
        muscleItems.add(new MuscleItem("Dos", R.drawable.ic_dos));
        muscleItems.add(new MuscleItem("Jambes", R.drawable.ic_jambes));
        muscleItems.add(new MuscleItem("Épaules", R.drawable.ic_epaules));
        muscleItems.add(new MuscleItem("Biceps", R.drawable.ic_biceps));
        muscleItems.add(new MuscleItem("Triceps", R.drawable.ic_triceps));

        MuscleSpinnerAdapter adapter = new MuscleSpinnerAdapter(this, muscleItems);
        muscleSpinner.setAdapter(adapter);

        muscleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MuscleItem selectedMuscle = muscleItems.get(position);
                showLoading(true);
                new android.os.Handler().postDelayed(() -> {
                    updateExercises(selectedMuscle.getName());
                    showLoading(false);
                }, 500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                exercisesText.setText("Sélectionnez un groupe musculaire");
            }
        });
    }

    private void updateExercises(String muscle) {
        String exercises;
        switch (muscle) {
            case "Poitrine":
                exercises = "✓ Développé couché (4x8-12)\n✓ Écarté couché (3x10-12)\n✓ Pompes (4x max)\n✓ Développé incliné (4x8-12)";
                break;
            case "Dos":
                exercises = "✓ Tractions (4x max)\n✓ Rowing barre (4x8-12)\n✓ Tirage horizontal (3x10-12)\n✓ Soulevé de terre (4x6-8)";
                break;
            case "Jambes":
                exercises = "✓ Squats (4x8-10)\n✓ Fentes (3x10/jambe)\n✓ Presse à jambes (4x10-12)\n✓ Leg curl (3x12-15)";
                break;
            case "Épaules":
                exercises = "✓ Développé militaire (4x8-10)\n✓ Élévations latérales (3x12-15)\n✓ Oiseau (3x12-15)\n✓ Face pull (3x12-15)";
                break;
            case "Biceps":
                exercises = "✓ Curl haltères (4x10-12)\n✓ Curl barre (4x8-12)\n✓ Curl pupitre (3x10-12)\n✓ Curl marteau (3x12-15)";
                break;
            case "Triceps":
                exercises = "✓ Dips (4x max)\n✓ Extensions poulie (3x12-15)\n✓ Barre au front (4x8-10)\n✓ Kickback (3x12-15)";
                break;
            default:
                exercises = "Sélectionnez un groupe musculaire valide";
        }
        exercisesText.setText(exercises);
    }

    private void playVideoForMuscle(String muscle) {
        String videoPath = getLocalVideoPathForMuscle(muscle);

        if (videoPath != null) {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra("VIDEO_RES_ID", videoPath);
            startActivity(intent);
        } else {
            showSnackbar("Aucune vidéo disponible pour ce muscle");
        }
    }

    private String getLocalVideoPathForMuscle(String muscle) {
        int resId = -1;
        switch (muscle) {
            case "Poitrine": resId = R.raw.poitrine_video; break;
            case "Dos": resId = R.raw.dos_video; break;
            case "Jambes": resId = R.raw.jambes_video; break;
            case "Épaules": resId = R.raw.epaules_video; break;
            case "Biceps": resId = R.raw.biceps_video; break;
            case "Triceps": resId = R.raw.triceps_video; break;
        }
        return resId != -1 ? "android.resource://" + getPackageName() + "/" + resId : null;
    }

    private void showLoading(boolean show) {
        loadingProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        muscleSpinner.setEnabled(!show);
        watchVideoBtn.setEnabled(!show);
        proteinImage.setEnabled(!show);
        creatineImage.setEnabled(!show);
        shakeImage.setEnabled(!show);
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(getResources().getColor(R.color.snackbar_background))
                .setTextColor(getResources().getColor(R.color.white))
                .show();
    }
}