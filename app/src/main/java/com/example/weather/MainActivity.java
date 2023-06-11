package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ImageView imageView;
    TextView temptv, time, longitude, latitude, humidity, sunrise, sunset, pressure, wind, country, city_nam, max_temp, min_temp, feels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);
        temptv = findViewById(R.id.textView3);
        time = findViewById(R.id.textView2);

        longitude = findViewById(R.id.longitude);
        latitude = findViewById(R.id.latitude);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure = findViewById(R.id.pressure);
        wind = findViewById(R.id.wind);
        country = findViewById(R.id.country);
        city_nam = findViewById(R.id.city_nam);
        max_temp = findViewById(R.id.temp_max);
        min_temp = findViewById(R.id.min_temp);
        feels = findViewById(R.id.feels);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FindWeather();
            }
        });

    }
    public void FindWeather()
    {
        final String city = editText.getText().toString();
        String url ="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("main");
                            double temp = object.getDouble("temp");
                            temptv.setText("Temperature\n"+temp+"°C");

                            JSONObject object8 = jsonObject.getJSONObject("sys");
                            String count = object8.getString("country");
                            country.setText(count+"  :");

                            String city = jsonObject.getString("name");
                            city_nam.setText(city);

                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject obj = jsonArray.getJSONObject(0);
                            String icon = obj.getString("icon");
                            Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat std = new SimpleDateFormat("HH:mm a \nE, MMM dd yyyy");
                            String date = std.format(calendar.getTime());
                            time.setText(date);

                            JSONObject object2 = jsonObject.getJSONObject("coord");
                            double lat_find = object2.getDouble("lat");
                            latitude.setText(lat_find+"°  N");

                            JSONObject object3 = jsonObject.getJSONObject("coord");
                            double long_find = object3.getDouble("lon");
                            longitude.setText(long_find+"°  E");

                            JSONObject object4 = jsonObject.getJSONObject("main");
                            int humidity_find = object4.getInt("humidity");
                            humidity.setText(humidity_find+"  %");

                            JSONObject object5 = jsonObject.getJSONObject("sys");
                            String sunrise_find = object5.getString("sunrise");
                            sunrise.setText(sunrise_find+"  am");

                            JSONObject object6 = jsonObject.getJSONObject("sys");
                            String sunset_find = object6.getString("sunset");
                            sunset.setText(sunset_find+"  pm");

                            JSONObject object7 = jsonObject.getJSONObject("main");
                            String pressure_find = object7.getString("pressure");
                            pressure.setText(pressure_find+"  hPa");

                            JSONObject object9 = jsonObject.getJSONObject("wind");
                            String wind_find = object9.getString("speed");
                            wind.setText(wind_find+"  km/h");

                            JSONObject object10 = jsonObject.getJSONObject("main");
                            double mintemp = object10.getDouble("temp_min");
                            min_temp.setText("Min Temp\n"+mintemp+" °C");

                            JSONObject object12 = jsonObject.getJSONObject("main");
                            double maxtemp = object12.getDouble("temp_max");
                            max_temp.setText("Max Temp\n"+maxtemp+" °C");

                            JSONObject object13 = jsonObject.getJSONObject("main");
                            double feels_find = object13.getDouble("feels_like");
                            feels.setText(feels_find+" °C");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Unable to Find the Location", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

}