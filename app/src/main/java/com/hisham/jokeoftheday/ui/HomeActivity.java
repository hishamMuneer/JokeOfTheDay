package com.hisham.jokeoftheday.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hisham.jokeoftheday.R;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 *
 * * <h21<a href="http://android-developers.blogspot.in/2015/05/android-design-support-library.html">Reference link for Blog reading and images</a></h1>
 * <b>How to start:</b>
 * <li>Create a new project, blank activity</li>
 * <li>Open build.gradle module app file and add:</li>
 * <pre>
 * compile 'com.android.support:support-v4:22.0.0'
 * compile 'com.android.support:appcompat-v7:22.0.0'
 * compile 'com.android.support:design:22.2.0'
 * <li>Extend your activity to AppCompatActivity</li>
 *
 * If you face error
 * Set theme in manifest application tag: android:theme="@style/Theme.AppCompat.Light"
 *
 * <h1>1. NavigationView</h1>
 * Add the following code in xml file:
 * <android.support.v4.widget.DrawerLayout
 android:id="@+id/drawerLayout"
 xmlns:android="http://schemas.android.com/apk/res/android"
 xmlns:app="http://schemas.android.com/apk/res-auto"
 android:layout_width="match_parent"
 android:layout_height="match_parent"
 android:fitsSystemWindows="true">

 <!-- your content layout -->

 <android.support.design.widget.NavigationView
 android:id="@+id/navigationView"
 android:layout_width="wrap_content"
 android:layout_height="match_parent"
 android:layout_gravity="start"
 app:headerLayout="@layout/drawer_header"
 app:menu="@menu/menu_main"/>
 </android.support.v4.widget.DrawerLayout>


 * You can also make optional header inside the Navigation View and you can also populate the options inside Navigation View via Options menu
 *
 * Make a single checkable group inside menu:
 *
 *     <group android:checkableBehavior="single">
 <item
 android:id="@+id/navigation_item_1"
 android:checked="true"
 android:icon="@mipmap/ic_launcher"
 android:title="@string/item_1" />
 <item
 android:id="@+id/navigation_item_2"
 android:icon="@mipmap/ic_launcher"
 android:title="@string/item_2" />
 </group>

 *
 * You'll get callbacks on selected items by setting a OnNavigationItemSelectedListener using setNavigationItemSelectedListener().
 * This provides you with the MenuItem that was clicked, allowing you to handle selection events, changed the checked status,
 * load new content, programmatically close the drawer, or any other actions you may want.
 *
 * Close is called on drawerLayout
 * You can also hide the menu by not implementing onCreateOptionsMenu, NavigationView will work as it working.
 *
 *
 * Files included in this example:
 * layout/activity_mail.xml
 * layout/drawer_header.xml
 * menu/menu_main.xml
 * minmpa/ic_launcher
 *
 * </pre>
 *
 * To make drawer arrow animation. Add the following lines of code in onCreate()
 *
 *final ActionBar ab = getSupportActionBar();
 ab.setHomeAsUpIndicator(R.drawable.ic_menu);
 ab.setDisplayHomeAsUpEnabled(true);

 ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
 drawerLayout.setDrawerListener(actionBarDrawerToggle);
 actionBarDrawerToggle.syncState();
 *
 *
 * Android you also need to implement the onOptionsItemSelected like this:
 *
 * int id = item.getItemId();

 if(android.R.id.home == id) {
 if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
 drawerLayout.closeDrawer(GravityCompat.START);
 } else {
 drawerLayout.openDrawer(GravityCompat.START);
 }
 }
 *
 * How to implement DrawerArrowToggle from Android appcompat - v7 - 21 - library. <br />
 * <h3>Steps: </h3>
 *
 * <p>Inside the onCreate() goes the following: </p>
 * <pre class="prettyprint">
 *

 final ActionBar ab = getSupportActionBar();
 ab.setHomeAsUpIndicator(R.drawable.ic_menu);
 ab.setDisplayHomeAsUpEnabled(true);

 DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

 ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
 mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
 actionBarDrawerToggle.syncState();

 // Override the onOptionsItemSelected so that on selecting the burger icon, navigation drawer should open.
 // Burger icon should be present in the drawable folder. ex: ic_menu.png

 public boolean onOptionsItemSelected(MenuItem item) {
 switch (item.getItemId()) {
 case android.R.id.home:
 if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
 mDrawerLayout.closeDrawer(GravityCompat.START);
 } else {
 mDrawerLayout.openDrawer(GravityCompat.START);
 }
 return true;
 }
 return super.onOptionsItemSelected(item);
 }
 </pre>

 <p>Code for xml layout: </p>

 <pre class="prettyprint">
 &lt;android.support.v4.widget.DrawerLayout
 xmlns:android="http://schemas.android.com/apk/res/android"
 xmlns:app="http://schemas.android.com/apk/res-auto"
 android:id="@+id/drawer_layout"
 android:layout_height="match_parent"
 android:layout_width="match_parent"
 android:fitsSystemWindows="true"&gt;
 &lt;!--Whatever you want to display in the middle goes here--&gt;
 &lt;ListView
 android:id="@+id/myListView"
 android:layout_width="match_parent"
 android:layout_height="match_parent"&gt;
 &lt;/ListView>
 &lt;!-- ENDS - Whatever you want to display in the middle goes here--&gt;
 &lt;android.support.design.widget.NavigationView
 android:id="@+id/nav_view"
 android:layout_height="match_parent"
 android:layout_width="wrap_content"
 android:layout_gravity="start"
 android:fitsSystemWindows="false"
 app:menu="@menu/menu_home"/&gt;

 &lt;/android.support.v4.widget.DrawerLayout&gt;
 </pre>
 *
 */

