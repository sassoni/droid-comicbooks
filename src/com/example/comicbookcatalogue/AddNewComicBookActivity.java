package com.example.comicbookcatalogue;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddNewComicBookActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_comicbook);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.edit_details, menu);
//		return true;
//	}

}
