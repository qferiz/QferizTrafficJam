package com.qferiz.trafficjam.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import com.qferiz.trafficjam.callback.FragmentCommunicator;
import com.qferiz.trafficjam.fragment.FragmentInfoTrafficList;
import com.qferiz.trafficjam.fragment.FragmentRequestInfo;
import com.qferiz.trafficjam.fragment.FragmentSendInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_ITEM_ID = "selectedId";
    private static final String FIRST_TIME = "first_time";

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

    //interface through which communication is made to fragment
    public FragmentCommunicator mFragmentCommunicator;
    public int mLoadData = 1;

    // Variabel untuk Google Maps Location
    private LocationManager mLocationManager;
    Location mLocation;
    private double GET_LONGITUDE;
    private double GET_LATITUDE;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10; // dalam meter
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 60000; // dalam Miliseconds
    private static final String TAG_REFRESH = "REFRESH";
    private String getNamaJalan, getNamaKecamatan, getNamaKota, getNamaPropinsi, getNamaNegara,
            getLatitude, getLongitude = "";

    private static final String EXTRA_LATITUDE = "send_latitude";
    private static final String EXTRA_LONGITUDE = "send_longitude";
    private static final String EXTRA_NAMA_JALAN = "send_nama_jalan";
    private static final String EXTRA_NAMA_KECAMATAN = "send_nama_kecamatan";
    private static final String EXTRA_KOTA = "send_kota";
    private static final String EXTRA_PROPINSI = "send_propinsi";
    private static final String EXTRA_NEGARA = "send_negara";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        showRetrievingData();

        setupToolbar();
        setupTAB();
//        setupFAB();

        mUserLearnedDrawer = Boolean.valueOf(readSharedSetting(this, PREF_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_send_info, sendDataFragment()).commit();*/
        }

        setupNavDrawer();
        // Mengambil data Identity HP : IMSI, IMEI, PHONENUMBER
        getMyIdentityPhone();
        sendDataFragment();

        // Cek NullPointerExeption

       /* try {
            if (getLatitude.trim().equals("null") || getLatitude.trim().equals("") || getLatitude.trim().length() <= 0
                    || getLongitude.equals("null") || getLongitude.equals("") || getLongitude.trim().length() <= 0
                    || getNamaJalan.equals("null") || getNamaJalan.equals("") || getNamaJalan.trim().length() <= 0
                    || getNamaKecamatan.equals("null") || getNamaKecamatan.equals("") || getNamaKecamatan.trim().length() <= 0
                    || getNamaKota.equals("null") || getNamaKota.equals("") || getNamaKota.trim().length() <= 0
                    || getNamaPropinsi.equals("null") || getNamaPropinsi.equals("") || getNamaPropinsi.trim().length() <= 0
                    || getNamaNegara.equals("null") || getNamaNegara.equals("") || getNamaNegara.trim().length() <= 0) {

                // Setting Location
                setupShowCurrentLocation();

                // Setting ReverseGeocoding
                setupReverseGeocoding();

            } else {
                // Passing Data From Activity to FragmentSendInfo
                sendDataFragment();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }*/

    }

    private void sendDataFragment() {

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
        Intent i = new Intent(TAG_REFRESH);
        lbm.sendBroadcast(i);

       /* if (mFragmentCommunicator != null) {
            mFragmentCommunicator.passDataToFragment(getLatitude, getLongitude, getNamaJalan,
                    getNamaKecamatan, getNamaKota, getNamaPropinsi, getNamaNegara);
        }*/
      /*  FragmentSendInfo fragSendInfo = (FragmentSendInfo) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_send_info);

        if (fragSendInfo != null){
            fragSendInfo.updateDataFragmentSendInfo(getLatitude, getLongitude, getNamaJalan);

        } else {*/

     /*       Bundle mBundle = new Bundle();
            mBundle.putString("latitude", getLatitude);
            mBundle.putString("longitude", getLongitude);
            mBundle.putString("nama_jalan", getNamaJalan);
            mBundle.putString("nama_kecamatan", getNamaKecamatan);
            mBundle.putString("nama_kota", getNamaKota);
            mBundle.putString("nama_propinsi", getNamaPropinsi);
            mBundle.putString("nama_negara", getNamaNegara);

            FragmentSendInfo fragSendInfo = new FragmentSendInfo();
            fragSendInfo.setArguments(mBundle);
*/
           /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_content, fragSendInfo);
            transaction.addToBackStack(null);
            transaction.commit();*/
