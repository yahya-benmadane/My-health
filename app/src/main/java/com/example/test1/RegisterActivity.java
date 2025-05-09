package com.example.test1;  // Assure-toi que c'est le bon package

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Importation de DatabaseHelper
import com.example.test1.DatabaseHelper;  // Ajoute cette ligne si nécessaire

public class RegisterActivity extends AppCompatActivity {

    EditText username, password;
    Button btnRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Initialisation des vues
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        db = new DatabaseHelper(this);  // Ici, db est maintenant correctement initialisé

        // Logique pour l'inscription
        btnRegister.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            // Vérifier si l'utilisateur existe déjà
            if (db.isUserExist(user)) {
                Toast.makeText(this, "L'utilisateur existe déjà", Toast.LENGTH_SHORT).show();
            } else {
                // Enregistrer l'utilisateur dans la base de données
                if (db.registerUser(user, pass)) {
                    Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                    // Rediriger vers la page de login
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Toast.makeText(this, "Erreur d'inscription", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Lien vers la page de connexion
        findViewById(R.id.linkLogin).setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }
}
