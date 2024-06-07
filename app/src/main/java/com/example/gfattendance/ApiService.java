package com.example.gfattendance;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

// Interface pour définir l'API
public interface ApiService {
    @POST("/login_check")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
