package com.example.comicbookcatalogue;

import android.os.AsyncTask;
import android.os.Bundle;
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

	private static final String TAG = "AddNewActivity";
	private static final String ZXING_SCANNER_INTENT = "com.google.zxing.client.android.SCAN";

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
		switch (item.getItemId()) {

		case R.id.menu_scan_barcode:
			Intent intent = new Intent(ZXING_SCANNER_INTENT);
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
				String barcode = data.getStringExtra("SCAN_RESULT");
//				String format = data.getStringExtra("SCAN_RESULT_FORMAT");

				Log.i(TAG, "Barcode scanned: " + barcode);

				if (barcode != null) {
					ComicBookInfoRequest comicBookInfoRequest = new ComicBookInfoRequest();
					comicBookInfoRequest.execute(barcode);
				}
				else {
					showAlert("Sorry, could not find barcode :(");
				}

			} else if (resultCode == RESULT_CANCELED) {
				Log.i(TAG, "Scanning canceled");
			}
		}
	}

	private void showAlert(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(AddNewActivity.this);
		builder.setMessage(message);
		builder.setCancelable(true);
		builder.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private class ComicBookInfoRequest extends AsyncTask<String, Void, ComicBook> {
		
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
				EditText titleText = (EditText) findViewById(R.id.add_new_activity_book_title_ev);
				titleText.setText(book.getTitle());
			}
			else {
				showAlert("Sorry, could not find barcode :(");
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}

}
