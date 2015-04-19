package fragments;

import com.gmail.paandmegames.kharkovforum.DBHelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubcatsFragment extends ListFragment {
	
    private static final String TAG = SubcatsFragment.class.getSimpleName();

    private int position;
    private String cat;
    
    public SubcatsFragment(int position) {
    	this.position = position+1;
    }
    
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
		DBHelper dbHelper = new DBHelper(getActivity());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor catsCursor = db.rawQuery("SELECT title FROM cats WHERE id='"+position+"'", null);
		if(catsCursor.moveToFirst()) 
			cat = catsCursor.getString(catsCursor.getColumnIndex("title"));
        
		Cursor subcatsCursor = db.rawQuery("SELECT title FROM subcats WHERE cat='"+cat+"'", null);
        
		String[] subcats = new String[subcatsCursor.getCount()];
        //Log.d(TAG, String.valueOf(subcatsCursor.getCount()));
        
        int i=0;
        if(subcatsCursor.moveToFirst()) {
        	while(!subcatsCursor.isAfterLast()) {
        		//Log.d(TAG, String.valueOf(i));
        		 subcats[i] = subcatsCursor.getString(subcatsCursor.getColumnIndex("title"));
        		 subcatsCursor.moveToNext();
        		i++;
        		
        	}   		
        }
        
        dbHelper.close();
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, subcats));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Log.i("FragmentList", "Item clicked: " + id);
    }
}
