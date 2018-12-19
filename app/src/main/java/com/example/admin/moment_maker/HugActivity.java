package com.example.admin.moment_maker;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HugActivity extends AppCompatActivity {
    private ListView listview;
    DatabaseReference ref;
    List<UserDetails> userslist;
    UserDetails userDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hug);
        listview=(ListView)findViewById(R.id.list_view);
        ref=FirebaseDatabase.getInstance().getReference("Hugs");
        userslist=new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnap: dataSnapshot.getChildren()) {
                    userDetails = userSnap.getValue(UserDetails.class);
                    userslist.add(userDetails);
                }
                UserAdapter adapter=new UserAdapter(HugActivity.this,userslist);
                listview.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
