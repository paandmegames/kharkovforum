package com.gmail.paandmegames.kharkovforum;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MenuAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private String[] mScreenTitles;
        
        List<Bitmap> icons = new ArrayList<Bitmap>();

        public MenuAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
            mScreenTitles = context.getResources().getStringArray(R.array.screen_array);
           
            // Icons bound to the rows.
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_settings));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_chat));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_collection));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_help));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_add_group));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_collection));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_favorite));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_important));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_go_to_today));
            icons.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_cancel));
        
        
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return mScreenTitles.length;
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate( R.layout.drawer_list_item, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text1);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(mScreenTitles[position]);
           
            holder.icon.setImageBitmap(icons.get(position));

            return convertView;
        }

        static class ViewHolder {
            TextView text;
            ImageView icon;
        }
    

}
