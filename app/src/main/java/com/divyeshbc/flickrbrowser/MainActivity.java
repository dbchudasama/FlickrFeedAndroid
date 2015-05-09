package com.divyeshbc.flickrbrowser;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "MainActivity";
    private List<Photo> mPhotoList = new ArrayList<Photo>();
    private RecyclerView mRecyclerView;
    private FlickrRecyclerViewAdapter mflickrRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialise the recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //Setting up the Layout for the Recycler View
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Setting up the process criteria "android or lollipop" photos
        ProcessPhotos processPhotos = new ProcessPhotos("dragonball z",true);
        processPhotos.execute();

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

        return super.onOptionsItemSelected(item);
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
                //Initialising the adapter to update from Main Activity
                mflickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this, getMPhotos());
                //Adding the adapter to the view
                mRecyclerView.setAdapter(mflickrRecyclerViewAdapter);
            }
        }
    }
}
