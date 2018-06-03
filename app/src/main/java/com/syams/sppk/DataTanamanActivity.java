package com.syams.sppk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DataTanamanActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    ArrayList<String> test;
    EditText txt_namaTanaman;
    EditText txt_suhu;
    EditText txt_curahHujan;
    EditText txt_kelembapan;
    EditText txt_kedalamanTanah;
    EditText txt_drainase;
    Button btn_simpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_tanaman);
        txt_namaTanaman = (EditText) findViewById(R.id.txt_namaTanaman);
        txt_suhu = (EditText) findViewById(R.id.txt_suhu);
        txt_curahHujan = (EditText) findViewById(R.id.txt_curahHujan);
        txt_kelembapan = (EditText) findViewById(R.id.txt_kelembapan);
        txt_kedalamanTanah = (EditText) findViewById(R.id.txt_kedalamanTanah);
        txt_drainase = (EditText) findViewById(R.id.txt_drainase);
        btn_simpan = (Button) findViewById(R.id.btn_simpan);


        btn_simpan.setVisibility(View.INVISIBLE);
        txt_namaTanaman.setEnabled(false);
        txt_suhu.setEnabled(false);
        txt_curahHujan.setEnabled(false);
        txt_kelembapan.setEnabled(false);
        txt_kedalamanTanah.setEnabled(false);
        txt_drainase.setEnabled(false);


        String idTanaman = getIntent().getStringExtra("idTanaman");

//        Log.v(" test",idTanaman);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("dataTraining").child(idTanaman);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.v("E_VALUE","Data :"+dataSnapshot.getValue());

                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                String namaTanaman = map.get("nama tanaman");
                String suhu = map.get("temperatur rerata");
                String kembapan = map.get("kelembapan");
                String kedalamanTanah = map.get("kedalaman tanah");
                String drainase = map.get("drainase");
                String curahHujan = map.get("curah hujan");

                txt_namaTanaman.setText(namaTanaman);
                txt_suhu.setText(suhu);
                txt_kelembapan.setText(kembapan);
                txt_kedalamanTanah.setText(kedalamanTanah);
                txt_drainase.setText(drainase);
                txt_curahHujan.setText(curahHujan);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
