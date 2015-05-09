package com.divyeshbc.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by DivyeshBC on 09/05/15.
 */

//Extending this class to call the RecyclerView ViewHolder
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {

    //ImageView where the image will be stored
    protected ImageView thumbnail;
    //TextView where the text will be displayed
    protected TextView title;


    //Constructor for the Image View Holder
    public FlickrImageViewHolder(View view) {
        super(view);
        //Initialising the ImageView as defined in the browse.xml file
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        //Initialising the TextView as defined in the browse.xml file
        this.title = (TextView) view.findViewById(R.id.title);
    }

}
