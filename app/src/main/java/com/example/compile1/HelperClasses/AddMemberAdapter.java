package com.example.compile1.HelperClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.compile1.R;

import java.util.ArrayList;
import java.util.List;

public class AddMemberAdapter extends ArrayAdapter<String> {

    public AddMemberAdapter(Activity context, int resource, List<String> list) {
        super(context, resource, list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        String userRecord = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.addmember_list_item, parent, false);
        }

        TextView textViewEmail;

        textViewEmail = (TextView) convertView.findViewById(R.id.addmember_name);
        textViewEmail.setText(userRecord);

        return convertView;
    }
}
