package com.example.damis.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class ChooseSeat extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_choose);


        Spinner spinner = (Spinner) findViewById(R.id.et_seat);

        Bundle bundle = getIntent().getExtras();

        ArrayList<Integer> list = bundle.getIntegerArrayList("miejsca");

        ArrayList<String> cos = new ArrayList<>();

        for (int i =0; i< 50; ++i){
            char l = (char)(64 + i);
            cos.add(l + String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, cos);
        spinner.setAdapter(adapter);
    }
}