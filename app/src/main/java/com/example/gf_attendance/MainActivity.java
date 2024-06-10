package com.example.gf_attendance;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity {
    private final Context context;
    private JSONObject userData;

    public MainActivity(Context context) {
        this.context = context;
        loadUserData();
    }

    private void loadUserData() {
        try {
            InputStream inputStream = context.getAssets().open("login info.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            userData = new JSONObject(json);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void recoverPassword(String email) {
        try {
            if (userData != null) {
                JSONArray usersArray = userData.getJSONArray("users");
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject user = usersArray.getJSONObject(i);
                    if (email.equals(user.getString("email"))) {
                        // Send password recovery email or perform necessary actions
                        Toast.makeText(context, "Password recovery email sent to " + email, Toast.LENGTH_SHORT).show();
                        //
                        return;
                    }
                }
                // If email not found in the JSON data
                Toast.makeText(context, "Email not registered", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private String NouveauMotDePasse(){
        return "NewPassword";
        }

    private void EnregistrementDesDonnées() {
        try {
            File file = new File(context.getExternalFilesDir(null), "login info.json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(userData.toString().getBytes(StandardCharsets.UTF_8));
            fos.close();
            Toast.makeText(context, " Mises à jour du login et password", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
    }
}
}
