package com.hisham.jokeoftheday.ui;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by hisham on 10/15/2015.
 */
public class JokeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "mvraFXbOjlvFZ7XN5yLL6R99MjTd58CRcHkXlRvb", "kUmvNPeN8GuGeT7opwPBFqKN9KBC9XwqrzTfYVXT");

    }
}
