package com.example.firebaselecture;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

public class DataStore extends AppCompatActivity {

    FloatingActionButton add, pop;
    SearchView search;
    ListView list1;
    private FirebaseAuth mAuth;


    ArrayList<HashMap<Object, Object>> datalist = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_store);

        add = findViewById(R.id.add);
        list1 = findViewById(R.id.list1);
        pop = findViewById(R.id.pop);
        search = findViewById(R.id.search);
        mAuth = FirebaseAuth.getInstance();

        int userid = getIntent().getIntExtra("userid", 5);

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DataStore.this, Addnew.class)
                        .putExtra("userid", userid));
                finish();
            }
        });


        //store data show karva mate
        DatabaseReference myref = database.getReference("user");

        // main page store(add) data show karava mate
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

                        datalist.add(userdata);

                    }
                    datalist.sort(new Comparator<HashMap<Object, Object>>() {
                        @Override
                        public int compare(HashMap<Object, Object> o1, HashMap<Object, Object> o2) {
                            return o1.get("name").toString().compareTo(o2.get("name").toString());
                        }
                    });

                    MyAdpater adpater = new MyAdpater(DataStore.this, datalist);
                    list1.setAdapter(adpater);
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


        ArrayList<HashMap<Object, Object>> searchlist = new ArrayList();


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // submit ni click  thay taire data show karva
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }


            // text type thay taire data show karva
            @Override
            public boolean onQueryTextChange(String newText) {

                searchlist.clear();

                for (HashMap<Object,Object> data : datalist)
                {
                    String sname = data.get("name").toString();
                    String snumber = data.get("number").toString();
                    String seamilid = data.get("Email id").toString();

                    if(sname.contains(newText) || snumber.contains(newText) || seamilid.contains(newText))
                    {

                        searchlist.add(data);
                    }


                }

                MyAdpater searchAdpater = new MyAdpater(DataStore.this,searchlist);
                list1.setAdapter(searchAdpater);

                return true;
            }
        });





//     pop menu open karava
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu pmenu = new PopupMenu(DataStore.this, pop);

                pmenu.inflate(R.menu.mymenu);
                pmenu.show();

                pmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.logout) {

                            Dialog dialog = new Dialog(DataStore.this);
                            dialog.setContentView(R.layout.dialogview);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.show();

                            TextView tex = dialog.findViewById(R.id.tex);
                            Button yes = dialog.findViewById(R.id.yes);
                            Button no = dialog.findViewById(R.id.no);

                            tex.getText();

                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Space_Screen.edit.putBoolean("status", false);
                                    Space_Screen.edit.apply();
                                    Space_Screen.edit.putInt("uid", 0);

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
                        } else if (item.getItemId() == R.id.setting) {

                            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                            startActivity(intent);
                            finish();

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





//  datalist.sort(new Comparator<HashMap<Object, Object>>() {
//    @Override
//    public int compare(HashMap<Object, Object> o1, HashMap<Object, Object> o2) {
//        return o1.get("name").toString().toUpperCase().compareTo(o2.get("name").toString());
//    }
//});