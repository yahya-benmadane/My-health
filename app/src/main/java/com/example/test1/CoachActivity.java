package com.example.test1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CoachActivity extends AppCompatActivity {

    private void setupCoach(int imageId, String name, String email, String phone, String insta, String fb, String price,
                            int imageViewId, int nameViewId, int emailViewId, int phoneViewId, int instaViewId, int fbViewId,
                            int priceViewId, int btnViewId) {

        ((ImageView) findViewById(imageViewId)).setImageResource(imageId);
        ((TextView) findViewById(nameViewId)).setText(name);
        ((TextView) findViewById(emailViewId)).setText(email);
        ((TextView) findViewById(phoneViewId)).setText(phone);
        ((TextView) findViewById(instaViewId)).setText(insta);
        ((TextView) findViewById(fbViewId)).setText(fb);
        ((TextView) findViewById(priceViewId)).setText("Prix : " + price);

        // Email icon click
        findViewById(emailViewId).setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Coaching personnalisé");
            startActivity(Intent.createChooser(emailIntent, "Envoyer un email"));
        });

        // Phone icon click
        findViewById(phoneViewId).setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });

        // Instagram icon click
        findViewById(instaViewId).setOnClickListener(v -> openLink(insta));

        // Facebook icon click
        findViewById(fbViewId).setOnClickListener(v -> openLink(fb));

        // Contact button
        ((Button) findViewById(btnViewId)).setOnClickListener(v -> {
            // Vous pouvez choisir quelle action déclencher ici
            // Par exemple, composer le numéro de téléphone :
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });
    }

    private void openLink(String url) {
        if (!url.startsWith("http")) {
            url = "https://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_activity);

        // Ajout du bouton de retour
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Crée une nouvelle intention pour revenir à ExerciseActivity
            Intent intent = new Intent(CoachActivity.this, ExerciseActivity.class);
            startActivity(intent);
            // Optionnellement, vous pouvez aussi ajouter cette ligne pour finir CoachActivity afin de ne pas revenir en arrière à cette activité
            finish();
        });

        // Coach 1 - Adib
        setupCoach(
                R.drawable.adib,
                "Coach Adib",
                "adibsebbata@email.com",
                "+212612345678", // Numéro de téléphone ajouté
                "https://www.instagram.com/adib.seb/",
                "https://www.facebook.com/adib.sebbata?locale=fr_FR",
                "300 MAD / mois",
                R.id.coach1Img, R.id.coach1Name, R.id.coach1Email,
                R.id.coach1Phone, R.id.coach1Insta, R.id.coach1Fb,
                R.id.coach1Price, R.id.contactCoach1Btn
        );

        // Coach 2 - Anass
        setupCoach(
                R.drawable.anass,
                "Coach Anass",
                "Anassessayah85@gmail.com",
                "+212687654321", // Numéro de téléphone ajouté
                "https://www.instagram.com/anasss_syh/",
                "https://www.facebook.com/anass.el.395891?locale=fr_FR",
                "250 MAD / mois",
                R.id.coach2Img, R.id.coach2Name, R.id.coach2Email,
                R.id.coach2Phone, R.id.coach2Insta, R.id.coach2Fb,
                R.id.coach2Price, R.id.contactCoach2Btn
        );
    }
}