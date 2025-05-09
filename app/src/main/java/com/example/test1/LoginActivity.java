package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;
    TextView errorMessage;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Initialisation des vues
        username = findViewById(R.id.usernameLogin);
        password = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        errorMessage = findViewById(R.id.error_message);
        db = new DatabaseHelper(this);

        // Écouteur de clic pour le bouton de connexion
        btnLogin.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            // Masquer le message d'erreur au début de chaque tentative de connexion
            errorMessage.setVisibility(View.GONE);

            // Validation des champs
            if (user.isEmpty() || pass.isEmpty()) {
                errorMessage.setText("Tous les champs sont obligatoires.");
                errorMessage.setVisibility(View.VISIBLE);
                return;
            }

            // Vérification des identifiants de l'utilisateur
            if (db.loginUser(user, pass)) {
                // Connexion réussie
                Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();  // Empêche l'utilisateur de revenir à la page de connexion en appuyant sur "Retour"
            } else {
                // Connexion échouée
                errorMessage.setText("Identifiants incorrects. Veuillez réessayer.");
                errorMessage.setVisibility(View.VISIBLE);
            }
        });

        // Lien vers la page d'inscription
        findViewById(R.id.linkRegister).setOnClickListener(v -> {
            // Redirection vers la page d'inscription
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
