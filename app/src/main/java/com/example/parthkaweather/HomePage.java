package com.example.parthkaweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;

public class HomePage extends AppCompatActivity {


    private static LottieAnimationView weather;
    final String apiKey = "c9e42dc2e27e2cf81701fd143aafd85c";
    final String weatherURL = "https://api.openweathermap.org/data/2.5/weather";
    final long minTime = 5000;
    final float minDist = 1000;
    final int request = 101;

    String url1;
    String Location = LocationManager.GPS_PROVIDER;

    TextView cityTextView, tempTextView, feelsTextView, tempMaxTextView, tempMinTextView,sunriseTextView,sunsetTextView,rainTextView,weatherTextView;
    Button addLocButton;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_home_page);

        cityTextView=findViewById(R.id.cityTextView);
        tempTextView=findViewById(R.id.tempTextView);
        feelsTextView=findViewById(R.id.feelTextView);
        tempMaxTextView=findViewById(R.id.tempMaxTextView);
        tempMinTextView=findViewById(R.id.tempMinTextView);
        sunriseTextView=findViewById(R.id.sunrise);
        sunsetTextView=findViewById(R.id.sunset);
        rainTextView=findViewById(R.id.rainTextView);
        weatherTextView=findViewById(R.id.weatherTextView);
        weather=findViewById(R.id.weatherIcon);
        addLocButton=findViewById(R.id.addLocButton);

        //weather.setAnimationFromUrl("https://assets3.lottiefiles.com/packages/lf20_dw8rzsix.json");

        addLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, searchPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent1 = getIntent();
        String city = intent1.getStringExtra("City");
        if(city!=null){
            getWeatherParticular(city);
        }
        else {
            getWeather();
        }
    }

    private FusedLocationProviderClient fusedLocationClient;

    public void getWeatherParticular(String city){

        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",apiKey);
        apicall(params);
    }

    public void getWeather() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            String latitude = String.valueOf(location.getLatitude());
                            String longitude = String.valueOf(location.getLongitude());
                            RequestParams params = new RequestParams();
                           // Toast.makeText(HomePage.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();
                            params.put("lat", latitude);
                            params.put("lon", longitude);
                            params.put("appid", apiKey);
                            apicall(params);
                        }
                    }
                });


    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void getWeather1() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull android.location.Location location) {

                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                RequestParams params=new RequestParams();
               // Toast.makeText(HomePage.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();
                params.put("Lat",latitude);
                params.put("Long",longitude);
                params.put("API_KEY",apiKey);
                apicall(params);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request);
            return;
        }
        locationManager.requestLocationUpdates(Location, minTime, minDist, locationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==request){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(HomePage.this, "Location Accessed", Toast.LENGTH_SHORT).show();
                getWeather();
            }
            else{
                //PERMISSION DENIED
            }
        }
    }

    public void apicall(RequestParams params){
        AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
        asyncHttpClient.get(weatherURL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);

                Toast.makeText(HomePage.this, "Data Accessed", Toast.LENGTH_SHORT).show();
               // weatherData wData=weatherData.fromJSON(response);
                try {
                    Double temp = response.getJSONObject("main").getDouble("temp")-273.15;
                    Double feelsLike=response.getJSONObject("main").getDouble("feels_like")-273.15;
                    Double tempMax=response.getJSONObject("main").getDouble("temp_max")-273.15;
                    Double tempMin=response.getJSONObject("main").getDouble("temp_min")-273.15;
                    String city = response.getString("name");
                    int humidity=response.getJSONObject("main").getInt("humidity");
                    long sunrise=response.getJSONObject("sys").getLong("sunrise");
                    long sunset=response.getJSONObject("sys").getLong("sunset");
                    int condition=response.getJSONArray("weather").getJSONObject(0).getInt("id");
                    String weather=response.getJSONArray("weather").getJSONObject(0).getString("description");

                    String riseTime=calculatetime(sunrise+19800);
                    String setTime=calculatetime(sunset+19800);
                    tempTextView.setText(Integer.toString((int)Math.rint(temp))+"°C");
                    feelsTextView.setText("Feels Like "+Integer.toString((int)Math.rint(feelsLike))+"°C");
                    tempMaxTextView.setText("Maximum Temperature "+Integer.toString((int)Math.rint(tempMax))+"°C");
                    tempMinTextView.setText("Minimum Temperature "+Integer.toString((int)Math.rint(tempMin))+"°C");
                    sunriseTextView.setText("Sunrise "+riseTime+" IST");
                    sunsetTextView.setText("Sunset "+setTime+" IST");
                    rainTextView.setText(humidity+"%");
                    weatherTextView.setText(weather);
                    cityTextView.setText(city);
                    updateWeatherIcon(condition);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // updateUI(wData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(HomePage.this, "Data Not Accessed "+errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String calculatetime(long seconds) {
        String ans="";
        int hours, minutes, secondss, extraTime;

        extraTime = (int) (seconds % (24 * 60 * 60));

        hours = extraTime / 3600;
        minutes = (extraTime % 3600) / 60;
        secondss = (extraTime % 3600) % 60;
        ans += String.valueOf(hours);
        ans += ":";
        ans += String.valueOf(minutes);
        ans += ":";
        ans += String.valueOf(secondss);

        return ans;
    }


    private static void updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            weather.setAnimationFromUrl("https://assets3.lottiefiles.com/packages/lf20_6rza2vis.json");
        }
        else if(condition>=300 && condition<=500)
        {
            weather.setAnimationFromUrl("https://assets1.lottiefiles.com/packages/lf20_mhlhglws.json");
        }
        else if(condition>=500 && condition<=600)
        {
            weather.setAnimationFromUrl("https://assets3.lottiefiles.com/private_files/lf30_LPtaP2.json");
        }
        else  if(condition>=600 && condition<=700)
        {
            weather.setAnimationFromUrl("https://assets5.lottiefiles.com/temp/lf20_RHbbn6.json");
        }
        else if(condition>=701 && condition<=771)
        {
            weather.setAnimationFromUrl("https://assets5.lottiefiles.com/temp/lf20_HflU56.json");
        }

        else if(condition>=772 && condition<800)
        {
            weather.setAnimationFromUrl("https://assets6.lottiefiles.com/packages/lf20_1eaisi3u.json");
        }
        else if(condition==800)
        {
            weather.setAnimationFromUrl("https://assets5.lottiefiles.com/packages/lf20_jqfghjiz.json");
        }
        else if(condition>=801 && condition<=804)
        {
            weather.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_trr3kzyu.json");
        }
        else  if(condition>=900 && condition<=902)
        {
            weather.setAnimationFromUrl("https://assets3.lottiefiles.com/packages/lf20_6rza2vis.json");
        }
        if(condition==903)
        {
            weather.setAnimationFromUrl("https://assets1.lottiefiles.com/temp/lf20_WtPCZs.json");
        }
        if(condition==904)
        {
            weather.setAnimationFromUrl("https://assets5.lottiefiles.com/packages/lf20_jqfghjiz.json");
        }
        if(condition>=905 && condition<=1000)
        {
            weather.setAnimationFromUrl("https://assets10.lottiefiles.com/private_files/lf30_LPtaP2.json");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(locationManager!=null){
            locationManager.removeUpdates(locationListener);
        }
    }
}