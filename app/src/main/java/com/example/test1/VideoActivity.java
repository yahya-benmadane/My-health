package com.example.test1;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        exitButton = findViewById(R.id.exitButton);

        // Récupérer la vidéo passée par l’intent
        String videoUri = getIntent().getStringExtra("VIDEO_RES_ID");

        if (videoUri != null) {
            Uri uri = Uri.parse(videoUri);
            videoView.setVideoURI(uri);

            // Contrôles de lecture
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            videoView.requestFocus();
            videoView.start();
        }

        // Quitter l’activité quand on clique sur le bouton
        exitButton.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.stopPlayback();
            }
            finish(); // Ferme l’activité
        });
    }
}
