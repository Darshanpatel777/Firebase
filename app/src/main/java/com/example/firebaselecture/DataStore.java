package com.example.firebaselecture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DataStore extends AppCompatActivity {

   Button add;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_store);

        add = findViewById(R.id.add);
        mAuth = FirebaseAuth.getInstance();



        int user = getIntent().getIntExtra("userid", 5);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DataStore.this,Addnew.class));
            }
        });


        //store data show karva mate
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("user");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                HashMap alldata = (HashMap<Object, Object>) dataSnapshot.getValue();
                        for(Object data : alldata.values())
                        {
                            HashMap userdata = (HashMap<Object,Object>) data;
//
                            Log.d("===response===", "onDataChange: " + userdata.get("number"));
                            Log.d("===response===", "onDataChange: " + userdata.get("name"));
                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("+-+-+-", "onCancelled: "+error.toException());
            }
        };

        myref.addValueEventListener(postListener);








        //store data show karva mate
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

    }
}