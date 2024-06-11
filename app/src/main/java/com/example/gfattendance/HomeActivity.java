package com.example.gfattendance;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Cette classe représente l'activité principale de l'application
public class HomeActivity extends AppCompatActivity {

    // Déclaration des variables
    private TextView textViewName;
    private String token;

    // Méthode appelée lors de la création de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialisation des variables
        textViewName = findViewById(R.id.textViewName);

        // Récupération du token depuis l'intent
        Intent intent = getIntent();
        token = intent.getStringExtra("TOKEN");

        // Si le token est présent, on lance une tâche asynchrone pour récupérer le profil de l'utilisateur
        if (token != null) {
            new FetchProfileTask(token).execute();
        } else {
            // Sinon, on affiche un message d'erreur
            Toast.makeText(this, "Token is missing", Toast.LENGTH_SHORT).show();
        }
    }

    // Classe interne représentant la tâche asynchrone pour récupérer le profil de l'utilisateur
    private class FetchProfileTask extends AsyncTask<Void, Void, String> {
        private String token;

        // Constructeur de la classe
        FetchProfileTask(String token) {
            this.token = token;
        }

        // Méthode exécutée en arrière-plan
        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Création de l'URL et de la connexion
                URL url = new URL("http://10.0.2.2:8000/api/profil");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + token);

                // Récupération du code de réponse
                int responseCode = connection.getResponseCode();
                Log.d("FetchProfileTask", "Response Code: " + responseCode);

                // Si le code de réponse est OK, on lit la réponse
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    Log.d("FetchProfileTask", "Response: " + response.toString());
                    return response.toString();
                } else {
                    // Sinon, on log l'erreur et on retourne null
                    Log.e("FetchProfileTask", "Error Response Code: " + responseCode);
                    return null;
                }
            } catch (Exception e) {
                // En cas d'exception, on log l'erreur et on retourne null
                Log.e("FetchProfileTask", "Exception: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        // Méthode appelée après l'exécution de la tâche en arrière-plan
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    // On parse la réponse en JSON
                    JSONObject jsonResponse = new JSONObject(result);
                    String nom = jsonResponse.getString("nom");
                    String prenom = jsonResponse.getString("prenom");
                    String fullName = prenom + " " + nom;
                    // On affiche le nom complet de l'utilisateur
                    textViewName.setText(fullName);
                } catch (JSONException e) {
                    // En cas d'erreur de parsing, on affiche un message d'erreur
                    Toast.makeText(HomeActivity.this, "JSON parsing error", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Si la réponse est null, on affiche un message d'erreur
                Toast.makeText(HomeActivity.this, "Failed to fetch profile", Toast.LENGTH_SHORT).show();
            }
        }
    }
}