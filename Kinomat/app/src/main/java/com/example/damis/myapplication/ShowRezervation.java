package com.example.damis.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.gson.Gson;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;

public class ShowRezervation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rezervation);

        Bundle bundle = getIntent().getExtras();

        ListView lista = (ListView) findViewById(R.id.listView);

        ArrayList<String> lis = bundle.getStringArrayList("rezerwacja");

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.one, lis);
        lista.setAdapter(adapter);



    }
}
