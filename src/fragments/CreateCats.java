package fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gmail.paandmegames.kharkovforum.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class CreateCats extends AsyncTask<String, Void, Exception>{
	
	private static final String LOG_TAG = CreateCats.class.getSimpleName();
    
	private static final String CATEGORY = "tcat";
    private static final String SUBCATEGORY = "alt1Active";
    private static final String TAG = "td";
    
    private FragmentActivity context;
    private Document doc;
    
    public CreateCats(FragmentActivity context) {
    	this.context = context;
    }

	@Override
	protected Exception doInBackground(String... html) {
		
		//Log.d(LOG_TAG, String.valueOf(html[0].length()));
		
		DBHelper dbHelper = new DBHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
   	 	db.execSQL("CREATE TABLE cats (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "title TEXT, link TEXT)");
   	 	db.execSQL("CREATE TABLE subcats (id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "cat TEXT, title TEXT, link TEXT)");

   	 	
   	 	doc = Jsoup.parse(html[0]);
   	 	
   		Elements cats = doc.select(TAG+"."+CATEGORY).select("a[href*=forumdisplay]");
		
   		for(Element cat : cats) {		
			
			String linkHref = cat.attr("href");
			String linkText = cat.text();
			String[] halfs =  linkHref.split("s=.+&");
			linkHref = halfs[0] + halfs[1];
						
			ContentValues values = new ContentValues();
			values.put("title", linkText);
			values.put("link", linkHref);
			db.insert("cats", null, values);

		}
		
		db.close();
		return null;
	}
	

	@Override
    protected void onPostExecute(Exception result) {
    	
    	if (result != null) { //If any error occurs
  
    		
    	} else {  //Success

    //		context.getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, 
    //				new CatsFragment()).commit();
    		
    		new CreateSubcats(context).execute(doc);
    		
    	}
    
    }

}
