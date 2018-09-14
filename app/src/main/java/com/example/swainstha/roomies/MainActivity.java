package com.example.swainstha.roomies;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        toolbar.setTitle("News");

        //initializing a bottombar navigation view with the bottombar view  and setting the callback function
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_expenses:
                    toolbar.setTitle("Expenses");
//                    fragment = new CommonFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.action_summary:
                    toolbar.setTitle("Summary");
//                    fragment = new MatchFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.action_chat:
                    toolbar.setTitle("Chat");
//                    fragment = new PositionFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.action_stock:
                    toolbar.setTitle("Stock");
//                    fragment = new GroupRankFragment();
//                    loadFragment(fragment);
                    return true;
                case R.id.action_reminder:
                    toolbar.setTitle("Reminders");
//                    fragment = new GroupRankFragment();
//                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

    //loading the different fragments when items of bottombar nav are selected
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragmentholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present. like MyProfile, Settings etc
        //getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//
//        } else if (id==R.id.Logout) {
//
//
//        } else if(id == R.id.Heroku) {
//            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString(getString(R.string.link), "https://world-cup-server.herokuapp.com");
//            editor.apply();
//
//
//        } else if(id == R.id.Local) {
//            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString(getString(R.string.link), "http://192.168.1.119:3001");
//            editor.apply();
//
//        } else if(id == R.id.search_m) {
//            super.onSearchRequested();
//            return true;
//        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_profile) {
//            // Handle the camera action
//        } else if (id == R.id.nav_history) {
//
//        } else if (id == R.id.nav_credits) {
//
//        } else if (id == R.id.nav_logout) {
//
//        }
//
//        //when a item is selected in slide navigation, the drawer closes itself
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
