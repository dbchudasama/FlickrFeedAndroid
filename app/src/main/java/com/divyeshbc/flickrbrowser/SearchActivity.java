package com.divyeshbc.flickrbrowser;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;


public class SearchActivity extends BaseActivity {

    //Search View Variable
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        //Adding the 'Left Arrow' to be able to get back to Home View
        activateToolbarWithHomeEnabled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        //Adding additional options to the toolbar:
        //Search View Menu Item
        final MenuItem searchItem = menu.findItem(R.id.search_view);
        //Getting the basic search view that we need
        mSearchView = (SearchView) searchItem.getActionView();
        //Introducing Search Manager which will return the SEARCH SERVICE
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //Setting the Search tab to be able to search for info
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconified(false);
        //Search Text Listener that responds to the 'text' to search for that is entered by the user
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //Method that will return results after an entire word has been submitted
            public boolean onQueryTextSubmit(String s) {
                //Save what the user types in preferences so that it can be used elsewhere
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //Write the result to FLICKR_QUERY - BaseActivity (via saving it by commit() command)
                sharedPref.edit().putString(FLICKR_QUERY, s).commit();
                //Clearing out the Search View
                mSearchView.clearFocus();
                //Exit - Close current screen and return to previous screen
                finish();
                //Successfully searched for a result
                return true;
            }
            //Called every time a single character is typed and returns results
            @Override
            public boolean onQueryTextChange(String s) {
                //Leaving this as true as will not be doing any processing for this
                return true;
            }
        });
        //Adding listener for close events
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //Invoking standard finish method for closing the screen
                finish();
                return false;
            }

        });

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
}
