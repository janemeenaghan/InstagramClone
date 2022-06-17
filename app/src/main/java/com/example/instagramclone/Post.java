package com.example.instagramclone;

import android.util.Log;
import android.widget.RatingBar;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcel;

import java.lang.reflect.Array;
import java.util.Date;
@Parcel(analyze= Post.class)
@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_COMMENTS = "comments";


    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public JSONArray getLikes(){
        return getJSONArray(KEY_LIKES);
    }
    public int getLikesCount(){
        return getLikes().length();
    }

    public boolean isLiked(){
        boolean containsUserLike = false;
        JSONArray array1 = getJSONArray(KEY_LIKES);
        for (int i = 0 ; i < array1.length() ; i ++){
            try {
                if (array1.get(i).toString().equals(ParseUser.getCurrentUser().getObjectId())){
                    containsUserLike = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return containsUserLike;
    }

    public void likeButtonClicked() throws JSONException {
        if (isLiked()){
            removeALike();
        }
        else{
            addALike();
        }
    }
    public void addALike() throws JSONException {
        JSONArray array1 = getJSONArray(KEY_LIKES);
        JSONArray array2 = new JSONArray(array1.length()+1);
        for (int i = 0 ; i < array2.length() ; i ++){
            array2.put(array1.get(i));
        }
        array2.put(ParseUser.getCurrentUser().getObjectId());
        put(KEY_LIKES, array2);

        //put(KEY_LIKES,ParseUser.getCurrentUser().getObjectId());
                //(Number)((int)(getNumber(KEY_LIKES))+1));
    }
    public void removeALike() throws JSONException {
        JSONArray array1 = getJSONArray(KEY_LIKES);
        JSONArray array2 = new JSONArray(array1.length()-1);
        for (int i = 0 ; i < array2.length() ; i ++){
            if (array1.get(i) != ParseUser.getCurrentUser().getObjectId()){
                array2.put(array1.get(i));
            }
        }
        put(KEY_LIKES, array2);
                //(KEY_LIKES,(Number)((int)(getNumber(KEY_LIKES))-1));
    }
/*

    public JSONArray getComments(){
        return getJSONArray(KEY_COMMENTS);
    }
    public void addAComment(String comment) throws JSONException {
       /* JSONArray comments = getJSONArray(KEY_COMMENTS);
        JSONArray comments2 = new JSONArray(comments.length()+1);
        for (int i = 0 ; i < comments.length(); i ++){
            comments2.put(comments.get(i));
        }
        comments2.put(comment);

        put(KEY_COMMENTS, comments2);

        // OR
        //  put(KEY_COMMENTS,comment);
    }*/


    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }
        return "";
    }




}
