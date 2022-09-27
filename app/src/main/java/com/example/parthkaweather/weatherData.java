package com.example.parthkaweather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class weatherData {

    Bitmap bmp;
    String temp,icon,city,weatherType;
    int condition;

    public static weatherData fromJSON(JSONObject jsonObject){

        try{
            weatherData wData=new weatherData();
            wData.city=jsonObject.getString("name");
            wData.condition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            wData.weatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            wData.icon=jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
            URL url = new URL("http://openweathermap.org/img/wn/\"+wData.icon+\"@2x.png");
            wData.bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            wData.temp= Integer.toString((int)Math.rint(jsonObject.getJSONObject("main").getDouble("temp")-273.15));

            return wData;
        }
        catch (JSONException | IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public String getTemp(){
        return temp+"°C";
    }
    public String getIcon(){
        return icon;
    }
    public String getCity(){
        return city;
    }
    public String getWeatherType(){
        return weatherType;
    }

}
