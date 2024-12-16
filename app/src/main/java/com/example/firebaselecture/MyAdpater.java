package com.example.firebaselecture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdpater extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<Object, Object>> datalist;

    public MyAdpater(Context context, ArrayList<HashMap<Object, Object>> datalist) {


        this.context = context;
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

//        View vv = LayoutInflater.from(context).inflate(R.layout.myview, parent, false);
//
//        TextView name = vv.findViewById(R.id.sname), num = vv.findViewById(R.id.snum);
//
//        name.setText(datalist.get(position).getName());
//        num.setText(datalist.get(position).getNum());

        View vv = LayoutInflater.from(context).inflate(R.layout.myview,parent,false);


        TextView name  = vv.findViewById(R.id.sname);
        TextView number  = vv.findViewById(R.id.snum);

        HashMap<Object, Object> data = datalist.get(position);

        name.setText((CharSequence) data.get("name"));
        number.setText((CharSequence) data.get("number"));


        return convertView;
    }
}
