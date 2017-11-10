package com.niquid.personal.bakeme.models;

import org.parceler.Parcel;

import timber.log.Timber;

@Parcel
public class Step {

    private static final String FORMAT_MP4 = "mp4";

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    Step() { //For parcel
    }

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public boolean hasVideo(){
        if(!videoURL.equals(""))
            return true;
        else if(isVideo(thumbnailURL))
            return true;
        return false;
    }

    public String getVideo(){
        if(!videoURL.equals(""))
            return videoURL;
        else
            return thumbnailURL;
    }

    private boolean isVideo(String url) {
        int dotIndex = url.lastIndexOf(".");
        return (dotIndex != -1 && dotIndex != 0)
                && url.substring(dotIndex + 1).equals(FORMAT_MP4);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
