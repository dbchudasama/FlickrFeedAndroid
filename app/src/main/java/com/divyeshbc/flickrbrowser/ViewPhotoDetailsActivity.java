package com.divyeshbc.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class ViewPhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);
        //Enabling the Home 'Back Arrow'
        activateToolbarWithHomeEnabled();

        //Retrieving current intent NOT CREATING a new one
        Intent intent = getIntent();
        //Returning the photo object that is passed from MainActivity, hence allowing us to access the photo details
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        //Linking up the photo title text view from the photo_details view
        TextView photoTitle = (TextView) findViewById(R.id.photo_title);
        //Getting the photo title
        photoTitle.setText("Title: " + photo.getTitle());

        TextView photoTags = (TextView) findViewById(R.id.photo_tags);
        photoTags.setText("Tags: " + photo.getTags());

        TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
        photoAuthor.setText("Author: " + photo.getAuthor());

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        //Picasso will now get the image and put it into the Image View defined in photo_details.xml
        Picasso.with(this).load(photo.getLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_details, menu);
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