//        }


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
                         /*   LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
                            Intent i = new Intent(TAG_REFRESH);
                            lbm.sendBroadcast(i);*/

                         /*   Bundle mBundle = new Bundle();
                            if (getNamaJalan != null){
                                mBundle.putString(EXTRA_NAMA_JALAN, getNamaJalan);
                            }

                            if (getNamaKecamatan != null){
                                mBundle.putString(EXTRA_NAMA_KECAMATAN, getNamaKecamatan);
                            }

                            if (getNamaKota != null){
                                mBundle.putString(EXTRA_KOTA, getNamaKota);
                            }

                            if (getNamaPropinsi != null){
                                mBundle.putString(EXTRA_PROPINSI, getNamaPropinsi);
                            }

                            if (getNamaNegara != null){
                                mBundle.putString(EXTRA_NEGARA, getNamaNegara);
                            }

                            if (getLatitude != null){
                                mBundle.putString(EXTRA_LATITUDE, getLatitude);
                            }

                            if (getLongitude != null){
                                mBundle.putString(EXTRA_LONGITUDE, getLongitude);
                            }

                            FragmentSendInfo fragSendInfo = new FragmentSendInfo();
                            fragSendInfo.setArguments(mBundle);
*/
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
//        String IMSI = mTelephonyManager.getSubscriberId();

        String deviceIdIMEI = mTelephonyManager.getDeviceId(); // Membaca IMEI pada Smartphone, Maybe Return NULL
        if (deviceIdIMEI.isEmpty() || deviceIdIMEI.equals("")
                || deviceIdIMEI.trim().equals("null")
                || deviceIdIMEI.trim().length() <= 0) {
            // Untuk Tablet - Membaca Android ID Hex 64 bit
            deviceIdIMEI = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        // Untuk Tablet
//        String androidID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        String phoneSerialNumber = mTelephonyManager.getLine1Number(); // Maybe return NULL
        if (phoneSerialNumber.isEmpty() || phoneSerialNumber.equals("")
                || phoneSerialNumber.trim().equals("null")
                || phoneSerialNumber.trim().length() <= 0) {

            phoneSerialNumber = Build.SERIAL; // Serial Number HP
        }

//            phoneSerialNumber = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        /*String serialNumberSIM = mTelephonyManager.getSimSerialNumber();
        String osAndroid = mTelephonyManager.getDeviceSoftwareVersion();
        String serialNumberHP = Build.SERIAL;*/

        String message = String.format(
                "Data Identity HP Anda " +
//                        "\n IMSI : %1$s " +
                        "\n IMEI/Android ID : %1$s " +
//                        "\n ANDROID ID : %3$s" +
                        "\n NO HP/Serial Number HP : %2$s",
//                        "\n SerialNumber SIM : %5$s" +
//                        "\n OS Android : %6$s" +
//                        "\n Serial Number HP : %6$s",
//                IMSI, deviceIdIMEI, androidID, phoneNumber, serialNumberSIM, osAndroid, serialNumberHP
                deviceIdIMEI, phoneSerialNumber
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
        mViewPagerAdapter.addFragment(new FragmentInfoTrafficList(), getString(R.string.tabInfo));
        mViewPagerAdapter.addFragment(new FragmentRequestInfo(), getString(R.string.tabSearch));
        mViewPagerAdapter.addFragment(new FragmentSendInfo(), getString(R.string.tabSend));
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
//            showRetrievingData();
            sendDataFragment();
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
//                L.t(getApplicationContext(), "Navigasi Info");
                mCurrentSelectedPosition = 0;
                mViewPager.setCurrentItem(mCurrentSelectedPosition);
                return true;

            case R.id.nav_request:
                hideDrawer();
//                L.t(getApplicationContext(), "Navigasi Request");
                mCurrentSelectedPosition = 1;
                mViewPager.setCurrentItem(mCurrentSelectedPosition);
                return true;

            case R.id.nav_sending:
                hideDrawer();
//                L.t(getApplicationContext(), "Navigasi Sending");
                mCurrentSelectedPosition = 2;
                mViewPager.setCurrentItem(mCurrentSelectedPosition);
                return true;

            case R.id.nav_maps:
                hideDrawer();
//                L.t(getApplicationContext(), "Navigasi Maps");
                mIntent = new Intent(this, ActivityMaps.class);
                startActivity(mIntent);
                mCurrentSelectedPosition = 3;
                return true;

            case R.id.nav_setting:
                hideDrawer();
//                L.t(getApplicationContext(), "Navigasi Setting");
                mCurrentSelectedPosition = 4;
                return true;

            case R.id.nav_about:
                hideDrawer();
//                L.t(getApplicationContext(), "Navigasi About");
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

    private void setupLocationManager() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MapsLocationListener()
        );

    }

    private void setupReverseGeocoding() {
        if (mLocation != null) {

            Geocoder mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            GET_LATITUDE = mLocation.getLatitude();
            GET_LONGITUDE = mLocation.getLongitude();

            List<Address> mAddresses = null;
            String addressText = "";

            try {
                mAddresses = mGeocoder.getFromLocation(GET_LATITUDE, GET_LONGITUDE, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mAddresses != null && mAddresses.size() > 0) {
                Address address = mAddresses.get(0);

                getNamaJalan = address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "";
                getNamaKecamatan = address.getLocality();
                getNamaKota = address.getSubAdminArea();
                getNamaPropinsi = address.getAdminArea();
                getNamaNegara = address.getCountryName();

                /*addressText = String.format("%s, %s, %s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", // Jalan Rokan No. 2
                        address.getLocality(), // Wonokromo (Kecamatan)
                        address.getSubAdminArea(), // Kota Surabaya
                        address.getAdminArea(), // East Java (Propinsi)
                        address.getCountryName()); // Indonesia (Negara)*/

            /*    mRoads = (TextView) mView.findViewById(R.id.txtNamaJalan);
                mRoads.setText(getNamaJalan);

                mSubDistrict = (TextView) mView.findViewById(R.id.txtNamaKecamatan);
                mSubDistrict.setText(getNamaKecamatan);

                mCity = (TextView) mView.findViewById(R.id.txtNamaKota);
                mCity.setText(getNamaKota);

                mRegion = (TextView) mView.findViewById(R.id.txtNamaPropinsi);
                mRegion.setText(getNamaPropinsi);

                mCountry = (TextView) mView.findViewById(R.id.txtNamaNegara);
                mCountry.setText(getNamaNegara);*/

            }
        }
    }

    private void setupLastKnownLocation() {
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (mLocation == null) {
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    private void setupShowCurrentLocation() {
        // Progress Loading Data
//        showProgress();

        if (mLocation != null) {
            GET_LATITUDE = mLocation.getLatitude();
            GET_LONGITUDE = mLocation.getLongitude();

            String message = String.format(
                    "Lokasi saat ini \n Latitude: %1$s \n Longitude: %2$s",
                    GET_LATITUDE, GET_LONGITUDE
            );

            Toast.makeText(getApplicationContext(), message,
                    Toast.LENGTH_LONG).show();

            getLatitude = Double.toString(GET_LATITUDE);
            getLongitude = Double.toString(GET_LONGITUDE);

            /*mLatitude = (TextView) mView.findViewById(R.id.txtLatitude);
            mLatitude.setText(getLatitude + ",");

            mLongitude = (TextView) mView.findViewById(R.id.txtLongitude);
            mLongitude.setText(getLongitude);*/

        }

//        stopProgress();
    }


    private void showRetrievingData() {
        // Progress Loading Data
//        showProgress();

        // Setting Location Manager
        setupLocationManager();

        // Setting LastKnownLocation
        setupLastKnownLocation();

        // Setting Location
        setupShowCurrentLocation();

        // Setting ReverseGeocoding
        setupReverseGeocoding();

        // Progress Stop Loading Data
//        stopProgress();

    }


    private class MapsLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            /*try {
                LatLng currentLokasi = new LatLng(location.getLatitude(), location.getLongitude());

            } catch (NullPointerException e){

            }*/
            try {
                if (mLocation != null) {
                    String message = String.format(
                            "Deteksi Lokasi Baru \n Latitude: %2$s \n Longitude: %1$s",
                            mLocation.getLatitude(), mLocation.getLongitude()
                    );
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            try {
                Toast.makeText(getApplicationContext(), "Status provider berubah",
                        Toast.LENGTH_LONG).show();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderEnabled(String s) {

            try {
                Toast.makeText(getApplicationContext(),
                        "Provider diaktifkan oleh user, GPS on",
                        Toast.LENGTH_LONG).show();

                setupLastKnownLocation();
                setupShowCurrentLocation();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onProviderDisabled(String s) {
            try {
                Toast.makeText(getApplicationContext(),
                        "Provider dinonaktifkan oleh user, GPS off",
                        Toast.LENGTH_LONG).show();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
