package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.khantilchoksi.myandroidlibrary.MainLibraryActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{

    private static String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private  static ProgressDialog progressDialog;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);



        Button tellJokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG,"Calling ASYNC Task.");
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Fetching Joke...");
                progressDialog.show();

                EndpointsAsyncTask task = new EndpointsAsyncTask(progressDialog);
                task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
                    @Override
                    public void onComplete(String jokeString, Exception e) {
                        Intent libraryIntent = new Intent(getActivity(), MainLibraryActivity.class);
                        Log.d(LOG_TAG,"Builiding intent, received joke: "+jokeString);
                        progressDialog.dismiss();
                        libraryIntent.putExtra("joke",jokeString);
                        getActivity().startActivity(libraryIntent);
                    }
                }).execute();

            }
        });
        return root;
    }


    /*public void tellJoke(View view) {
        //Toast.makeText(this, new MyJokes().getJoke(), Toast.LENGTH_SHORT).show();

        //Intent libraryIntent = new Intent(this, MainLibraryActivity.class);
        //libraryIntent.putExtra("joke",new MyJokes().getJoke());
        //this.startActivity(libraryIntent);
        Log.d(LOG_TAG,"Calling ASYNC Task.");
        new EndpointsAsyncTask().execute();
    }*/


}
