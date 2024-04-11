package com.example.hotelreservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ReservedFragment extends Fragment {
    View view;
    TextView reservedTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.reserved,container,false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        //super.onViewCreated(view, savedInstanceState);

        reservedTextView = view.findViewById(R.id.reserved_textview);

        Integer confirmation_number = getArguments().getInt("confirmation_number");

        reservedTextView.setText("confirmation_number: " + confirmation_number);
    }
}
