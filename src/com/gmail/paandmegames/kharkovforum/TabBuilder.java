package com.gmail.paandmegames.kharkovforum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TabBuilder {
	
	private static final int TOTAL_PAGES = 9;
	private static final String HEADER_REQUEST = "http://www.kharkovforum.com/forumdisplay.php?f=";
	
	private static final String[] CATEGORIES = {
		"Харьков",
		"Бизнес, налоги, право",
		"Женский BAZAAR",
		"Харьков Балка",
		"Hi-Tech...",
		"Хобби",
		"Человек",
		"Черный и белый список Харькова",
		"Форум"
	};
	private static final String[] CAT_REQUESTS = {
		"56",
		"121",
		"183",
		"4",
		"50",
		"59",
		"55",
		"65",
		"47"
	};
	private static final String[] STYLES = {
		"&styleid=32", //светлый для планшетов
		"&styleid=22", //мобильный
		"&styleid=29", //светлый без кеширования
		"&styleid=8",  //white traffic low
		"&styleid=17", //светлый
		"&styleid=15", //темный
		"&styleid=28", //темный без кеширования
		"&styleid=31", //темный для планшетов
		"&styleid=12"  //black traffic low
	};

	public static class TabView extends Fragment {

	    private SlidingTabLayout mSlidingTabLayout;
	    private ViewPager mViewPager;

	    public TabView() {
	    }

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {

	        View rootView = inflater.inflate(R.layout.tab_scrolls, container, false);

	        return rootView;
	    }
	    
	    @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
	        // Get the ViewPager and set it's PagerAdapter so that it can display items
	        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
	        mViewPager.setAdapter(new SamplePagerAdapter());

	        // Give the SlidingTabLayout the ViewPager, this must be 
	        // done AFTER the ViewPager has had it's PagerAdapter set.
	        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
	        mSlidingTabLayout.setViewPager(mViewPager);
	    }

	    // Adapter
	    class SamplePagerAdapter extends PagerAdapter {

	        /**
	         * Return the number of pages to display
	         */
	        @Override
	        public int getCount() {
	            return CATEGORIES.length;
	        }

	        /**
	         * Return true if the value returned from is the same object as the View
	         * added to the ViewPager.
	         */
	        @Override
	        public boolean isViewFromObject(View view, Object o) {
	            return o == view;
	        }

	        /**
	         * Return the title of the item at position. This is important as what
	         * this method returns is what is displayed in the SlidingTabLayout.
	         */
	        @Override
	        public CharSequence getPageTitle(int position) {
	            return CATEGORIES[position];
	        }

	        /**
	         * Instantiate the View which should be displayed at position. Here we
	         * inflate a layout from the apps resources and then change the text
	         * view to signify the position.
	         */
	        @Override
	        public Object instantiateItem(ViewGroup container, int position) {
	            // Inflate a new layout from our resources
	            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
	                    container, false);
	            // Add the newly created View to the ViewPager
	            container.addView(view);
	            WebView w = (WebView)view.findViewById(R.id.webView);
	            WebSettings webSettings = w.getSettings();
	    		webSettings.setJavaScriptEnabled(true);
	    		
	            w.setWebViewClient(new WebViewClient() {
	            	@Override
	            	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	            sendRequest("http://www.kharkovforum.com/" + url + STYLES[0], getActivity(), view);
	            		return true;
	            	}
	            });
		
	            sendRequest(HEADER_REQUEST + CAT_REQUESTS[position] + STYLES[0], getActivity(), w);


	            return view;
	        }

	        /**
	         * Destroy the item from the ViewPager. In our case this is simply
	         * removing the View.
	         */
	        @Override
	        public void destroyItem(ViewGroup container, int position, Object object) {
	            container.removeView((View) object);
	        }
	    }	
	
	}


	
	/**
     * Network connection
     * @param url
     */
    public static void sendRequest(String url, Context c, WebView w) {
    	
    	final WebView webView = w;
    	final Context context = c;
		Network network = new BasicNetwork(new HurlStack());
		
		RequestQueue mRequestQueue;

		// Instantiate the cache
		Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap


		// Instantiate the RequestQueue with the cache and network.
		mRequestQueue = new RequestQueue(cache, network);

		// Start the queue
		mRequestQueue.start();

		// Formulate the request and handle the response.
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
		        new Response.Listener<String>() {
		    
			@Override
		    public void onResponse(String response) {
				
				new PageBuilder(webView, context).execute(response);
	      
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

	
	/**
	 * CATEGORIES
	 * 
	 *
	 */

   /* public static class KharkovCat extends Fragment {

 
        public KharkovCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            return inflater.inflate(R.layout.kharkov_cat, container, false);
        }
    }
    
    public static class BusinessCat extends Fragment {

    	 
        public BusinessCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.business_cat, container, false);
        }
    }
    
    public static class FemaleBazaarCat extends Fragment {

   	 
        public FemaleBazaarCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.female_bazaar_cat, container, false);
        }
    }
    
    public static class BalkaCat extends Fragment {

   	 
        public BalkaCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.balka_cat, container, false);
        }
    }
    
    public static class HitechCat extends Fragment {

   	 
        public HitechCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.hitech_cat, container, false);
        }
    }
    
    public static class HobbyCat extends Fragment {

   	 
        public HobbyCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.hobby_cat, container, false);
        }
    }
    
    public static class HumanCat extends Fragment {

   	 
        public HumanCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.human_cat, container, false);
        }
    }
    
    public static class BlackWhiteListCat extends Fragment {

   	 
        public BlackWhiteListCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.black_white_list_cat, container, false);
        }
    }
    
    public static class ForumCat extends Fragment {

   	 
        public ForumCat() {
        }
 
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	
            return inflater.inflate(R.layout.forum_cat, container, false);
        }
    }*/
    
    







 
	
	
}
