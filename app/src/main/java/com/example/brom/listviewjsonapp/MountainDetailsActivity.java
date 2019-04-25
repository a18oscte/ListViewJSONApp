package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MountainDetailsActivity extends AppCompatActivity {

    private ImageView Bild;
    private int vBild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_details);


        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //String Title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        TextView textView = findViewById(R.id.namn);
        //textView.setText(message);
        //setTitle(Title);
        Bild = (ImageView)findViewById(R.id.bild);

        /*if(Title.equals("Kebnekaise")){
            vBild = R.drawable.kebenikasie;
        }else if(Title.equals("Matterhorn")){
            vBild = R.drawable.matterhorn;
        }else if(Title.equals("Mont Blanc")){
            vBild = R.drawable.montblanc;
        }else if(Title.equals("Denali")){
            vBild = R.drawable.denali;
        }

        Bild.setImageResource(vBild);*/

    }
}

