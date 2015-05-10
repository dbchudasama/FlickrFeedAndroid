package com.divyeshbc.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DivyeshBC on 10/05/15.
 */

//Class that will listen our for clicks on Image Thumbnails
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    //Interface for item click
    public static interface OnItemClickListener {

        //Short click
        public void onItemClick(View view, int position);
        //Long click
        public void onItemLongClick(View view, int position);
    }

    //Listener variable for item click
    private OnItemClickListener mListener;
    //Gesture Detector variable to detect if long or short click
    private GestureDetector mGestureDetector;

    //Method to listen to item click on the recycler view
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        //Listener for Gesture
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            //Single Tap
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            //Long Tap
            public void onLongPress(MotionEvent e) {
                //The getX and getY basically finds the coordinates - where abouts on the row in the Recycler view the user has clicked (The position of the click)
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                //If a click is detected by the listener or on the view
                if(childView != null && mListener != null) {
                    //Then get the position of the click on the row and determine it as a long click
                    mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });

    }

    //Method for short tap - Description mimics above
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView,view.getChildPosition(childView));
        }
        return false;
    }

    //Method for motion highlight when touching a row - Empty method as we do not want anything to happen
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

    }

}
