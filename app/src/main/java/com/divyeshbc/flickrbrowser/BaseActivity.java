package com.divyeshbc.flickrbrowser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by DivyeshBC on 09/05/15.
 */

//Creating a Base Activity class that can be used throughout the app consisting of shared activities
public class BaseActivity extends ActionBarActivity {

    //Variable to store the Toolbar
    private Toolbar mToolbar;
    //String variable for Search Query
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    //String returning all Photo Object properties
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";


    //Method to create Custom ToolBar
    protected Toolbar activateToolBar() {
        //If no toolbar has been created then create a new toolbar using Toolbar View app_bar
        if(mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            //If toolbar already created then no need to create a new one.
            if(mToolbar != null) {
                //Use current toolbar
                setSupportActionBar(mToolbar);
            }
        }
        //Return the created Toolbar
        return mToolbar;
    }

    //Method to add the 'Back Arrow' to allow user to navigate back to Home View
    protected Toolbar activateToolbarWithHomeEnabled() {
        //Create standard Toolbar
        activateToolBar();
        if(mToolbar != null) {
            //If Toolbar is created then automatically add the 'Back Arrow (Left Arrow)' symbol to home view
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return mToolbar;
    }

}
