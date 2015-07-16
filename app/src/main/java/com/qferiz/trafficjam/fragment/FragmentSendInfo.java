package com.qferiz.trafficjam.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.qferiz.trafficjam.R;
import com.qferiz.trafficjam.logging.L;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSendInfo extends Fragment {

    private LocationManager mLocationManager;
    Location mLocation;
    private double GET_LONGITUDE;
    private double GET_LATITUDE;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10; // dalam meter
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 60000; // dalam Miliseconds
    private static final String TAG_REFRESH = "REFRESH";

    public Context mContext;
    private Spinner mSpinnerKondisi;
    //    private Spinner mSpinnerWilayah;
    private View mView;
    private Button mButton;
    private CircleImageView mCircleImageView;
    private TextView mLatitude, mLongitude, mRoads, mSubDistrict, mCity, mRegion, mCountry;
    private ProgressDialog mProgressDialog;
    private MyReceiver mReceiver;
    protected FragmentActivity mFragmentActivity;
    private Menu mMenu;

    private String getExtraLatitude, getExtraLongitude, getExtraNamaJalan, getExtraNamaKecamatan,
            getExtraKota, getExtraPropinsi, getExtraNegara = "";

    public static final String EXTRA_LATITUDE = "send_latitude";
    public static final String EXTRA_LONGITUDE = "send_longitude";
    public static final String EXTRA_NAMA_JALAN = "send_nama_jalan";
    public static final String EXTRA_NAMA_KECAMATAN = "send_nama_kecamatan";
    public static final String EXTRA_KOTA = "send_kota";
    public static final String EXTRA_PROPINSI = "send_propinsi";
    public static final String EXTRA_NEGARA = "send_negara";


    public FragmentSendInfo() {
        // Required empty public constructor
    }

   /* public static FragmentSendInfo newInstance() {

        *//*Bundle args = new Bundle();

        FragmentSendInfo fragment = new FragmentSendInfo();
        fragment.setArguments(args);
        return fragment;*//*
        return new FragmentSendInfo();
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        mContext = getActivity();
//        ((ActivityMain) mContext).mFragmentCommunicator = this;
        mFragmentActivity = (FragmentActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cek Value dari passing data Activity
       /* if (savedInstanceState != null) {
            getExtraLatitude = savedInstanceState.getString(EXTRA_LATITUDE);
            getExtraLongitude = savedInstanceState.getString(EXTRA_LONGITUDE);
            getExtraNamaJalan = savedInstanceState.getString(EXTRA_NAMA_JALAN);
            getExtraNamaKecamatan = savedInstanceState.getString(EXTRA_NAMA_KECAMATAN);
            getExtraKota = savedInstanceState.getString(EXTRA_KOTA);
            getExtraPropinsi = savedInstanceState.getString(EXTRA_PROPINSI);
            getExtraNegara = savedInstanceState.getString(EXTRA_NEGARA);
        }*/

        setHasOptionsMenu(true);

        // Setting Location Manager
//        setupLocationManager();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      /*  outState.putString(EXTRA_LATITUDE, getExtraLatitude);
        outState.putString(EXTRA_LONGITUDE, getExtraLongitude);
        outState.putString(EXTRA_NAMA_JALAN, getExtraNamaJalan);
        outState.putString(EXTRA_NAMA_KECAMATAN, getExtraNamaKecamatan);
        outState.putString(EXTRA_KOTA, getExtraKota);
        outState.putString(EXTRA_PROPINSI, getExtraPropinsi);
        outState.putString(EXTRA_NEGARA, getExtraNegara);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_send_info, container, false);
        mView = inflater.inflate(R.layout.fragment_send_info, container, false);

   /*     if (savedInstanceState != null){
            // Read Data From ActivityMain
            readData();
        }*/

        if (mMenu != null) {
            mMenu.findItem(R.id.my_location).setVisible(false);
        }

        // Progress Retrieving Data
//        showRetrievingData();

        mSpinnerKondisi = (Spinner) mView.findViewById(R.id.spinnerKondisi);
        ArrayAdapter<CharSequence> mAdapterKondisi = ArrayAdapter.createFromResource(
                getActivity(), R.array.object_kondisi, android.R.layout.simple_spinner_item);
        mAdapterKondisi.setDropDownViewResource(R.layout.my_spinner_item);
        mSpinnerKondisi.setAdapter(mAdapterKondisi);

        mSpinnerKondisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                L.T(getActivity(), "Item Number Kondisi : " + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       /* mButton = (Button) mView.findViewById(R.id.btnSend);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.t(getActivity(), "Tes Sending...");
            }
        });*/

        mCircleImageView = (CircleImageView) mView.findViewById(R.id.imageLaLin);
        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.t(getActivity(), "Tes Upload Foto...");
            }
        });

        FloatingActionButton mFAB = (FloatingActionButton) mView.findViewById(R.id.fabSendInfo);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.t(getActivity(), "Tes Sending...");
