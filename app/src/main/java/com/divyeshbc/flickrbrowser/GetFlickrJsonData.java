package com.divyeshbc.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DivyeshBC on 04/05/15.
 */

//THIS CLASS WILL ACCESS THE JSON DATA RETURNED

public class GetFlickrJsonData extends GetRawData {

    //Adding Class fields
    //Adding LOG_TAG
    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    //Storing Photos in a list (of type Photo - created in the class Photo)
    private List<Photo> mPhotos;
    //Recording the Destination URI from where the Photos were downloaded
    //URI stands for Universe Resource Identifier
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll){
        //Initialising the super class
        super(null);

        //Putting together the URL that the superclass can ultimately download from
        createAndUpdateUri(searchCriteria, matchAll);

        //Initialising the mPhotos list to hold the object type Photo
        mPhotos = new ArrayList<Photo>();
    }

    //Execute method
    public void execute(){
        //Using Setter from GetRawData Class
        super.setmRawURL(mDestinationUri.toString());
        DownloadJSONData downloadJSONData = new DownloadJSONData();
        //To make sure it works, will log
        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
        //Execute the download of JSON data
        downloadJSONData.execute(mDestinationUri.toString());
    }

    //Function to construct the URI that we need to download the Photo's from
    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll){
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        //Parameters to use
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";

        //Storing what is being created into mDestination Uri - This will automatically create the URL being used to search the images rather manually having to remember it
        mDestinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM,searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        //Return True if the URL being used is valid
        return mDestinationUri != null;

    }

    //Getter to get the list of Photos
    public List<Photo> getMPhotos() {
        return mPhotos;
    }


    //Method to process downloaded raw data
    public void processResult(){
        //If the data was not downloaded successfully
        if(getmDownloadStatus() != DownloadStatus.OK){
            //Log this error and do not parse data to JSON
            Log.e(LOG_TAG, "Error downloading raw file");
            return;
        }

        //PARSE the data to JSON
        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try{

            //Fetching the data that is being downloaded and storing in this new JSON Object
            JSONObject jsonData = new JSONObject(getmData()); //getmData is from the class GetRawData
            //JSOn Array storing a list of all photos returned from the search.
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            //Iterate through each photo and extract out the data
            for(int i=0; i<itemsArray.length(); i++){
                //Getting each piece of information as a JSON Object as have stored each photo as a JSON Object int he JSON Array
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                //Now individually accessing each photo property
                String title  = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                //To get Photo URL, need to access a new JSON Object
                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                //Creating new Photo Object and passing in details for the image
                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                //Adding the photo the list of photos mPhotos (as defined above)
                this.mPhotos.add(photoObject);

            }

            //For every photo in the Array List
            for(Photo singlePhoto: mPhotos){
                //Dump into String - to check that it is working
                Log.v(LOG_TAG, singlePhoto.toString()); //toString method as defined in Photo.java
            }

        }catch(JSONException jsone){
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error processing JSON Data");
        }
    }

    //Invoke the download of data - Using the DownloadRawData class as we are extending from GetRawData
    public class DownloadJSONData extends DownloadRawData {
        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            processResult();
        }

        protected String doInBackground(String... params) {
            //To avoid crashing, here setting up an object with one element (the URI) as the code expects a list of parameters and not a single URL
            String[] par = { mDestinationUri.toString() };
            //This will be going into GetRawData class and carrying out the method to download the RAW Data in the background of the app before parsing to JSON
            return super.doInBackground(par);
        }
    }
}
