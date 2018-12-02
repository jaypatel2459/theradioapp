package com.theradioapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.theradioapp.R;
import com.theradioapp.TinyDB;
import com.theradioapp.activity.MainActivity;
import com.theradioapp.constants.AppConstatnts;
import com.theradioapp.models.RecentSongs;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<RecentSongs> items; //data source of the list adapter
    TinyDB tinyDB;

    //public constructor
    public CustomListAdapter(Context context, ArrayList<RecentSongs> items) {
        this.context = context;
        this.items = items;
        tinyDB = new TinyDB(context);
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_recent_items, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // get current item to be displayed
        RecentSongs currentItem = (RecentSongs) getItem(position);

        //sets the text for item name and item description from the current item object
        viewHolder.tvArtist.setText(currentItem.getArtist());
        viewHolder.tvTitle.setText(currentItem.getTitle());
        if (tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL).equals("")) {
            viewHolder.ivMain.setImageResource(R.drawable.main_placeholder);
        } else {
            Glide.with(context)
                    .load(tinyDB.getString(AppConstatnts.CURRENT_IMAGE_URL))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(viewHolder.ivMain);
        }
        // returns the view for the current row
        return convertView;
    }

    private class ViewHolder {
        private final ImageView ivMain;
        TextView tvTitle;
        TextView tvArtist;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvArtist = (TextView) view.findViewById(R.id.tvArtist);
            ivMain = (ImageView) view.findViewById(R.id.ivMain);
        }

    }
}