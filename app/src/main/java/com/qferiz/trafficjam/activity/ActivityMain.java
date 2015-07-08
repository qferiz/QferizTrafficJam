package com.qferiz.trafficjam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
    private static final String TAG_REFRESH = "REFRESH";

    private static final String PREFERENCES_FILE = "trafficjam_settings";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private int mCurrentSelectedPosition;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawer;
    private ActionBarDrawerToggle mDrawerTogle;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Menu mMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupTAB();
//        setupFAB();

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        setupNavDrawer();
        // Mengambil data Identity HP : IMSI, IMEI, PHONENUMBER
        getMyIdentityPhone();

    }

    private void setupTAB() {
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tab);

        if (mViewPager != null) {
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (mViewPager != null) {
                    mViewPager.setCurrentItem(tab.getPosition());

            /*        if (tab.getPosition() == 0) {
                        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
                        Intent i = new Intent(TAG_REFRESH);
                        lbm.sendBroadcast(i);
                    }*/

                    switch (tab.getPosition()) {
                        case 0:
//                            L.t(getApplicationContext(), "Tab Info");
                            mCurrentSelectedPosition = 0;
                            break;
                        case 1:
//                            L.t(getApplicationContext(), "Tab Request Info");
                            mCurrentSelectedPosition = 1;
                            break;
                        case 2:
//                            L.t(getApplicationContext(), "Tab Send Info");
                            mCurrentSelectedPosition = 2;
                            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
                            Intent i = new Intent(TAG_REFRESH);
                            lbm.sendBroadcast(i);
                            break;
                    }

                    // Set checked for Drawer Slide Navigation View
                    drawerChecked();
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void drawerChecked() {
        mMenu = mDrawer.getMenu();
        mMenu.getItem(mCurrentSelectedPosition).setChecked(true);
    }

   /* private void setupFAB() {
        FloatingActionButton mFAB = (FloatingActionButton) findViewById(R.id.main_fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Drawer Open", Snackbar.LENGTH_LONG)
                        .setAction("OPEN", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDrawerLayout.openDrawer(GravityCompat.START);
                            }
                        }).show();
//                        .setAction("DETAIL", null).show();
            }
        });
    }*/

    private void setupNavDrawer() {
        mDrawer = (NavigationView) findViewById(R.id.nav_view);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Animate Burger Icon
        if (mToolbar != null) {
            mDrawerTogle = new ActionBarDrawerToggle(this,
                    mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);

            mDrawerLayout.setDrawerListener(mDrawerTogle);
            mDrawerTogle.syncState();
        }

        if (!mUserLearnedDrawer) {
            showDrawer();
            mUserLearnedDrawer = true;
            saveSharedSetting(this, PREF_USER_LEARNED_DRAWER, "true");
        }

        // Set checked for Drawer Slide Navigation View
        drawerChecked();
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void getMyIdentityPhone() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = mTelephonyManager.getSubscriberId();
        String deviceIdIMEI;
        // Membaca IMEI
        if (mTelephonyManager.getDeviceId() != null) {
            // Untuk SmartPhone
            deviceIdIMEI = mTelephonyManager.getDeviceId();
        } else {
            // Untuk Tablet
            deviceIdIMEI = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        // Untuk Tablet
        String androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        String phoneNumber = mTelephonyManager.getLine1Number(); // Maybe return NULL
        String serialNumberSIM = mTelephonyManager.getSimSerialNumber();
        String osAndroid = mTelephonyManager.getDeviceSoftwareVersion();
        String serialNumberHP = Build.SERIAL;

        String message = String.format(
                "Data Identity HP Anda " +
                        "\n IMSI : %1$s " +
                        "\n IMEI : %2$s " +
                        "\n ANDROID ID : %3$s" +
                        "\n NOHP : %4$s" +
                        "\n SerialNumber SIM : %5$s" +
                        "\n OS Android : %6$s" +
                        "\n Serial Number HP : %7$s",
                IMSI, deviceIdIMEI, androidID, phoneNumber, serialNumberSIM, osAndroid, serialNumberHP
        );

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
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

        if (id == R.id.sync) {
            getMyIdentityPhone();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        Intent mIntent = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_info:
                hideDrawer();
                L.t(getApplicationContext(), "Navigasi Info");
                mCurrentSelectedPosition = 0;
                mViewPager.setCurrentItem(mCurrentSelectedPosition);
                return true;

            case R.id.nav_request:
                hideDrawer();
                L.t(getApplicationContext(), "Navigasi Request");
                mCurrentSelectedPosition = 1;
                mViewPager.setCurrentItem(mCurrentSelectedPosition);
                return true;

            case R.id.nav_sending:
                hideDrawer();
                L.t(getApplicationContext(), "Navigasi Sending");
                mCurrentSelectedPosition = 2;
                mViewPager.setCurrentItem(mCurrentSelectedPosition);
                return true;

            case R.id.nav_maps:
                hideDrawer();
                L.t(getApplicationContext(), "Navigasi Maps");
                mIntent = new Intent(this, ActivityMaps.class);
                startActivity(mIntent);
                mCurrentSelectedPosition = 3;
                return true;

            case R.id.nav_setting:
                hideDrawer();
                L.t(getApplicationContext(), "Navigasi Setting");
                mCurrentSelectedPosition = 4;
                return true;

            case R.id.nav_about:
                hideDrawer();
                L.t(getApplicationContext(), "Navigasi About");
                mIntent = new Intent(this, ActivityAbout.class);
                startActivity(mIntent);
                mCurrentSelectedPosition = 5;
                return true;

            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static void saveSharedSetting(Context context, String settingName, String settingValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static String readSharedSetting(Context context, String settingName, String settingValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(settingName, settingValue);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerTogle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        mMenu = mDrawer.getMenu();
        mMenu.getItem(mCurrentSelectedPosition).setChecked(true);
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