//                Snackbar.make(mView, "Direction", Snackbar.LENGTH_LONG)
//                .setAction("GO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        L.t(getActivity(), "Direction Maps");
//                    }
//                }).show();
            }
        });

//        setRetainInstance(true);
        return mView;
    }

    /*private void readData() {

        *//*Bundle mBundle = getActivity().getIntent().getExtras();
        String strLatitude = mBundle.getString(EXTRA_LATITUDE);
        String strLongitude = mBundle.getString(EXTRA_LONGITUDE);
        String strNamaJalan = mBundle.getString(EXTRA_NAMA_JALAN);
        String strNamaKecamatan = mBundle.getString(EXTRA_NAMA_KECAMATAN);
        String strNamaKota = mBundle.getString(EXTRA_KOTA);
        String strNamaPropinsi = mBundle.getString(EXTRA_PROPINSI);
        String strNegara = mBundle.getString(EXTRA_NEGARA);*//*
        Bundle mBundle = this.getArguments();

        if (mBundle != null) {
            String strLatitude = mBundle.getString("latitude");
            String strLongitude = mBundle.getString("longitude");
            String strNamaJalan = mBundle.getString("nama_jalan");
            String strNamaKecamatan = mBundle.getString("nama_kecamatan");
            String strNamaKota = mBundle.getString("nama_kota");
            String strNamaPropinsi = mBundle.getString("nama_propinsi");
            String strNegara = mBundle.getString("nama_negara");

            mLatitude = (TextView) mView.findViewById(R.id.txtLatitude);
            mLatitude.setText(strLatitude + ",");

            mLongitude = (TextView) mView.findViewById(R.id.txtLongitude);
            mLongitude.setText(strLongitude);

            mRoads = (TextView) mView.findViewById(R.id.txtNamaJalan);
            mRoads.setText(strNamaJalan);

            mSubDistrict = (TextView) mView.findViewById(R.id.txtNamaKecamatan);
            mSubDistrict.setText(strNamaKecamatan);

            mCity = (TextView) mView.findViewById(R.id.txtNamaKota);
            mCity.setText(strNamaKota);

            mRegion = (TextView) mView.findViewById(R.id.txtNamaPropinsi);
            mRegion.setText(strNamaPropinsi);

            mCountry = (TextView) mView.findViewById(R.id.txtNamaNegara);
            mCountry.setText(strNegara);
        }

    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();

//        setupLastKnownLocation();
    }

    /*public void init() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(true);

    }*/

    public void refresh() {
        // Progress Retrieving Data
        showRetrievingData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mReceiver = new MyReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver,
                new IntentFilter(TAG_REFRESH));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Methode Refresh di panggil ketika fragment dipanggil/background proses
            FragmentSendInfo.this.refresh();
        }
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

    private void showProgress() {
        mProgressDialog = ProgressDialog.show(getActivity(), "Retrieving Data", "Loading...", true);
    }

    private void stopProgress() {
        mProgressDialog.cancel();
    }

    private void setupReverseGeocoding() {
        if (mLocation != null) {

            Geocoder mGeocoder = new Geocoder(getActivity(), Locale.getDefault());
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

                String getNamaJalan = address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "";
                String getNamaKecamatan = address.getLocality();
                String getNamaKota = address.getSubAdminArea();
                String getNamaPropinsi = address.getAdminArea();
                String getNamaNegara = address.getCountryName();

                /*addressText = String.format("%s, %s, %s, %s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", // Jalan Rokan No. 2
                        address.getLocality(), // Wonokromo (Kecamatan)
                        address.getSubAdminArea(), // Kota Surabaya
                        address.getAdminArea(), // East Java (Propinsi)
                        address.getCountryName()); // Indonesia (Negara)*/

                mRoads = (TextView) mView.findViewById(R.id.txtNamaJalan);
                mRoads.setText(getNamaJalan);

                mSubDistrict = (TextView) mView.findViewById(R.id.txtNamaKecamatan);
                mSubDistrict.setText(getNamaKecamatan);

                mCity = (TextView) mView.findViewById(R.id.txtNamaKota);
                mCity.setText(getNamaKota);

                mRegion = (TextView) mView.findViewById(R.id.txtNamaPropinsi);
                mRegion.setText(getNamaPropinsi);

                mCountry = (TextView) mView.findViewById(R.id.txtNamaNegara);
                mCountry.setText(getNamaNegara);

            }
        }
    }

    private void setupLastKnownLocation() {
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (mLocation == null) {
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLocation = ((ActivityMain) getActivity()).mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }*/

    private void setupShowCurrentLocation() {
        // Progress Loading Data
//        showProgress();

        if (mLocation != null) {
            GET_LATITUDE = mLocation.getLatitude();
            GET_LONGITUDE = mLocation.getLongitude();

         /*   String message = String.format(
                    "Lokasi saat ini \n Latitude: %1$s \n Longitude: %2$s",
                    GET_LATITUDE, GET_LONGITUDE
            );

            Toast.makeText(getActivity(), message,
                    Toast.LENGTH_LONG).show();*/

            String strLatitude = Double.toString(GET_LATITUDE);
            String strLongitude = Double.toString(GET_LONGITUDE);

            mLatitude = (TextView) mView.findViewById(R.id.txtLatitude);
            mLatitude.setText(strLatitude + ",");

            mLongitude = (TextView) mView.findViewById(R.id.txtLongitude);
            mLongitude.setText(strLongitude);

        }

//        stopProgress();
    }

    private void setupLocationManager() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MapsLocationListener()
        );

    }

   /* public void updateDataFragmentSendInfo(String latitude, String longitude, String namaJalan) {

        getExtraLatitude = latitude;
        getExtraLongitude = longitude;
        getExtraNamaJalan = namaJalan;
      *//*  getExtraNamaKecamatan = namaKecamatan;
        getExtraKota = namaKota;
        getExtraPropinsi = namaPropinsi;
        getExtraNegara = namaNegara;*//*

        mLatitude = (TextView) mView.findViewById(R.id.txtLatitude);
        mLatitude.setText(getExtraLatitude + ",");

        mLongitude = (TextView) mView.findViewById(R.id.txtLongitude);
        mLongitude.setText(getExtraLongitude);

        mRoads = (TextView) mView.findViewById(R.id.txtNamaJalan);
        mRoads.setText(getExtraNamaJalan);

    *//*    mSubDistrict = (TextView) mView.findViewById(R.id.txtNamaKecamatan);
        mSubDistrict.setText(getExtraNamaKecamatan);

        mCity = (TextView) mView.findViewById(R.id.txtNamaKota);
        mCity.setText(getExtraKota);

        mRegion = (TextView) mView.findViewById(R.id.txtNamaPropinsi);
        mRegion.setText(getExtraPropinsi);

        mCountry = (TextView) mView.findViewById(R.id.txtNamaNegara);
        mCountry.setText(getExtraNegara);*//*
    }
*/
    // Passing Data From Activity to Fragment
    //FragmentCommunicator interface implementation
