package com.example.gfattendance;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginManager {
    private Context context;
    private ApiService apiService;

    // Constructeur pour initialiser le contexte et Retrofit
    public LoginManager(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8000/api")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    /**
     * Méthode pour valider les informations de connexion
     *
     * @param email    L'email entré par l'utilisateur
     * @param password Le mot de passe entré par l'utilisateur
     */
    public void validateLogin(String email, String password, LoginCallback callback) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Connexion réussie
                    callback.onSuccess(response.body().getToken());
                } else {
                    // Connexion échouée
                    callback.onError("Invalid email or password");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Erreur réseau ou autre
                callback.onError("Login failed: " + t.getMessage());
            }
        });
    }

    // Interface de rappel pour gérer les résultats de la connexion
    public interface LoginCallback {
        void onSuccess(String token);
        void onError(String errorMessage);
    }
}
