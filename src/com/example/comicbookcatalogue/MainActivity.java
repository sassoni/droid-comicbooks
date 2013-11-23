package com.example.comicbookcatalogue;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.menu_add_new:
			Intent myIntent = new Intent(MainActivity.this, AddNewActivity.class);
			startActivity(myIntent);
			return true;
		
		case R.id.menu_settings:
			// nothing yet
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
