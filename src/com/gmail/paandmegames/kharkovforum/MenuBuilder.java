package com.gmail.paandmegames.kharkovforum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MenuBuilder {
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

	

	public static Fragment createFragment(int position) {
		Fragment fragment;
		switch (position) {
			case 0:
				fragment = new KharkovCat();
				return fragment;
			case 1:
				fragment = new BusinessCat();
				return fragment;
			case 2:
				fragment = new FemaleBazaarCat();
				return fragment;
			case 3:
				fragment = new BalkaCat();
				return fragment;
			case 4:
				fragment = new HitechCat();
				return fragment;
			case 5:
				fragment = new HobbyCat();
				return fragment;
			case 6:
				fragment = new HumanCat();
				return fragment;
			case 7:
				fragment = new BlackWhiteListCat();
				return fragment;
			case 8:
				fragment = new ForumCat();
				return fragment;
		}

		return null;
	}
	

	public static String getTitle(int position) {
		// TODO Auto-generated method stub
		return CATEGORIES[position];
	}
	
	public static String getRequest(int position) {
		
		return HEADER_REQUEST + CAT_REQUESTS[position];
	}
	


	public static int getCount() {
		// TODO Auto-generated method stub
		return CATEGORIES.length;
	} 
	
	
	
	/**
	 * CATEGORIES
	 * @author Олег
	 *
	 */

    public static class KharkovCat extends Fragment {

 
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
    }
    
}
