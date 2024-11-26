package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Home_page extends AppCompatActivity {

    Button sing_up2;
    TextInputEditText name,mail2,crtpass,cnfpass;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        sing_up2  = findViewById(R.id.sing_up2);

        name = findViewById(R.id.name);
        mail2 = findViewById(R.id.mail2);
        crtpass = findViewById(R.id.crtpass);
        cnfpass = findViewById(R.id.cnfpass);


        sing_up2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Home_page.this,MainActivity.class));
            }
        });

    }
}