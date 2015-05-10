package com.divyeshbc.flickrbrowser;

import java.io.Serializable;

/**
 * Created by DivyeshBC on 04/05/15.
 */

//THIS CLASS WILL STORE THE DATA RETURNED FROM THE JSON DATA

//Serializable allows the photo object to be passed to various different platforms in a language that java understands
//Here will pass the Photo Object in to the view to allow recyclerView to detect which Image has been selected to allow it's
//independent properties to display
public class Photo implements Serializable {

    //Adding serialization version - 1L indicates the version number of this Photo Object. This can be incremented if additional
    //details/properties of the photo are added
    private static final long serialVersionUID = 1L;

    //Defining individual photo properties as private variables
    private String mTitle;
    private String mAuthor;
    private String mAuhtorId;
    private String mLink;
    private String mTags;
    private String mImage;

    //Constructor using all the above properties
    public Photo(String mTitle, String mAuthor, String mAuhtorId, String mLink, String mTags, String mImage) {
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAuhtorId = mAuhtorId;
        this.mLink = mLink;
        this.mTags = mTags;
        this.mImage = mImage;
    }

    //Getters --

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAuhtorId() {
        return mAuhtorId;
    }

    public String getLink() {
        return mLink;
    }

    public String getTags() {
        return mTags;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    //Returning Photo with it's properties
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuhtorId='" + mAuhtorId + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
