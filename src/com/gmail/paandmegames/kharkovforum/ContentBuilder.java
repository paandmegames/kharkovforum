package com.gmail.paandmegames.kharkovforum;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.Toast;

public class ContentBuilder extends AsyncTask<String, Integer, Exception>{
	
	private WebView webView;
	Document document;
	Elements elements;
	Context context;
	String css;
	
	public ContentBuilder(WebView w, Context c) {
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

    	for(Element columns : document.select("td.alt1")) {
    		columns.nextElementSibling().remove();
    		columns.remove();    		
    	}

    	for(Element links : document.select("a[href]")) {
    		String linkHref = links.attr("href");
			String[] parts =  linkHref.split("s=.+&");
			linkHref = "http://www.kharkovforum.com/" + parts[0] + parts[1];
			links.attr("href", linkHref);
    	}

    	} catch (Exception e) {
    		try {

    	    	String[] s3 = s2[1].split("<!-- threads list  -->");
    	    	String[] s4 = s3[1].split("<!-- end show threads -->");
    	    	String[] s5 = s4[0].split("controls above thread list");
    	    	
    	    	document = Jsoup.parse(s5[0] + s5[2]);
    	    	document.select("table").first().remove();
    	    	
    	    	for (Element rows : document.select("tr")) {
    	    		rows.select("td").last().remove();
    	    		rows.select("td").last().remove();
    	    	}
    	    	
    	    	for(Element links : document.select("a[href]")) {
    	    		links.attr("href", "http://www.kharkovforum.com/" + links.attr("href"));
    	    	}


    		} catch (Exception e1) {
    			try {
    		    	
    		    	document = Jsoup.parse(s2[1]);
    		    	elements = document.select("div#posts");

        	    	
    		    	for(Element table : elements.select("table.tborder")) {
    		    		table.select("tr").first().nextElementSibling().select("td").first().attr("width", "50");
    		    	}
    		    	
    		    	
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
