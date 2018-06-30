package com.syams.sppk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class HitungSppkActivity extends AppCompatActivity {

    EditText txt_suhu, txt_curahHujan, txt_kelembapan, txt_kedalamanTanah,
            txt_drainase;

    TextView txt_hasil;

    Button btn_hitung;

    int urutan;
    String[][] tanaman;

    Double suhuRerata, curahHujan, kelembapan,
            kedalamanTanah, drainase;

//    double parameterSuhu, parameterCurahHujan, parameterKelembapan,
//            parameterKedalamanTanah, parameterDrainase;

    private DatabaseReference mDatabase;

    ArrayList<String> idTanaman = new ArrayList<>();

    public double parameterSuhu(Double suhuRerata){
        double parameterSuhu;
        if (suhuRerata < 17) {
            parameterSuhu = 0;
        } else if (suhuRerata >= 18 && suhuRerata <= 20) {
            parameterSuhu = 0.15;
        } else if (suhuRerata >= 21 && suhuRerata <= 22) {
            parameterSuhu = 0.3;
        } else if (suhuRerata >= 23 && suhuRerata <= 30) {
            parameterSuhu = 0.5;
        } else if (suhuRerata >= 31 && suhuRerata <= 32) {
            parameterSuhu = 0.65;
        } else if (suhuRerata >= 33 && suhuRerata <= 35) {
            parameterSuhu = 0.8;
        } else {
            parameterSuhu = 1;
        }
        return parameterSuhu;
    }

    public double parameterCurahHujan(Double curahHujan){
        double parameterCurahHujan;
        if (curahHujan < 199) {
            parameterCurahHujan = 0;
        } else if (curahHujan >= 200 && curahHujan <= 300) {
            parameterCurahHujan = 0.15;
        } else if (curahHujan >= 301 && curahHujan <= 400) {
            parameterCurahHujan = 0.3;
        } else if (curahHujan >= 401 && curahHujan <= 1110) {
            parameterCurahHujan = 0.5;
        } else if (curahHujan >= 1111 && curahHujan <= 1600) {
            parameterCurahHujan = 0.65;
        } else if (curahHujan >= 1601 && curahHujan <= 1900) {
            parameterCurahHujan = 0.8;
        } else {
            parameterCurahHujan = 1;
        }
        return parameterCurahHujan;
    }

    public double parameterKelembapan(Double kelembapan){
        double parameterKelembapan;
        if (kelembapan < 29) {
            parameterKelembapan = 0;
        } else if (kelembapan >= 30 && kelembapan <= 36) {
            parameterKelembapan = 0.2;
        } else if (kelembapan >= 37 && kelembapan <= 42) {
            parameterKelembapan = 0.4;
        } else if (kelembapan >= 43 && kelembapan <= 75) {
            parameterKelembapan = 0.6;
        } else if (kelembapan >= 76 && kelembapan <= 90) {
            parameterKelembapan = 0.8;
        } else {
            parameterKelembapan = 1;
        }
        return parameterKelembapan;
    }

    public double parameterKedalamanTanah(Double kedalamanTanah){
        double parameterKedalamanTanah;

        if (kedalamanTanah < 50) {
            parameterKedalamanTanah = 0;
        } else if (kedalamanTanah >= 50 && kedalamanTanah <= 75) {
            parameterKedalamanTanah = 0.3;
        } else if (kedalamanTanah >= 76 && kedalamanTanah <= 100) {
            parameterKedalamanTanah = 0.6;
        } else {
            parameterKedalamanTanah = 1;
        }
        return parameterKedalamanTanah;
    }

    public double parameterDrainase(Double drainase){
        double parameterDrainase;
        if (suhuRerata < 0) {
            parameterDrainase = 0;
        } else if (drainase==0) {
            parameterDrainase = 0.15;
        } else if (drainase >= 25 && drainase <= 49) {
            parameterDrainase = 0.3;
        } else if (drainase >= 50 && drainase <= 99) {
            parameterDrainase = 0.5;
        } else if (drainase >= 100 && drainase <= 149) {
            parameterDrainase = 0.65;
        } else if (drainase >= 150 && drainase <= 199) {
            parameterDrainase = 0.8;
        } else {
            parameterDrainase = 1;
        }
        return parameterDrainase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_sppk);

        txt_suhu = findViewById(R.id.txt_suhu);
        txt_curahHujan = findViewById(R.id.txt_curahHujan);
        txt_kelembapan = findViewById(R.id.txt_kelembapan);
        txt_kedalamanTanah = findViewById(R.id.txt_kedalamanTanah);
        txt_drainase = findViewById(R.id.txt_drainase);
        btn_hitung = findViewById(R.id.btn_hitung);
        txt_hasil = (TextView) findViewById(R.id.txt_hasil);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("dataTraining");
        urutan = 0;

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getKey();
                idTanaman.add(value);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("E_VALUE","DATA: "+dataSnapshot.getValue());

                tanaman = new String[idTanaman.size()][7];
                for (int i=0;i<idTanaman.size();i++){
                    Map<String, String> map = (Map<String, String>) dataSnapshot.child(idTanaman.get(i)).getValue();
                    tanaman[i][0] = map.get("nama tanaman");
                    tanaman[i][1] = map.get("temperatur rerata");
                    tanaman[i][2] = map.get("curah hujan");
                    tanaman[i][3] = map.get("kelembapan");
                    tanaman[i][4] = map.get("kedalaman tanah");
                    tanaman[i][5] = map.get("drainase");

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean error=false;

                if (txt_suhu.getText().toString().isEmpty()){
                    txt_suhu.setError("Data tidak boleh kosong");
                    error = true;
                }
                if (txt_curahHujan.getText().toString().isEmpty()){
                    txt_curahHujan.setError("Data tidak boleh kosong");
                    error = true;
                }
                if (txt_kelembapan.getText().toString().isEmpty()){
                    txt_kelembapan.setError("Data tidak boleh kosong");
                    error = true;
                }
                if (txt_kedalamanTanah.getText().toString().isEmpty()){
                    txt_kedalamanTanah.setError("Data tidak boleh kosong");
                    error = true;
                }
                if (txt_drainase.getText().toString().isEmpty()){
                    txt_drainase.setError("Data tidak boleh kosong");
                    error = true;
                }
                if (!error){

                    suhuRerata = Double.parseDouble(String.valueOf(txt_suhu.getText()));
                    curahHujan = Double.parseDouble(String.valueOf(txt_curahHujan.getText()));
                    kelembapan = Double.parseDouble(String.valueOf(txt_kelembapan.getText()));
                    kedalamanTanah = Double.parseDouble(String.valueOf(txt_kedalamanTanah.getText()));
                    drainase = Double.parseDouble(String.valueOf(txt_drainase.getText()));

                    Intent i = new Intent(HitungSppkActivity.this, HasilSppkActivity.class);

                    String hasil;

                    hasil = "Data Test : ";
                    hasil += "\nSuhu rerata : "+suhuRerata+" \t-> "+parameterSuhu(suhuRerata);
                    hasil += "\nCurah Hujan : "+curahHujan+" \t-> "+parameterCurahHujan(curahHujan);
                    hasil += "\nKelembapan : "+kelembapan+" \t-> "+parameterKelembapan(kelembapan);
                    hasil += "\nKedalaman Tanah : "+kedalamanTanah+" \t-> "+parameterKedalamanTanah(kedalamanTanah);
                    hasil += "\nDrainase : "+drainase+" \t-> "+parameterDrainase(drainase);

                    for (int j=0;j<idTanaman.size();j++){

                        double nilaiSuhu = parameterSuhu(Double.parseDouble(tanaman[j][1]));
                        double nilaiCurahHujan = parameterCurahHujan(Double.parseDouble(tanaman[j][2]));
                        double nilaiKelembapan = parameterKelembapan(Double.parseDouble(tanaman[j][3]));
                        double nilaiKedalamanTanah = parameterKedalamanTanah(Double.parseDouble(tanaman[j][4]));
                        double nilaiDrainase = parameterDrainase(Double.parseDouble(tanaman[j][5]));

                        double nilaiTotal = Math.sqrt(Math.pow(nilaiSuhu-parameterSuhu(suhuRerata),2)+
                                Math.pow(nilaiCurahHujan-parameterCurahHujan(curahHujan),2)+
                                Math.pow(nilaiKelembapan-parameterKelembapan(kelembapan),2)+
                                Math.pow(nilaiKedalamanTanah-parameterKedalamanTanah(kedalamanTanah),2)+
                                Math.pow(nilaiDrainase-parameterDrainase(drainase),2));

                        tanaman[j][6] = String.valueOf(nilaiTotal);


                    }

                    for (int j=0;j<idTanaman.size()-1;j++){
                        for (int k=0;k<idTanaman.size()-j-1;k++){
                            if (Double.parseDouble(tanaman[k][6]) > Double.parseDouble(tanaman[k+1][6]))
                            {
                                // swap temp and arr[i]
                                String nama = tanaman[k][0];
                                String nilaiSuhu = tanaman[k][1];
                                String nilaiCurahHujan = tanaman[k][2];
                                String nilaiKelembapan = tanaman[k][3];
                                String nilaiKedalamanTanah = tanaman[k][4];
                                String nilaiDrainase = tanaman[k][5];
                                String nilaiTotal = tanaman[k][6];

                                tanaman[k][0] = tanaman[k+1][0];
                                tanaman[k][1] = tanaman[k+1][1];
                                tanaman[k][2] = tanaman[k+1][2];
                                tanaman[k][3] = tanaman[k+1][3];
                                tanaman[k][4] = tanaman[k+1][4];
                                tanaman[k][5] = tanaman[k+1][5];
                                tanaman[k][6] = tanaman[k+1][6];

                                tanaman[k+1][0] = nama;
                                tanaman[k+1][1] = nilaiSuhu;
                                tanaman[k+1][2] = nilaiCurahHujan;
                                tanaman[k+1][3] = nilaiKelembapan;
                                tanaman[k+1][4] = nilaiKedalamanTanah;
                                tanaman[k+1][5] = nilaiDrainase;
                                tanaman[k+1][6] = nilaiTotal;

                            }
                        }
                    }

                    for (int j=0;j<idTanaman.size();j++){
                        double nilaiSuhu = parameterSuhu(Double.parseDouble(tanaman[j][1]));
                        double nilaiCurahHujan = parameterCurahHujan(Double.parseDouble(tanaman[j][2]));
                        double nilaiKelembapan = parameterKelembapan(Double.parseDouble(tanaman[j][3]));
                        double nilaiKedalamanTanah = parameterKedalamanTanah(Double.parseDouble(tanaman[j][4]));
                        double nilaiDrainase = parameterDrainase(Double.parseDouble(tanaman[j][5]));


                        hasil += "\n\nData Traning : "+idTanaman.get(j);
                        hasil += "\nNama Tumbuhan : "+tanaman[j][0];
                        hasil += "\nSuhu rerata : "+tanaman[j][1]+" \t-> "+nilaiSuhu;
                        hasil += "\nCurah Hujan : "+tanaman[j][2]+" \t-> "+nilaiCurahHujan;
                        hasil += "\nKelembapan : "+tanaman[j][3]+" \t-> "+nilaiKelembapan;
                        hasil += "\nKedalaman Tanah : "+tanaman[j][4]+" \t-> "+nilaiKedalamanTanah;
                        hasil += "\nDrainase : "+tanaman[j][5]+" \t-> "+nilaiDrainase;
                        hasil += "\nHasil : "+ tanaman[j][6];
                    }

                    i.putExtra("hasil",hasil);
                    startActivity(i);

                }
            }
        });


    }
}
