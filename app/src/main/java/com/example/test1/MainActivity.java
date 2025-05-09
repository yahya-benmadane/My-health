package com.example.test1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private VideoView backgroundVideo;
    private Button btnGoToCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des vues
        backgroundVideo = findViewById(R.id.backgroundVideo);
        btnGoToCalculator = findViewById(R.id.btnGoToCalculator);

        // Configuration de la vidéo d'arrière-plan
        setupBackgroundVideo();

        // Gestion du clic sur le bouton
        btnGoToCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CalorieCalculatorActivity.class));
            }
        });
    }

    private void setupBackgroundVideo() {
        try {
            // Chemin vers la vidéo dans les ressources raw
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background_video);

            backgroundVideo.setVideoURI(videoUri);
            backgroundVideo.setZOrderOnTop(false); // Important pour le positionnement

            backgroundVideo.setOnPreparedListener(mp -> {
                mp.setLooping(true);
                mp.setVolume(0, 0); // Désactive le son

                // Ajustement de la luminosité (optionnel)
                View darkOverlay = findViewById(R.id.darkOverlay);
                darkOverlay.setAlpha(0.4f); // 40% d'opacité

                mp.start();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (backgroundVideo != null) {
            backgroundVideo.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backgroundVideo != null) {
            backgroundVideo.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundVideo != null) {
            backgroundVideo.stopPlayback();
        }
    }
}