package com.divyeshbc.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private static final String LOG_TAG = "MainActivity";
    private List<Photo> mPhotoList = new ArrayList<Photo>();
    private RecyclerView mRecyclerView;
    private FlickrRecyclerViewAdapter mflickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Calling Activate Toolbar method
        activateToolBar();

        //Initialise the recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //Setting up the Layout for the Recycler View
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialising the FLICKR Recycler view with an empty set of data
        mflickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this, new ArrayList<Photo>());

        //Setting the recycler view adapter to listen for updates in the above empty Recycler View
        mRecyclerView.setAdapter(mflickrRecyclerViewAdapter);

        //Adding the item click listener - Passing in the active Recycler View
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Display below Toast message of short Tap
                Toast.makeText(MainActivity.this, "Normal Tap", Toast.LENGTH_SHORT).show();
            }

            @Override
            //The position of the element in the view will be passed in
            public void onItemLongClick(View view, int position) {
                //Display below Toast message of long Tap
                //Toast.makeText(MainActivity.this, "Long Tap", Toast.LENGTH_SHORT).show();

                //Adding intent to allow the Long Click to navigate to the Photo Details Class
                Intent intent = new Intent(MainActivity.this, ViewPhotoDetailsActivity.class);
                //Along with the intent also transfer the additional photo details that will display based on the
                //position of the image in the cell
                intent.putExtra(PHOTO_TRANSFER, mflickrRecyclerViewAdapter.getPhoto(position));
                //Navigate to that intent
                startActivity(intent);
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //Method is invoke the Search intent when the the search icon is clicked from the Main Menu
        if(id == R.id.menu_search) {
            //Invoking the SearchActivity Class as the new intent
            Intent intent = new Intent(this, SearchActivity.class);
            //Navigate to the above intent
            startActivity(intent);
            //Return true as we don't want to reprocess the Menu Option after doing it once
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //'On Resume' method being overridden to process the 'search' data that is being typed by the user
    @Override
    protected void onResume() {
        //Executing system processes
        super.onResume();
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //Retrieve the FLICKR Query (i.e. the Search criteria) as this is the variable the Search Criteria is getting saved in
            String query = getSavedPreferenceData(FLICKR_QUERY);
            //Here checking the 'text' of the search
            if(query.length() > 0) {
                //Reprocessing the Async task which will process the results of what has been typed
                ProcessPhotos processPhotos = new ProcessPhotos(query, true);
                //Execute the process and return results (photos based on the search)
                processPhotos.execute();
            }
        }

    //Method to return the search query
    private String getSavedPreferenceData(String key) {
        //As used before, get the context of the 'Search'
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //If the 'Key' is empty it will return an empty string or else the 'word'/key that has been typed will be returned
        return sharedPref.getString(key, "");
    }

    public class ProcessPhotos extends GetFlickrJsonData {

        //Constructor
        public ProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }

        public void execute() {
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJSONData {

            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);
                //Executing load new data method
                mflickrRecyclerViewAdapter.loadNewData(getPhotos());
            }
        }
    }
}
