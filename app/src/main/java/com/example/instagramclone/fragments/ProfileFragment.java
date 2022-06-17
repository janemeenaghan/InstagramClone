package com.example.instagramclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment{
    private static final String TAG = "ProfileFragment";
    private Button logout,changeProfilePicture;
    private TextView usernameOnProfile;
    private ImageView profilePictureonProfile;
    private SwipeRefreshLayout swipeContainer;
    @Override
    public void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        // limit query to latest 20 items
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        // order posts by creation date (newest first)
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());

                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        usernameOnProfile = view.findViewById(R.id.usernameOnProfile);
        profilePictureonProfile= view.findViewById(R.id.profilePictureOnProfile);
       // IMPLEMENT
        // Glide.with(getContext()).load(ParseUser.getCurrentUser().getP.getUrl()).into(ProfilePicture);

        usernameOnProfile.setText(ParseUser.getCurrentUser().getUsername());
        logout = view.findViewById(R.id.logout);

        changeProfilePicture = view.findViewById(R.id.changeProfilePicture);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick logout button");
                logoutUser();
            }
        });
        changeProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick changeProfilePicture button");
              //  changeProfilePicture();
            }
        });
    }

    //IMPLEMENT
    public void changeProfilePicture(){
        //ParseUser.getCurrentUser().setProfilePicture();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void logoutUser() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT);


        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        // finish() fixes the problem discussed with Naga about users going back to activities they aren't supposed to
        //finish();
    }
}
