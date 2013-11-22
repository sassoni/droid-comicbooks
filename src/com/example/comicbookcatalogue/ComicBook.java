package com.example.comicbookcatalogue;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

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

	}

}




