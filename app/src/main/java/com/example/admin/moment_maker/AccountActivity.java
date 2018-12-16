package com.example.admin.moment_maker;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    ImageView profilePic;
    TextView username,createhug;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Glide glide;
    private android.support.v7.widget.Toolbar mtoolbar;
    String displayName;
    Uri displayPic;
    ImageButton btn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(AccountActivity.this,LoginActivity.class));
                }
            }
        };
        mtoolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("MomentMaker");
        getSupportActionBar().setSubtitle("Hi,"+username);
        profilePic=(ImageView)findViewById(R.id.profile);
        username=(TextView)findViewById(R.id.gUsername);
        createhug=(TextView)findViewById(R.id.texthug);
        btn=(ImageButton)findViewById(R.id.imageButton);

        displayName=getIntent().getStringExtra("uname").toString();

        displayPic= Uri.parse(getIntent().getStringExtra("profilepic"));
        username.setText(displayName);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AccountActivity.this,DescriptionActivity.class);
                startActivity(i);
            }
        });
        Glide.with(this).load(displayPic).into(profilePic);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.main_logout){

            mAuth.signOut();
        }
        return true;
    }
}
