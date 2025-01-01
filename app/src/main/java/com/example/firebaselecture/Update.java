package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class Update extends AppCompatActivity {


    TextInputEditText oldname, oldEmailId, oldnum;
    Button cancel, save, delete;
    ImageView Whatsapp, Skype,massage;
    private FirebaseAuth mAuth;
    DatabaseReference myref;
    FloatingActionButton pop1;


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
        Whatsapp = findViewById(R.id.Whatsapp);
        massage = findViewById(R.id.massage);
        Skype = findViewById(R.id.Skype);
        pop1 = findViewById(R.id.pop1);
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


                String dname = oldname.getText().toString();
                String dnumber = oldnum.getText().toString();
                String deamilid = oldEmailId.getText().toString();

                if (!dname.isEmpty() && !dnumber.isEmpty() && !deamilid.isEmpty()) {
                    DatabaseReference myref = database.getReference("user").child(key);

                    myref.child("name").setValue(dname);
                    myref.child("number").setValue(dnumber);
                    myref.child("Email id").setValue(deamilid);


                    startActivity(new Intent(Update.this, DataStore.class));
                    finish();
                } else {
                    Toast.makeText(Update.this, "please fill a data", Toast.LENGTH_SHORT).show();
                }


            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Update.this, DataStore.class));
                finish();
            }
        });

        Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                String sharebody = " name :-" + oldname.getText().toString() + "\n" +
                        "number:-" + oldnum.getText().toString() + "\n" +
                        "Email id :-" + oldEmailId.getText().toString();
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
                Update.this.startActivity(whatsappIntent);

            }
        });


        Skype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // Replace with your contact's Skype username or phone number
//                String skypeUsername = "9409810017";  // Example format: "live:username"
                final int REQUEST_SMS_PERMISSION = 1;

//                    sendMessageToSkype(skypeUsername, "Hello, this is a test message!");
                if (ContextCompat.checkSelfPermission(Update.this, android.Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(Update.this, new String[]{android.Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);

                }
                else {
                    Uri uri = Uri.parse("smsto:9409810017" );  // "smsto:" is used for SMS URI

                    // Create an Intent to open the messaging app
//                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//                    intent.putExtra("sms_body", "Text"); // Add a default message to the message body
//
//                    // Start the activity to open the SMS app
//                    startActivity(intent);

//-----------------------------------------------------

//                    String number = "8490087505";
//                    String msg = "Text";
//                    try {
//                        SmsManager smsManager = SmsManager.getDefault();
//                        smsManager.sendTextMessage(number, null, msg, null, null);
//                        Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Log.d("=========", "onClick: " + e.getLocalizedMessage());
//                        Toast.makeText(getApplicationContext(), "Some fields is Empty" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    }
                }

            }
        });

        massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });




        pop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu pmenu1 = new PopupMenu(Update.this, pop1);

                pmenu1.inflate(R.menu.mymenu1);
                pmenu1.show();

                pmenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.delete) {

                            Dialog dialog = new Dialog(Update.this);
                            dialog.setContentView(R.layout.dialogview_delete);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.show();

                            TextView tex1 = dialog.findViewById(R.id.tex1);
                            Button yes1 = dialog.findViewById(R.id.yes1);
                            Button no1 = dialog.findViewById(R.id.no1);

                            tex1.getText();

                            //Store data delete karva mate
                            yes1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //  Store data delete karva mare --> key,value
                                    DatabaseReference myRef = database.getReference("user").child(key);
                                    myRef.removeValue();

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
                        } else if (item.getItemId() == R.id.share) {


                            Intent intent = new Intent(Intent.ACTION_SEND);
                            String shareBody = "Name :-" + oldname.getText().toString() + "\n" +
                                    "Number :-" + oldnum.getText().toString() + "\n" +
                                    "Email id :-" + oldEmailId.getText().toString();
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                            Update.this.startActivity(Intent.createChooser(intent, ""));
                        }

                        return false;
                    }
                });
            }
        });
    }

    // Send message to Skype via Intent
    private void sendMessageToSkype(String skypeUsername, String message) {
        // Create Skype URL to send message
        String skypeUri = "skype:" + skypeUsername + "?chat&message=" + Uri.encode(message);

        // Create intent to open Skype and send message
        Intent skypeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(skypeUri));
        skypeIntent.setPackage("com.skype.raider");  // Ensure that it opens Skype

        // Start the Skype app with the provided message
        startActivity(skypeIntent);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, DataStore.class);
        startActivity(i);
        finish();
    }
}




