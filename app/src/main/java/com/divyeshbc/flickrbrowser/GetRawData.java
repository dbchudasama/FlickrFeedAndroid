package com.divyeshbc.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DivyeshBC on 04/05/15.
 */

//Creating an enum of Different Download status' to reflect Download progress
enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK }

//Initialising Get Raw Data class
public class GetRawData {
    //LOG_TAG gives us a standard way to access an output when debugging
    private String LOG_TAG = GetRawData.class.getSimpleName();
    //Variable for raw URL
    private String mRawURL;
    //Variable for Raw Data
    private String mData;
    //Variable for Raw Download Status (aspect of enum as defined above)
    private DownloadStatus mDownloadStatus;

    //Initialising Constructor - Using URL from where we would like to obtain the feed
    public GetRawData(String mRawURL) {
        this.mRawURL = mRawURL;
        //Setting an initial state for the download status
        this.mDownloadStatus = DownloadStatus.IDLE;

    }

    //Method to reset download status
    public void reset() {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawURL = null;
        this.mData = null;
    }

    //--
    //Creating 'Getter' and 'Setter' for both the status (as we may need to access this elsewhere) and the Data itself
    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public String getmData() {
        return mData;
    }

    public void setmRawURL(String mRawURL) {
        this.mRawURL = mRawURL;
    }

    public void execute() {
        this.mDownloadStatus = DownloadStatus.PROCESSING;
        //Invoking Async Task
        DownloadRawData downloadRawData = new DownloadRawData();
        //Execute Task so downloading begins. Passing in mRawURL as passed in in the MainActivitiy.java file
        downloadRawData.execute(mRawURL);
    }


    //--

    //Raw Data class to actually handle the downloading of the data.
    //AsyncTask<<Input>, <Progress>, <Return Value>>
    public class DownloadRawData extends AsyncTask<String, Void, String> {

        //This method will handle what needs to happen post downloading data
        protected void onPostExecute(String webData) {
            //Here, storing the data in the mData variable
            mData = webData; //Data that is being returned
            //Logging to make sure the data returned is correct
            Log.v(LOG_TAG, "Data returned was: " + mData);
            if (mData == null) {
                if (mRawURL == null){
                    //If nothing was returned because the URL was not initialised, then set download status to 'NOT INITIALISED'
                    mDownloadStatus = DownloadStatus.NOT_INITIALISED;
                } else {
                //Otherwise, if it still returned null but the URL was valid then set the Download status to 'FAILED OR EMPTY'
                mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            } else {
                //Success
                mDownloadStatus = DownloadStatus.OK;
            }
        }

        //Do this in the background behind the app - This will only download the Data, no procesing
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if (params == null)
                return null;

            try {
                //Opening up the first parameter and trying to download from this URL
                URL url = new URL(params[0]);

                //Opening URL Connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //Standard 'GET' method for the URL
                urlConnection.setRequestMethod("GET");
                //Now connecting to the URL
                urlConnection.connect();

                //Fetching the input stream from the URL
                InputStream inputStream = urlConnection.getInputStream();
                if(inputStream == null){
                    return null; //Error handling in case the input stream is null
                }
                //Creating a new String Buffer
                StringBuffer buffer = new StringBuffer();

                //Creating new buffer reader which will read in the Input Stream
                reader = new BufferedReader(new InputStreamReader(inputStream));

                //Variable to keep track of the line reads in the input stream
                String line;
                //If we haven't reached the end of the file
                while((line = reader.readLine()) != null) {
                    //Keep processing whilst we have got Data
                    buffer.append(line + "\n"); //Appending all the data that has been read in - Create new line everytime buffer moves on to reading a new line
                }

                //Once the end of the input stream is reached, return the buffer in String Format
                return buffer.toString();
            } catch(IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                //Making sure that if the URL returned a successful result, then once reaching the end of the read the app disconnects from the URL
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                //Making sure that if the buffer read is successfully reading input stream
                if(reader != null){
                    try {
                        //Close the reader, once done.
                        reader.close();
                    } catch(final IOException e){
                        //Error handling - If an issue occurs then display the below message
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }
    }
}

