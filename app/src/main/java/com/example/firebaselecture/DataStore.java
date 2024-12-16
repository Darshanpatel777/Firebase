package com.example.firebaselecture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataStore extends AppCompatActivity {

    TextView txtname,txtemail,txtnumber;
    Button add;
    ListView list;
    private FirebaseAuth mAuth;


    ArrayList<HashMap<Object, Object>> datalist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_store);

        add = findViewById(R.id.add);
        list = findViewById(R.id.listview);
//        txtname = findViewById(R.id.txtname);
//        txtemail = findViewById(R.id.txtemail);
//        txtnumber = findViewById(R.id.txtnumber);
        mAuth = FirebaseAuth.getInstance();


        int userid = getIntent().getIntExtra("userid", 5);







        FirebaseDatabase database = FirebaseDatabase.getInstance();





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DataStore.this, Addnew.class)
                        .putExtra("userid",userid));
            }
        });



        //store data show karva mate
        DatabaseReference myref = database.getReference("user");


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null)
                {
                    HashMap alldata = (HashMap<Object, Object>) dataSnapshot.getValue();

                    for (Object data : alldata.values())
                    {
                        HashMap userdata = (HashMap<Object, Object>) data;

                        Log.d("===response===", "onDataChange: " + userdata.get("number"));
                        Log.d("===response===", "onDataChange: " + userdata.get("name"));
                        Log.d("===response===", "onDataChange: " + userdata.get("Email id"));

                        HashMap<Object,Object> item = new HashMap<>();
                        item.put("name", userdata.get("name"));
                        item.put("number", userdata.get("number"));

                        datalist.add(item);

////
//                        txtname.setText(userdata.get("name").toString());
//                        txtnumber.setText(userdata.get("number").toString());
//                        txtemail.setText(userdata.get("Email id").toString());



//                        list.setAdapter(new MyAdpater(DataStore.this,datalist));

                        MyAdpater adpater = new MyAdpater(DataStore.this,datalist);
                        list.setAdapter(adpater);


                    }
                }
                else
                {
                    Toast.makeText(DataStore.this, "Data not found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("+-+-+-", "onCancelled: " + error.toException());
            }
        };
        myref.addValueEventListener(postListener);





    }
}