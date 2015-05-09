package com.divyeshbc.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DivyeshBC on 09/05/15.
 */
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {

    //List of photo objects
    private List<Photo> mPhotoList;
    //Context
    private Context mContext;

    //A public Constructor - Passing the context adn list of photos through
    public FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        //Setting the variables
        mContext = context;
        this.mPhotoList = photoList;
    }

    @Override
    //We will be inflating the layout. Grabbing the image object and putting it into the holder.
    //Overriding the onCreateViewHolder method
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        //Here we are inflating the 'browse' layout (Layout for the Recycler View)
        //Now have a view object
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);

        //Creating an object of type FlickrImageViewHolder
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);
        //Returning the object flickrImageViewHolder
        return flickrImageViewHolder;
    }

    //Overriding the method onBindViewHolder. This is an important method as it is called every time a view on screen needs to be updated
    //This code will download (using the Picasso tool) the thumbnail to the image that is needed. This is very efficient as not all
    //the images will be downloaded but only those that will appear on screen, saving excess memory usage and processing power
    @Override
    public void onBindViewHolder(FlickrImageViewHolder flickrImageViewHolder, int position) {
        //Fetching the index position of the photo in the view being drawn on the screen
        Photo photoItem = mPhotoList.get(position);
        //Drawing the thumbnail, using the Picasso Library
        Picasso.with(mContext).load(photoItem.getmImage()) //Download the image
                .error(R.drawable.placeholder) //If an error occurs show the placeholder
                .placeholder(R.drawable.placeholder) //Whilst is it downloading show the placeholder
                .into(flickrImageViewHolder.thumbnail); //Putting the image into the thumbnail in the flickrImageViewHolder
        flickrImageViewHolder.title.setText(photoItem.getmTitle()); //Getting the title for the image and setting it in the TextView

    }

    @Override
    //This function gets a count of the number of elements in the object
    public int getItemCount() {
        //If mPhotos list is null, return 0 or else return the number of elements in that object
        return (null != mPhotoList ? mPhotoList.size() : 0);
    }

}
