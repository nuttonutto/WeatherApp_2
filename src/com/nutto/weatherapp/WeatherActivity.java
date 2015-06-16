package com.nutto.weatherapp;

import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;

@SuppressWarnings("deprecation")
public class WeatherActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		 
	    if (savedInstanceState == null) {
	        getSupportFragmentManager().beginTransaction()
	                .add(R.id.container, new WeatherFragment())
	                .commit();
	    }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}


	
}
