package com.gmail.paandmegames.kharkovforum;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

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
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private CreateCats parser = null;
	


	private static final String TAG = MainActivity.class.getSimpleName();
    
	
	
    private boolean first_run = false;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        final ActionBar bar = getActionBar();
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        bar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO, ActionBar.DISPLAY_USE_LOGO);
		
        setContentView(R.layout.activity_main);
		
		final TextView resultView = (TextView) findViewById(R.id.result_text);
	
		//this.deleteDatabase("KharkovForum.db");

		dbHelper = new DBHelper(this);
		db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select name from sqlite_master where type = 'table'", null);

		if(c.getCount() == 1) {
			Toast.makeText(this, "Создается база данных, подождите", Toast.LENGTH_LONG).show();
			first_run = true;
			sendRequest("http://www.kharkovforum.com");
			return;
		}

		dbHelper.close();	
        
		if(findViewById(R.id.fragment_content) != null) {
			if(savedInstanceState != null) {
				return;
			}
			
			CatsFragment page = new CatsFragment();

			//page.setArguments(getIntent().getExtras());
			
			getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_content, page).commit();
		}

	}

	
	private void sendRequest(String url) {

		Network network = new BasicNetwork(new HurlStack());
		
		RequestQueue mRequestQueue;

		// Instantiate the cache
		Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap


		// Instantiate the RequestQueue with the cache and network.
		mRequestQueue = new RequestQueue(cache, network);

		// Start the queue
		mRequestQueue.start();

		// Formulate the request and handle the response.
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
		        new Response.Listener<String>() {
		    
			@Override
		    public void onResponse(String response) {
		    	
				Log.d(TAG, String.valueOf(response.length()));
				if(first_run) new CreateCats(MainActivity.this).execute(response);

		    }
		},
		    new Response.ErrorListener() {
		        @Override
		        public void onErrorResponse(VolleyError error) {
		        	//mTextView.setText("That didn't work!");

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
		switch (id) { 
			case R.id.action_authorization:
				break;
			case R.id.action_my_cabinet:
				break;
			case R.id.action_search:
				break;
			case R.id.action_notes:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
