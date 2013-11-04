package com.example.comicbookcatalogue;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ComicBook {

	private String title;

	public ComicBook(){

	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle(){
		return title; 
	}

	public void getInfoFromBarcode(String barcode) {
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
			}
			else {
				System.out.println("BARCODE NOT FOUND");
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


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
