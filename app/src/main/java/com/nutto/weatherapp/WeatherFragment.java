package com.nutto.weatherapp;

import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
/**import android.os.Handler;**/
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



public class WeatherFragment extends Fragment {
    Typeface weatherFont;
    TextView cityField;
    TextView currentTemperatureField;
    TextView weatherIcon;

    /**
     * Handler handler;
     * <p/>
     * public WeatherFragment(){
     * handler = new Handler();
     * }
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        cityField = (TextView) rootView.findViewById(R.id.city_field);
        currentTemperatureField = (TextView) rootView.findViewById(R.id.current_temperature_field);
        weatherIcon = (TextView) rootView.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "weather.ttf");
        //updateWeatherData();
        new JSONParse().execute();
    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... args) {
            // Getting JSON
            JSONObject json = RemoteFetch.getJSON(getActivity());
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                cityField.setText(json.getString("name").toUpperCase(Locale.US) +
                        ", " +
                        json.getJSONObject("sys").getString("country"));

                JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                JSONObject main = json.getJSONObject("main");

                currentTemperatureField.setText(
                        String.format("%.2f", main.getDouble("temp")) + " ℃");

                setWeatherIcon(details.getInt("id"),
                        json.getJSONObject("sys").getLong("sunrise") * 1000,
                        json.getJSONObject("sys").getLong("sunset") * 1000);

            } catch (Exception e) {
                Log.e("SimpleWeather", "One or more fields not found in the JSON data");
            }

        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

}