package com.example.gfattendance;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

// Cette classe gère le processus de connexion
public class LoginManager {
    private Context context;

    // Constructeur de la classe
    public LoginManager(Context context) {
        this.context = context;
    }

    // Cette méthode valide les informations de connexion
    public void validateLogin(String email, String password, LoginCallback callback) {
        new LoginTask(email, password, callback).execute();
    }

    // Classe interne pour la tâche asynchrone de connexion
    private static class LoginTask extends AsyncTask<Void, Void, String> {
        private String email;
        private String password;
        private LoginCallback callback;

        // Constructeur de la classe
        LoginTask(String email, String password, LoginCallback callback) {
            this.email = email;
            this.password = password;
            this.callback = callback;
        }

        // Cette méthode est exécutée en arrière-plan
        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Création de l'URL et de la connexion
                URL url = new URL("http://10.0.2.2:8000/api/login_check"); // Emulator

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);
                connection.setDoInput(true);

                // Création du JSON avec les informations de connexion
                JSONObject json = new JSONObject();
                json.put("username", email);
                json.put("password", password);

                // Envoi du JSON
                Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(json.toString());
                writer.close();

                // Récupération du code de réponse
                int responseCode = connection.getResponseCode();
                Log.d("LoginTask", "Response Code: " + responseCode); // Log the response code

                // Si le code de réponse est OK, on lit la réponse
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    Log.d("LoginTask", "Response: " + response.toString()); // Log response
                    return response.toString();
                } else {
                    Log.e("LoginTask", "Error Response Code: " + responseCode);
                    return null;
                }
            } catch (Exception e) { // En cas d'exception, on log l'erreur et on retourne null
                Log.e("LoginTask", "Exception: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        // Cette méthode est appelée après l'exécution de la tâche en arrière-plan
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try { // On parse la réponse en JSON
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("token")) {
                        callback.onSuccess(jsonResponse.getString("token"));
                    } else {
                        callback.onError("Invalid response from server");
                    }
                } catch (JSONException e) {
                    callback.onError("JSON parsing error");
                }
            } else {
                callback.onError("Login failed");
            }
        }
    }

    // Interface pour le callback de la connexion
    public interface LoginCallback {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
}