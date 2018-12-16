package com.example.admin.moment_maker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout l1,l2;
    Button startbutton;

    Animation uptodown,downtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startbutton=(Button)findViewById(R.id.startBtn);
        l1=(LinearLayout)findViewById(R.id.l1);
        l2=(LinearLayout)findViewById(R.id.l2);

        uptodown=AnimationUtils.loadAnimation(this,R.anim.uptodown);
        l1.setAnimation(uptodown);
        downtoup=AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l2.setAnimation(downtoup);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(login);
            }
        });
    }
}
