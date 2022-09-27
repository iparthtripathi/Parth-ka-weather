package com.example.parthkaweather;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_check);
        ImageView imageView=findViewById(R.id.imageView3);

        String url1="https://openweathermap.org/img/w/04d.png";

        try {
            URL url = new URL(url1);
            Bitmap b=BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(Bitmap.createScaledBitmap(b,240,240,false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//                    String icon = response.getJSONArray("weather").getJSONObject(0).getString("icon");
//                    url1="http://openweathermap.org/img/w/" + icon + ".png";
//                    //addImage();
//
//                    try {
//                        URL url = new URL(url1);
//                        Bitmap bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                        weather.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 245, 244, false));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


//    private void addImage() throws IOException {
//        URL url = new URL(url1);
//        Bitmap bitmap= BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        weather.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 245, 244, false));
//    }