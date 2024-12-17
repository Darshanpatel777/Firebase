package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdpater extends BaseAdapter {

    private DataStore dataStore;
    private ArrayList<HashMap<Object, Object>> datalist;

    public MyAdpater(DataStore dataStore, ArrayList<HashMap<Object, Object>> datalist) {


        this.dataStore = dataStore;
        this.datalist = datalist;

    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Context DataStore;
        View vv = LayoutInflater.from(dataStore).inflate(R.layout.myview,parent,false);


        TextView sname  = vv.findViewById(R.id.sname);
        TextView snum  = vv.findViewById(R.id.snum);
        TextView email  = vv.findViewById(R.id.email);

        HashMap<Object, Object> data = datalist.get(position);

        sname.setText( data.get("name").toString());
        snum.setText( data.get("number").toString());
        email.setText( data.get("Email id").toString());


        sname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataStore.startActivity(new Intent(dataStore, Update.class)
                        .putExtra("name",sname.getText())
                        .putExtra("number",snum.getText())
                        .putExtra("Email id",email.getText()));
            }
        });


        return vv;
    }
}
