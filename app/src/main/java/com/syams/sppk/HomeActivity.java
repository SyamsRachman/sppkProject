package com.syams.sppk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
//    private DatabaseReference mDatabase2;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] myDataset;
    ArrayList<String> idtanaman = new ArrayList<>();
//    ArrayList<String> tanaman = new ArrayList<>();
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        String email = i.getStringExtra("email");

        TextView txt_welcome = (TextView) findViewById(R.id.txt_welcome);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("dataTraining");
//        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("dataTraining");

        Button btn_logout = (Button) findViewById(R.id.btn_logout);
        Button btn_tambah = (Button) findViewById(R.id.btn_tambahData);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_dataset);
//        myDataset = (String[]) idtanaman.toArray();


        txt_welcome.setText("Selamat datang " + email);

        mRecyclerView.setLayoutManager(new LinearLayoutManager( this));
        adapter = new Adapter(this,idtanaman);
        mRecyclerView.setAdapter(adapter);


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getKey();
                idtanaman.add(value);
//                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("dataTraining").child(value);
                adapter.notifyDataSetChanged();

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

//        mRecyclerView

    }

    @Override
    public void onBackPressed() {
//        finish();
//        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
