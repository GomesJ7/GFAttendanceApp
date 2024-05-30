package com.example.gfattendance;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe PasswordRecoveryManager gérant la récupération des mots de passe utilisateur
 * à partir d'un fichier JSON stocké dans les assets de l'application.
 */
public class PasswordRecoveryManager {
    private Context context;  // Contexte de l'application
    private JSONObject userData;  // Données des utilisateurs

    /**
     * Constructeur initialisant le contexte et chargeant les données utilisateurs.
     * @param context Contexte de l'application
     */
    public PasswordRecoveryManager(Context context) {
        this.context = context;
        loadUserData();  // Chargement des données utilisateurs
    }

    /**
     * Méthode privée pour charger les données utilisateurs à partir du fichier JSON.
     */
    private void loadUserData() {
        try {
            // Ouverture du fichier logininfo.json dans les assets
            InputStream inputStream = context.getAssets().open("logininfo.json");
            int size = inputStream.available();  // Taille du fichier
            byte[] buffer = new byte[size];  // Buffer pour lire les données
            inputStream.read(buffer);  // Lecture des données dans le buffer
            inputStream.close();  // Fermeture de l'InputStream

            // Conversion du buffer en String
            String json = new String(buffer, "UTF-8");

            // Création de l'objet JSON à partir de la String
            userData = new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();  // Affichage de la pile d'exception en cas d'erreur
        }
    }

    /**
     * Méthode publique pour récupérer le mot de passe d'un utilisateur à partir de son email.
     * @param email Email de l'utilisateur dont le mot de passe doit être récupéré.
     */
    public void recoverPassword(String email) {
        try {
            if (userData != null) {
                // Récupération du tableau JSON des utilisateurs
                JSONArray usersArray = userData.getJSONArray("users");

                // Parcours de tous les utilisateurs
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject user = usersArray.getJSONObject(i);

                    // Vérification si l'email correspond
                    if (email.equals(user.getString("email"))) {
                        // Envoi de l'email de récupération de mot de passe ou autres actions nécessaires
                        Toast.makeText(context, "Password recovery email sent to " + email, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // Si l'email n'est pas trouvé dans les données JSON
                Toast.makeText(context, "Email not registered", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();  // Affichage de la pile d'exception en cas d'erreur
        }
    }
}