//    @Override
    /*public void passDataToFragment(String latitude, String longitude, String namaJalan,
                                   String namaKecamatan, String namaKota,
                                   String namaPropinsi, String namaNegara) {

        getExtraLatitude = latitude;
        getExtraLongitude = longitude;
        getExtraNamaJalan = namaJalan;
        getExtraNamaKecamatan = namaKecamatan;
        getExtraKota = namaKota;
        getExtraPropinsi = namaPropinsi;
        getExtraNegara = namaNegara;

        mLatitude = (TextView) mView.findViewById(R.id.txtLatitude);
        mLatitude.setText(getExtraLatitude + ",");

        mLongitude = (TextView) mView.findViewById(R.id.txtLongitude);
        mLongitude.setText(getExtraLongitude);

        mRoads = (TextView) mView.findViewById(R.id.txtNamaJalan);
        mRoads.setText(getExtraNamaJalan);

        mSubDistrict = (TextView) mView.findViewById(R.id.txtNamaKecamatan);
        mSubDistrict.setText(getExtraNamaKecamatan);

        mCity = (TextView) mView.findViewById(R.id.txtNamaKota);
        mCity.setText(getExtraKota);

        mRegion = (TextView) mView.findViewById(R.id.txtNamaPropinsi);
        mRegion.setText(getExtraPropinsi);

        mCountry = (TextView) mView.findViewById(R.id.txtNamaNegara);
        mCountry.setText(getExtraNegara);

    }*/


    private class MapsLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            /*try {
                LatLng currentLokasi = new LatLng(location.getLatitude(), location.getLongitude());

            } catch (NullPointerException e){

            }*/
           /* try {
                if (mLocation != null) {
                    String message = String.format(
                            "Deteksi Lokasi Baru \n Latitude: %2$s \n Longitude: %1$s",
                            mLocation.getLatitude(), mLocation.getLongitude()
                    );
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }*/

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            try {
                Toast.makeText(getActivity(), "Status provider berubah",
                        Toast.LENGTH_LONG).show();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderEnabled(String s) {

            try {
                Toast.makeText(getActivity(),
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
                Toast.makeText(getActivity(),
                        "Provider dinonaktifkan oleh user, GPS off",
                        Toast.LENGTH_LONG).show();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_fragment_send_info, menu);

//
        MenuItem item = menu.add(Menu.NONE, R.id.my_location, 10, R.string.my_location);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_action_my_location);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_location:
                // Progress Retrieving Data
//                showRetrievingData();
                showProgress();
                refresh();
                stopProgress();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
