package com.example.comicbookcatalogue;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button scanBtn = (Button) findViewById(R.id.button1);
		scanBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
			}
		});

		//		public int onActivityResult(int requestCode, int resultCode, Intent intent) {
		//		    if (requestCode == 0) {
		//		        if (resultCode == RESULT_OK) {
		//		            String contents = intent.getStringExtra("SCAN_RESULT");
		//		            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		//		            return 1;
		//		        } else if (resultCode == RESULT_CANCELED) {
		//		            return 0;
		//		        }
		//		    }
		//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = data.getStringExtra("SCAN_RESULT");
				String format = data.getStringExtra("SCAN_RESULT_FORMAT");
				System.out.println("contents: " + contents);
				ComicBook book = new ComicBook();
				book.getInfoFromBarcode(contents);
			} else if (resultCode == RESULT_CANCELED) {
				System.out.println("cancel");
			}
		}
	}

	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		// Inflate the menu; this adds items to the action bar if it is present.
	//		getMenuInflater().inflate(R.menu.main, menu);
	//		return true;
	//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.menu_add_new:
			Intent myIntent = new Intent(MainActivity.this, AddNewComicBookActivity.class);
			//myIntent.putExtra("key", value); //Optional parameters
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
