package com.syams.sppk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TambahDataActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    TextView txt_namaTanaman;
    TextView txt_suhu;
    TextView txt_curahHujan;
    TextView txt_kelembapan;
    TextView txt_kedalamanTanah;
    TextView txt_drainase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("dataTraining");
        txt_namaTanaman = (TextView) findViewById(R.id.txt_namaTanaman);
        txt_suhu = (TextView) findViewById(R.id.txt_suhu);
        txt_curahHujan = (TextView) findViewById(R.id.txt_curahHujan);
        txt_kelembapan = (TextView) findViewById(R.id.txt_kelembapan);
        txt_kedalamanTanah = (TextView) findViewById(R.id.txt_kedalamanTanah);
        txt_drainase = (TextView) findViewById(R.id.txt_drainase);
        Button btn_simpan = (Button) findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaTanaman = txt_namaTanaman.getText().toString();
                String suhu = txt_suhu.getText().toString();
                String curahHujan = txt_curahHujan.getText().toString();
                String kelembapan = txt_kelembapan.getText().toString();
                String kedalamanTanah = txt_kedalamanTanah.getText().toString();
                String drainase = txt_drainase.getText().toString();

                if (namaTanaman.isEmpty()){
                    txt_namaTanaman.setError("Data tidak boleh Kosong");
                } else if (suhu.isEmpty()){
                    txt_suhu.setError("Data tidak boleh kosong");
                } else if (curahHujan.isEmpty()){
                    txt_curahHujan.setError("Data tidak boleh kosong");
                } else if (kelembapan.isEmpty()){
                    txt_kelembapan.setError("Data tidak boleh kosong");
                } else if (kedalamanTanah.isEmpty()){
                    txt_kedalamanTanah.setError("Data tidak boleh kosong");
                } else if (drainase.isEmpty()){
                    txt_drainase.setError("Data tidak boleh kosong");
                } else {
                    HashMap<String, String> dataMap = new HashMap<String, String>();
                    dataMap.put("nama tanaman",namaTanaman);
                    dataMap.put("temperatur rerata",suhu);
                    dataMap.put("curah hujan",curahHujan);
                    dataMap.put("kelembapan",kelembapan);
                    dataMap.put("kedalaman tanah",kedalamanTanah);
                    dataMap.put("drainase",drainase);
                    mDatabase.push().setValue(dataMap);
                    startActivity(new Intent(TambahDataActivity.this,HomeActivity.class));
                }

            }
        });

    }
}
