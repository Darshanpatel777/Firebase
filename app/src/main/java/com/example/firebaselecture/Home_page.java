package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_page extends AppCompatActivity {

    Button sing_up2;
    TextInputEditText name, crtpass, conpass;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        sing_up2 = findViewById(R.id.sing_up2);
        name = findViewById(R.id.name);
        crtpass = findViewById(R.id.crtpass);
        conpass = findViewById(R.id.conpass);
        mAuth = FirebaseAuth.getInstance();


        sing_up2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getText().toString().isEmpty() &&
                        !crtpass.getText().toString().isEmpty() && !conpass.getText().toString().isEmpty()) {

                    String email = name.getText().toString();
                    String password = crtpass.getText().toString();
                    String conpassword = conpass.getText().toString();


//                    ## email check
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        name.setError("Please Enter a Valid Email Address");
                        return;
                    }

//                    password in 6 character
                    if(password.length() != 6 && conpassword.length() !=6)
                    {
                        Toast.makeText(Home_page.this, "Password  Cannot Exceed 6 Digits", Toast.LENGTH_SHORT).show();
                        return;
                    }

//                    password ane  conpassword same hoy to
                    if(password.equals(conpassword)){
                        signup(email,password);

                        startActivity(new Intent(Home_page.this, MainActivity.class));
                        finish();

                    }
                    else {
                        Toast.makeText(Home_page.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Home_page.this, "Please Enter You Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // create account Sing up
    void signup(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("=========", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Home_page.this, "Authentication Completed",
                            Toast.LENGTH_SHORT).show();


                } else {
                    // if sign in fails , display a message to the user.
                    Log.w("---e----", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Home_page.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }

        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {


        }
    }
}