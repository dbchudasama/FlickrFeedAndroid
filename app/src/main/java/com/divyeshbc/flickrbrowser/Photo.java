package com.divyeshbc.flickrbrowser;

/**
 * Created by DivyeshBC on 04/05/15.
 */

//THIS CLASS WILL STORE THE DATA RETURNED FROM THE JSON DATA

public class Photo {
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

    //Getter --
    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuhtorId() {
        return mAuhtorId;
    }

    public String getmLink() {
        return mLink;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmImage() {
        return mImage;
    }
    // --


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
