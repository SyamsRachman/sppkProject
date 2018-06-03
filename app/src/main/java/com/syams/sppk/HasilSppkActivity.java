package com.syams.sppk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HasilSppkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_sppk);


        TextView txt_hasil = (TextView) findViewById(R.id.txt_hasil);
        txt_hasil.setText(getIntent().getStringExtra("hasil"));
    }
}
