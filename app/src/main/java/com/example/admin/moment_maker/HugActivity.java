package com.example.admin.moment_maker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HugActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UserAdapter adapter;
    DatabaseReference ref;
    List<UserDetails> userslist;
    private ProgressBar mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);
        mRecyclerView=(RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref=FirebaseDatabase.getInstance().getReference("Hugs");
        mprogress=(ProgressBar)findViewById(R.id.progressbar2);
        userslist=new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnap:dataSnapshot.getChildren()){
                    UserDetails userDetails = postSnap.getValue(UserDetails.class);
                    userslist.add(userDetails);
                }

                adapter=new UserAdapter(HugActivity.this,userslist);
                mRecyclerView.setAdapter(adapter);
                mprogress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(HugActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mprogress.setVisibility(View.INVISIBLE);
            }
        });

    }

}
