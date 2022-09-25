package com.example.legalconsultant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    TextView wel;
    private static int Splash_timeout = 5000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView lc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Animation
       topAnim=AnimationUtils.loadAnimation(this,R.anim.top_animation);
     bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        lc = findViewById(R.id.textview1);
        image = findViewById(R.id.imageView2);
        lc.setAnimation(bottomAnim);
        Glide.with(this).load(R.raw.lawyerbalance).into(image);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }, Splash_timeout);

    }

}