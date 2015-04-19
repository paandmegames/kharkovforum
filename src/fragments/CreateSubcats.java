package fragments;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import com.gmail.paandmegames.kharkovforum.DBHelper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class CreateSubcats extends AsyncTask<Document, Void, Exception> {

	private static final String LOG_TAG = CreateSubcats.class.getSimpleName();
	
	private static final String CATEGORY = "tcat";
    private static final String SUBCATEGORY = "alt1Active";
    private static final String TAG = "td";
    
    private FragmentActivity context;  
    
    public CreateSubcats(FragmentActivity context) {
    	this.context = context;
    }
	
	@Override
	protected Exception doInBackground(Document... docs) {
		
		DBHelper dbHelper = new DBHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
   		Elements cats = docs[0].select(TAG+"."+CATEGORY+","+TAG+"."+SUBCATEGORY).select("a[href*=forumdisplay]");
   		
   		String main_cat = "";
		
   		for(Element cat : cats) {
			
			if(cat.parent().attr("class").equals(CATEGORY)) {			
				
				main_cat = cat.text();				
			
			} else {
				
				String linkHref = cat.attr("href");
				String linkText = cat.text();
				String[] halfs =  linkHref.split("s=.+&");
				linkHref = halfs[0] + halfs[1];
				
				ContentValues values = new ContentValues();
				values.put("cat", main_cat);
				values.put("title", linkText);
				values.put("link", linkHref);
				db.insert("subcats", null, values);
				
				//Log.d(LOG_TAG, main_cat+"  "+linkText+"    "+linkHref);
			}
		}
		
		db.close();
		return null;
	}
	
	@Override
    protected void onPostExecute(Exception result) {
    	
    	if (result != null) { //If any error occurs
  
    		
    	} else {  //Success

    	/*	context.getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, 
    				new PageFragment()).commit();*/
    		
   
    		
    	}
    
    }

}
