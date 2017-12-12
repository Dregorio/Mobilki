package com.example.damis.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseWhatYouWant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_what_you_want);

        final Bundle bundle = getIntent().getExtras();

        Button btn = (Button)findViewById(R.id.btn_siedzenia);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ChooseSeat.class);
                intent.putExtra("miejsca", bundle.getIntegerArrayList("miejsca"));

                startActivity(intent);
                finish();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn_rezerwacje);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ShowRezervation.class);
                intent.putExtra("rezerwacja", bundle.getStringArrayList("rezerwacja"));
                startActivity(intent);
                finish();
            }
        });
    }
}
