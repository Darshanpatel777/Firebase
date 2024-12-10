package com.example.firebaselecture;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button sing_up1, login, btn;
    SignInButton google;

    TextInputEditText mail,pass;

    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        sing_up1 = findViewById(R.id.sing_up1);
        login = findViewById(R.id.login);
        google = findViewById(R.id.bt_sign_in);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);

//        btn = findViewById(R.id.btn);


//        firebase ma data add, delete ,show karva mate
//        FirebaseDatabase database = FirebaseDatabase.getInstance();


        // unique key vagar data Store Karva mate
//        DatabaseReference myref = database.getReference("user");

        // Data show karva mate
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                //unique key sathe data Store karva mate (push key use )
//                DatabaseReference myref = database.getReference("user").push();
//
//                //unique key leva mate
//                String key = myref.getKey();
//
//                myref.child("name").setValue("creative");
//                myref.child("number").setValue("1548956");
//                myref.child("key").setValue(key);
//            }
//        });



//        Store data delete karva mate
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //  Store data delete karva mare --> key,value
//                DatabaseReference myRef = database.getReference("user").child("-OCcf5oosWDJYPqeKCjs");
//                myRef.removeValue();
//
//            }
//        });




//                store data show karva mate
//                DatabaseReference myRef = database.getReference("user");

//           btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ValueEventListener postListener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        HashMap alldata = (HashMap<Object, Object>) dataSnapshot.getValue();
//                        for(Object data : alldata.values())
//                        {
//                            HashMap userdata = (HashMap<Object,Object>) data;
//
//                            Log.d("===response===", "onDataChange: " + userdata.get("number"));
//                            Log.d("===response===", "onDataChange: " + userdata.get("name"));
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Getting Post failed, log a message
//                        Log.w("===error===", "loadPost:onCancelled", databaseError.toException());
//                    }
//                };
//                myRef.addValueEventListener(postListener);
//            }
//        });



        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("16575743123-kaai7c8ecvk7ljck3k6r30ao3o6fsd2a.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(MainActivity.this, googleSignInOptions);

// google Sign in
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = googleSignInClient.getSignInIntent();
                // Start activity for result
                startActivityForResult(intent, 100);


            }
        });

        // create account Sign page ma java mate
        sing_up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Home_page.class));
//                finish();

//                signup("abc@gmail.com", "123456");
            }
        });

        // old Account login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mail.getText().toString().isEmpty() && !pass.getText().toString().isEmpty())
                {
                    oldlogin(mail.getText().toString(),pass.getText().toString());
//
                    Space_Screen.edit.putBoolean("status", true);
                    Space_Screen.edit.putInt("uid",0);
                    Space_Screen.edit.apply();

                    startActivity(new Intent(MainActivity.this,DataStore.class)
                            .putExtra("userid",0));
                    finish();

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter You Email-Id", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //google sign in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);

            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {

//                                    Log.d(TAG, "onComplete: ");
                                    displayToast("Firebase authentication successful");


                                    Space_Screen.edit.putBoolean("status", true);
                                    Space_Screen.edit.putInt("uid",0);
                                    Space_Screen.edit.apply();

                                    startActivity(new Intent(MainActivity.this,DataStore.class));
                                    finish();

                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("------------", "onActivityResult: " + signInAccountTask.getException());
                displayToast("fail" + signInAccountTask.getException());
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    // old Account login
    void oldlogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("---d---", "signInWithEmail:success");

                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(MainActivity.this, "Authentication success.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("---f---", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

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