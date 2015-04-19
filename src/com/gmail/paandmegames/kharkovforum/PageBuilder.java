package com.gmail.paandmegames.kharkovforum;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.Toast;

public class PageBuilder extends AsyncTask<String, Integer, Exception>{
	
	private WebView webView;
	Document document;
	Elements elements;
	Context context;
	String css;
	
	public PageBuilder(WebView w, Context c) {
		webView = w;
		context = c;
	}
	
    @Override
    protected Exception doInBackground(String... r) {

    	String[] s1 = r[0].split("<!-- CSS Stylesheet -->");
    	String[] s2 = s1[1].split("<!-- / CSS Stylesheet -->");
    	css = s2[0];
    	
    try {

    	String[] s3 = s2[1].split("<!-- sub-forum list  -->");
    	String[] s4 = s3[1].split("<!-- / sub-forum list  -->");
    	
    	document = Jsoup.parse(s4[0]);
    	document.select("table.tborder").first().remove();
    	document.select("table.tborder").first().select("td.thead").last().remove();
    	document.select("table.tborder").first().select("td.thead").last().remove();

    	for(Element el : document.select("tbody")) {
    		el.select("td.alt2").last().remove();
    		el.select("td.alt1").last().remove();    		
    	}

    	for(Element el : document.select("a[href]")) {
    		String linkHref = el.attr("href");
			String[] parts =  linkHref.split("s=.+&");
			linkHref = parts[0] + parts[1];
			el.attr("href", linkHref);
    	}

    	} catch (Exception e) {
    		try {

    	    	String[] s3 = s2[1].split("<!-- threads list  -->");
    	    	String[] s4 = s3[1].split("<!-- end show threads -->");
    	    	
    	    	document = Jsoup.parse(s4[0]);

    		} catch (Exception e1) {
    			try {
    		    	
    		    	document = Jsoup.parse(s2[1]);
    		    	elements = document.select("div#posts");
    		    	
    			} catch (Exception e2) {
    				return e;
    			}
    		}
    	}

    	return null;
    }

    @Override
    protected void onPostExecute(Exception result) {
    	if(result == null && elements != null) 
    		webView.loadDataWithBaseURL(null, css + elements.html(), "text/html", "UTF-8", null);
    	//else Toast.makeText(context, "Необходима авторизация", Toast.LENGTH_SHORT).show();
    	else if(result == null)
    		webView.loadDataWithBaseURL(null, css + document.html(), "text/html", "UTF-8", null);
    }
}
