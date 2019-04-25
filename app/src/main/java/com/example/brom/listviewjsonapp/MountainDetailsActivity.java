package com.example.brom.listviewjsonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class MountainDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_details);

        ImageView Bild;
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String Title = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);
        String bild = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        String url = intent.getStringExtra(MainActivity.EXTRA_MESSAGE4);
        TextView textView = findViewById(R.id.andra);
        TextView textView2 = findViewById(R.id.namn);
        TextView textView3 = findViewById(R.id.lank);
        textView.setText(message);
        textView2.setText(Title);
        textView3.setText(url);
        setTitle(Title);
        Bild = (ImageView)findViewById(R.id.imageView);

        new DownloadImageTask(Bild) .execute(bild);




    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

