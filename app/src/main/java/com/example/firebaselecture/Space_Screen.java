package com.example.firebaselecture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Space_Screen extends AppCompatActivity {


    static SharedPreferences sp;
    static SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_space_screen);


        sp = getSharedPreferences("myshare", MODE_PRIVATE);
        edit = sp.edit();


        Boolean in = sp.getBoolean("status",false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (in)
                {
                    startActivity(new Intent(Space_Screen.this,DataStore.class)
                            .putExtra("userid",sp.getInt("uid",0)));
                    finish();
                }
                else
                {

                    startActivity(new Intent(Space_Screen.this,MainActivity.class));
                    finish();
                }
            }
        },1000);

    }
}