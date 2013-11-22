package com.example.comicbookcatalogue;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class InfoScraper {

	static final String TAG = "INFO_SCRAPER";

	ComicBook book;

	public InfoScraper() {
		book = new ComicBook();
	}

	public ComicBook findBookByBarcode(String barcode) {

		// Jsoup scraping
		Log.i(TAG, "Barcode to check: " + barcode);
		Document doc;
		String url = "http://www.comics.org/barcode/" + barcode + "/";

		try {

			// need http protocol
			doc = Jsoup.connect(url).get();

			// get page title
			String title = doc.title();
			System.out.println("title : " + title);

			Elements myElems = doc.getElementsByClass("listing_even");

			if (myElems.size() > 0) {
				Elements e = myElems.get(0).select("a[href*=series]");
				System.out.println("text : " + e.get(0).text());
				book.setTitle(e.get(0).text());

				return book;
			}
			else {
				Log.i(TAG, "BARCODE NOT FOUND");
				return null;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}


//for (Element link : myElems) {

//System.out.println("text : " + link.text());

//get the value from href attribute
//System.out.println("\nlink : " + link.get.attr("href"));
//System.out.println("text : " + link.text());

//}


//// get all links
//Elements links = doc.select("a[href]");
//System.out.println("HI!!!!");
//System.out.println("Size " + links.size());
//for (Element link : links) {
//
//// get the value from href attribute
//System.out.println("\nlink : " + link.attr("href"));
//System.out.println("text : " + link.text());
//
//}

