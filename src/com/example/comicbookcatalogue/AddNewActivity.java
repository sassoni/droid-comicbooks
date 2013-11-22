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
import android.app.ProgressDialog;
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
				//				ComicBook book = new ComicBook();
				//				book.getInfoFromBarcode(contents);
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
			this.dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			this.dialog.setCancelable(false);
			this.dialog.setMessage("Searching barcode");
			this.dialog.show();
		}
		
		@Override
		protected ComicBook doInBackground(String... params) {

			// Jsoup scraping
			Log.i(TAG, "Barcode to check: " + params[0]);
			Document doc;
			String url = "http://www.comics.org/barcode/" + params[0] + "/";

			ComicBook book = new ComicBook();

			try {

				// need http protocol
				doc = Jsoup.connect(url).get();

				// get page title
				String title = doc.title();
				System.out.println("title : " + title);

				Elements myElems = doc.getElementsByClass("listing_even");

				//				String textss = null;

				if (myElems.size() > 0) {
					Elements e = myElems.get(0).select("a[href*=series]");
					System.out.println("text : " + e.get(0).text());
					book.setTitle(e.get(0).text());
				}
				else {
					Log.i(TAG, "BARCODE NOT FOUND");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return book;
		}

		@Override
		protected void onPostExecute(ComicBook book) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			EditText titleText = (EditText) findViewById(R.id.title_et);
			titleText.setText(book.getTitle());
		}

		@Override
		protected void onProgressUpdate(Void... values) { //show spinner? 
		}
	}


}


//for (Element link : myElems) {

//				System.out.println("text : " + link.text());

// get the value from href attribute
//System.out.println("\nlink : " + link.get.attr("href"));
//System.out.println("text : " + link.text());

//			}


//			// get all links
//			Elements links = doc.select("a[href]");
//			System.out.println("HI!!!!");
//			System.out.println("Size " + links.size());
//			for (Element link : links) {
//	 
//				// get the value from href attribute
//				System.out.println("\nlink : " + link.attr("href"));
//				System.out.println("text : " + link.text());
//	 
//			}
