package com.gmail.paandmegames.kharkovforum;

import java.net.HttpURLConnection;

import org.apache.http.client.HttpClient;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {


	private HttpStack stack;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_main);
		final TextView mTextView = (TextView) findViewById(R.id.result_text);
		
		
		
	
		// If the device is running a version >= Gingerbread...
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		    // ...use HttpURLConnection for stack.
			stack = new HurlStack();
		} else {
		    // ...use AndroidHttpClient for stack.
			stack = new HttpClientStack(AndroidHttpClient.newInstance("User-Agent"));
		}
		Network network = new BasicNetwork(stack);
		
		RequestQueue mRequestQueue;

		// Instantiate the cache
		Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap


		// Instantiate the RequestQueue with the cache and network.
		mRequestQueue = new RequestQueue(cache, network);

		// Start the queue
		mRequestQueue.start();

		String url ="http://www.google.com";
		
		// Formulate the request and handle the response.
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
		        new Response.Listener<String>() {
		    @Override
		    public void onResponse(String response) {
		    	 mTextView.setText("Response is: "+ response.substring(0,500));

		    }
		},
		    new Response.ErrorListener() {
		        @Override
		        public void onErrorResponse(VolleyError error) {
		        	mTextView.setText("That didn't work!");

		    }
		});

		// Add the request to the RequestQueue.
		mRequestQueue.add(stringRequest);


	}
	

	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
