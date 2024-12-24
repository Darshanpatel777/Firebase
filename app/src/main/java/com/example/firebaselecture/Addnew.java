package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Addnew extends AppCompatActivity {

    TextInputEditText name,emailid,phone;
    Button sve;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addnew);

        name = findViewById(R.id.name);
        emailid = findViewById(R.id.emailid);
        phone = findViewById(R.id.phone);
        sve = findViewById(R.id.sve);


        //        firebase ma data add, delete ,show karva mate
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        // Data show karva mate
        sve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // unique key vagar data Store Karva mate puse key use
                DatabaseReference myref = database.getReference("user").push();

                String key = myref.getKey();

                String email = emailid.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    emailid.setError("Please enter a valid email address");
                    return;
                }

                //number  required check
                String number = phone.getText().toString();
                if (number.isEmpty()) {
                    phone.setError("number if required");
                    return;
                }
                else if (number.length() != 10) // number 10 digit ma j aava aena mate
                {
                    phone.setError("Phone number cannot exceed 10 digits");
                    return;
                }

                myref.child("name").setValue(name.getText().toString());
                myref.child("number").setValue(phone.getText().toString());
                myref.child("Email id").setValue(emailid.getText().toString());
                myref.child("key").setValue(key);



                if (!name.getText().toString().isEmpty() &&
                        !phone.getText().toString().isEmpty() &&
                        !emailid.getText().toString().isEmpty())
                {
                    startActivity(new Intent(Addnew.this,DataStore.class));
                    finish();
                }

                else
                {
                    Toast.makeText(Addnew.this, "please fill date", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, DataStore.class);
        startActivity(i);
        finish();
    }

}




