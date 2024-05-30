package com.example.gfattendance;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class LoginManager {
    private Context context;

    // Constructeur pour initialiser le contexte
    public LoginManager(Context context) {
        this.context = context;
    }

    /**
     * Méthode pour valider les informations de connexion
     *
     * @param email    L'email entré par l'utilisateur
     * @param password Le mot de passe entré par l'utilisateur
     * @return true si les informations de connexion sont valides, false sinon
     */
    public boolean validateLogin(String email, String password) {
        try {
            // Lire le fichier JSON depuis le dossier assets
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("users.json");

            // Lire le contenu du fichier dans un buffer
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Convertir le buffer en chaîne de caractères
            String json = new String(buffer, "UTF-8");

            // Analyser les données JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONArray usersArray = jsonObject.getJSONArray("users");

            // Rechercher la combinaison email/mot de passe
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                String storedEmail = userObject.getString("email");
                String storedPassword = userObject.getString("password");

                // Vérifier si les informations de connexion sont valides
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    return true; // Informations de connexion valides trouvées
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return false; // Informations de connexion invalides ou erreur survenue
    }
}