public class HomeActivity extends AppCompatActivity implements JokeOfTheDayFragment.OnFragmentInteractionListener, SubmitAJokeFragment.OnFragmentInteractionListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    // IMPORTANT - see this http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ActionBar ab = getSupportActionBar();
        if(ab!=null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        JokeOfTheDayFragment jokeOfTheDayFragment = new JokeOfTheDayFragment();
        fragmentTransaction.add(R.id.fragment_container, jokeOfTheDayFragment);
        fragmentTransaction.commit();



//        SubmitAJokeFragment submitAJokeFragment = new SubmitAJokeFragment();
//                fragmentTransaction.add(R.id.fragment_container, submitAJokeFragment);
//                fragmentTransaction.commit();

//        ListView myListView = (ListView) findViewById(R.id.myListView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, objects);
//        myListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "OnStart called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * It stores the currently checked navigation menu item id, so that you don't have to load the fragment again.
     */
    private int currentMenuItemId = R.id.action_joke_of_the_day;

    /**
     * This is the side navigation listener, when you click on the drawer items, it gets called.
     */
    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            if(menuItem.getItemId() == currentMenuItemId){
                // no action should be performed.
            } else {
                switch (menuItem.getItemId()) {
                    case R.id.action_joke_of_the_day:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        JokeOfTheDayFragment jokeOfTheDayFragment = new JokeOfTheDayFragment();
                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack if needed
                        fragmentTransaction.replace(R.id.fragment_container, jokeOfTheDayFragment);
                        fragmentTransaction.addToBackStack(null);

                        // Commit the transaction
                        fragmentTransaction.commit();
                        break;

                    case R.id.action_submit_a_joke:
                        showSubmitAJokeFragment();
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "No action found against this action.", Toast.LENGTH_SHORT).show();
                        return false;
                } // switch ends
                menuItem.setChecked(true);
            } // else ends
            // ultimately close the drawer
            mDrawerLayout.closeDrawer(GravityCompat.START);
            // finally set the selected item as current menu item
            currentMenuItemId = menuItem.getItemId();
            return true;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void gotoCreateAJoke() {
        showSubmitAJokeFragment();
    }

    private void showSubmitAJokeFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        SubmitAJokeFragment submitAJokeFragment = new SubmitAJokeFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        fragmentTransaction.replace(R.id.fragment_container, submitAJokeFragment);
        fragmentTransaction.addToBackStack(null);
        // Commit the transaction
        fragmentTransaction.commit();
    }
}
