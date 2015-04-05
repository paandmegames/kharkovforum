package com.gmail.paandmegames.kharkovforum;

import java.util.ArrayList;
import java.util.List;



import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CatsFragment extends ListFragment {
	
        private static final String TAG = CatsFragment.class.getSimpleName();

		@Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            
    		DBHelper dbHelper = new DBHelper(getActivity());
    		SQLiteDatabase db = dbHelper.getReadableDatabase();

    		Cursor c = db.rawQuery("SELECT title FROM cats", null);
            String[] cats = new String[c.getCount()];
      
            int i=0;
            if(c.moveToFirst()) {
            	while(!c.isAfterLast()) {
            		// Log.d(TAG, String.valueOf(i));
            		cats[i] = c.getString(c.getColumnIndex("title"));
            		 c.moveToNext();
            		i++;
            		
            	}   		
            }
            
            dbHelper.close();
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, cats));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
           // Log.d(TAG, "Item clicked: " + id);
			SubcatsFragment page = new SubcatsFragment(position);

			//page.setArguments(getIntent().getExtras());
			
			getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_content, page).commit();
        }
    

}
