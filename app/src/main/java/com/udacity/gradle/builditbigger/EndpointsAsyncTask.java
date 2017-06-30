package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.khantilchoksi.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by khantilchoksi on 29/06/17.
 */

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();
    private EndpointsAsyncTaskListener mListener = null;
    private Exception mError = null;
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://192.168.0.102:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        //context = params[0];


        try {
            return myApiService.getJokeFromServer().execute().getData();
        } catch (IOException e) {
            mError = e;
            return e.getMessage();
        }
    }

    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(LOG_TAG,"Received Result on post execute: "+result);
        if (this.mListener != null){
            Log.d(LOG_TAG,"mListener is not null.");
            this.mListener.onComplete(result, mError);
        }



        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    public static interface EndpointsAsyncTaskListener {
        public void onComplete(String jokeString, Exception e);
    }
}
