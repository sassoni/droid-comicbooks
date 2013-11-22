package com.example.comicbookcatalogue;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class AddNewActivity extends Activity{

	static final String TAG = "ADD_NEW_ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new);

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_new_activity_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.menu_scan_barcode:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = data.getStringExtra("SCAN_RESULT");
				String format = data.getStringExtra("SCAN_RESULT_FORMAT");
				System.out.println("contents: " + contents);

				ComicBookInfoRequest comicBookInfoRequest = new ComicBookInfoRequest();
				comicBookInfoRequest.execute(contents);

			} else if (resultCode == RESULT_CANCELED) {
				System.out.println("cancel");
			}
		}
	}

	private class ComicBookInfoRequest extends AsyncTask<String, Void, ComicBook>{

		ProgressDialog dialog = new ProgressDialog(AddNewActivity.this);

		@Override
		protected void onPreExecute() {
			
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage("Searching barcode");
			dialog.show();
		}
		
		@Override
		protected ComicBook doInBackground(String... params) {
			
			String barcode = params[0];
			InfoScraper infoScraper = new InfoScraper();
			
			return infoScraper.findBookByBarcode(barcode);
		}

		@Override
		protected void onPostExecute(ComicBook book) {
			
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			if (book != null) {
				EditText titleText = (EditText) findViewById(R.id.title_et);
				titleText.setText(book.getTitle());
			}
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(AddNewActivity.this);
				builder.setMessage("Sorry, could not find barcode :(");
				builder.setCancelable(true);
				builder.setNeutralButton("OK",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
				AlertDialog alert1 = builder.create();
				alert1.show();
			}

		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}


}
