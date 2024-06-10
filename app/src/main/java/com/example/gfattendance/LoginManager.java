package com.example.gfattendance;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginManager {
    private ApiService apiService;

    public LoginManager(String baseUrl) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .followRedirects(false) // Ajoutez cette ligne pour désactiver le suivi des redirections
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public void validateLogin(String email, String password, final LoginCallback callback) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getToken());
                } else {
                    callback.onError("Invalid email or password");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError("Login failed: " + t.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    public interface ApiService {
        @POST("/login_check")
        Call<LoginResponse> login(@Body LoginRequest loginRequest);
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String email, String password) {
            this.password = password;
            this.username = email;

        }
    }

    public static class LoginResponse {
        private String token;

        public String getToken() {
            return token;
        }
    }
}
