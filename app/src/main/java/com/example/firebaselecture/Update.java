package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Update extends AppCompatActivity {


    TextInputEditText oldname, oldEmailId, oldnum;
    Button cancel, save, delete;
    private FirebaseAuth mAuth;
    DatabaseReference myref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update);

        oldname = findViewById(R.id.Newname);
        oldEmailId = findViewById(R.id.NewEmailId);
        oldnum = findViewById(R.id.Newnum);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.sav);
        delete = findViewById(R.id.delete);
        mAuth = FirebaseAuth.getInstance();


//        int userid = getIntent().getIntExtra("userid", 60);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        String updatename = getIntent().getStringExtra("name");
        String updatenumber = getIntent().getStringExtra("number");
        String updateEmailid = getIntent().getStringExtra("Email id");
        String key = getIntent().getStringExtra("key");


        oldname.setText(updatename);
        oldnum.setText(updatenumber);
        oldEmailId.setText(updateEmailid);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference myref = database.getReference("user").child(key);


                startActivity(new Intent(Update.this, DataStore.class));
                finish();

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Update.this, DataStore.class));

                finish();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(Update.this);
                dialog.setContentView(R.layout.dialogview_delete);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                TextView tex1 = dialog.findViewById(R.id.tex1);
                Button yes1 = dialog.findViewById(R.id.yes1);
                Button no1 = dialog.findViewById(R.id.no1);

                tex1.getText();

                yes1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        startActivity(new Intent(Update.this, DataStore.class));

                        finish();
                    }
                });
                no1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });


            }
        });


    }
}




