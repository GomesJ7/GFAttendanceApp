package com.example.gf_attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_open;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);

        btn_open = findViewById(R.id.open_LoginPage);

        btn_open.setOnClickListener(v -> {
            Intent intent =new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);

        });
    }
}