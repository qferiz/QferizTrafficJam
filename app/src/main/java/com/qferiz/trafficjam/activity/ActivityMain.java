package com.qferiz.trafficjam.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.qferiz.trafficjam.R;
import com.qferiz.trafficjam.fragment.FragmentInfoTrafficList;
import com.qferiz.trafficjam.fragment.FragmentRequestInfo;
import com.qferiz.trafficjam.fragment.FragmentSendInfo;
import com.qferiz.trafficjam.logging.L;

import java.util.ArrayList;
import java.util.List;


public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_ITEM_ID = "selectedId";
    private static final String FIRST_TIME = "first_time";
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawer;
    private ActionBarDrawerToggle mDrawerTogle;
    private int mSelectedId;
    private boolean mUserSawDrawer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        mDrawer = (NavigationView) findViewById(R.id.nav_view);
        mDrawer.setNavigationItemSelectedListener(this);

//        if (mDrawer != null) {
//            setupDrawerContent(mDrawer);
//        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerTogle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerTogle);
        mDrawerTogle.syncState();

//        L.T(getApplicationContext(), "Nilai didUseeDrawer Before = " + didUserSeeDrawer());
        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }

        L.T(getApplicationContext(), "Nilai didUseeDrawer After = " + didUserSeeDrawer());

        mSelectedId = savedInstanceState == null ? R.id.nav_info : savedInstanceState.getInt(SELECTED_ITEM_ID);
        navigate(mSelectedId);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.main_tab);

        if (mViewPager != null) {
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*switch (tab.getPosition()) {
                    case 0:
                        L.t(getApplicationContext(), "Tab Info");
                        break;
                    case 1:
                        L.t(getApplicationContext(), "Tab Request Info");
                        break;
                    case 2:
                        L.t(getApplicationContext(), "Tab Send Info");
                        break;
                }*/
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton mFAB = (FloatingActionButton) findViewById(R.id.main_fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tes Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("DETAIL", null).show();
            }
        });

    }

    private void navigate(int mSelectedId) {
        Intent intent = null;

        if (mSelectedId == R.id.nav_info) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            L.t(getApplicationContext(), "Navigasi Info");
//            intent = new Intent(this, ActivityMaps.class);
//            startActivity(intent);
        }

        if (mSelectedId == R.id.nav_request) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            L.t(getApplicationContext(), "Navigasi Requester");
//            intent = new Intent(this, ActivityMaps.class);
//            startActivity(intent);
        }

        if (mSelectedId == R.id.nav_sending) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            L.t(getApplicationContext(), "Navigasi Sending");
//            intent = new Intent(this, ActivityMaps.class);
//            startActivity(intent);
        }

        // Activity Maps
        if (mSelectedId == R.id.nav_maps) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, ActivityMaps.class);
            startActivity(intent);
        }

        if (mSelectedId == R.id.nav_setting) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            L.t(getApplicationContext(), "Navigasi Setting");
//            intent = new Intent(this, ActivityMaps.class);
//            startActivity(intent);
        }

        if (mSelectedId == R.id.nav_about) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, ActivityAbout.class);
            startActivity(intent);
        }

    }

    private boolean didUserSeeDrawer() {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }

    private void markDrawerSeen() {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        mSharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawers();
    }

    private void setupViewPager(ViewPager mViewPager) {
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new FragmentInfoTrafficList(), "INFO");
        mViewPagerAdapter.addFragment(new FragmentRequestInfo(), "REQUEST");
        mViewPagerAdapter.addFragment(new FragmentSendInfo(), "SEND");
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.ActivityTakePhoto) {
            startActivity(new Intent(this, ActivityTakePhoto.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();

        navigate(mSelectedId);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerTogle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentsTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentsTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public CharSequence getPageTitle(int position) {
            return mFragmentsTitles.get(position);
        }
    }
}
