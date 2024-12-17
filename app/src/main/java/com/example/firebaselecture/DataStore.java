package com.example.firebaselecture;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataStore extends AppCompatActivity {

    TextView txtname, txtemail, txtnumber;
    FloatingActionButton add,pop;
    ListView list;
    private FirebaseAuth mAuth;


    ArrayList<HashMap<Object, Object>> datalist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_store);

        add = findViewById(R.id.add);
        list = findViewById(R.id.list);
        pop = findViewById(R.id.pop);
        mAuth = FirebaseAuth.getInstance();

        int userid = getIntent().getIntExtra("userid", 5);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DataStore.this, Addnew.class)
                        .putExtra("userid", userid));
            }
        });


        //store data show karva mate
        DatabaseReference myref = database.getReference("user");


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    HashMap alldata = (HashMap<Object, Object>) dataSnapshot.getValue();

                    for (Object data : alldata.values()) {
                        HashMap userdata = (HashMap<Object, Object>) data;

                        Log.d("===response===", "onDataChange: " + userdata.get("number"));
                        Log.d("===response===", "onDataChange: " + userdata.get("name"));
                        Log.d("===response===", "onDataChange: " + userdata.get("Email id"));

                        HashMap<Object, Object> item = new HashMap<>();
                        item.put("name", userdata.get("name"));
                        item.put("number", userdata.get("number"));
                        item.put("Email id", userdata.get("Email id"));

                        datalist.add(item);

                        MyAdpater adpater = new MyAdpater(DataStore.this, datalist);
                        list.setAdapter(adpater);
                    }
                } else {
                    Toast.makeText(DataStore.this, "Data not found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.d("+-+-+-", "onCancelled: " + error.toException());
            }
        };
        myref.addValueEventListener(postListener);



//     pop menu open karava
    pop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PopupMenu pmenu = new PopupMenu(DataStore.this,pop);

            pmenu.inflate(R.menu.mymenu);
            pmenu.show();

            pmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {


                    if(item.getItemId() == R.id.Exit)
                    {
                        Dialog dialog = new Dialog(DataStore.this);
                        dialog.setContentView(R.layout.dialogview);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();

                        TextView tex = dialog.findViewById(R.id.tex);
                        Button yes = dialog.findViewById(R.id.yes);
                        Button no = dialog.findViewById(R.id.no);

                        tex.getText();

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Space_Screen.edit.putBoolean("ststus",false);
                                Space_Screen.edit.apply();

                                startActivity(new Intent(DataStore.this, MainActivity.class));
                                finish();
                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });
                    } else if (item.getItemId() == R.id.Callhistory) {

                        Toast.makeText(DataStore.this, "Callhistory", Toast.LENGTH_SHORT).show();
                    } else if (item.getItemId() == R.id.setting) {

                        Toast.makeText(DataStore.this, "setting", Toast.LENGTH_SHORT).show();

                    } else if (item.getItemId() == R.id.Helpfeedback) {

                        Toast.makeText(DataStore.this, "Helpfeedback", Toast.LENGTH_SHORT).show();

                    }
                    return false;
                }
            });

        }
    });


    }
}



