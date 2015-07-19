package com.qferiz.trafficjam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qferiz.trafficjam.R;

public class ActivityInfoTrafficMapsDetail extends AppCompatActivity {
    public static final String EXTRA_NAMA_JALAN = "info_traffic_jam";
    public static final String EXTRA_WAKTU = "info_waktu";
    public static final String EXTRA_KONDISI = "info_kondisi";
    public static final String EXTRA_FOTO = "info_foto";
    public static final String EXTRA_LONGITUDE = "info_longitude";
    public static final String EXTRA_LATITTUDE = "info_latittude";
    public static final String EXTRA_NAMA_WILAYAH = "info_nama_wilayah";
    public static final String EXTRA_LOKASI_FILE_FOTO = "info_lokasi_file_foto";
    public static final String EXTRA_KOMENTAR = "info_komentar";
    private String strInfoNamaJalan = "info_nama_jalan";
    private String strInfoWaktu = "info_waktu";
    private String strInfoKondisi = "info_kondisi";
    private String strInfoFoto = "info_foto";
    private String strLongitude = "info_longitude";
    private String strLatittude = "info_latitude";
    private String strNamaWilayah = "info_nama_wilayah";
    private String strLokasiFileFoto = "info_lokasi_file_foto";
    private String strKomentar = "info_komentar";
    private GoogleMap mGoogleMap;
    private Double mGetLatittude;
    private Double mGetLongitude;
    private LatLng mLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_traffic_maps_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapTrafficDetail);
        mGoogleMap = mapFragment.getMap();
        mGoogleMap.setMyLocationEnabled(true);

        Intent mIntent = getIntent();

        if (mIntent != null) {
            strInfoNamaJalan = mIntent.getStringExtra(EXTRA_NAMA_JALAN);
            strInfoWaktu = mIntent.getStringExtra(EXTRA_WAKTU);
            strInfoKondisi = mIntent.getStringExtra(EXTRA_KONDISI);
            strInfoFoto = mIntent.getStringExtra(EXTRA_FOTO);
            strLongitude = mIntent.getStringExtra(EXTRA_LONGITUDE);
            strLatittude = mIntent.getStringExtra(EXTRA_LATITTUDE);
            strKomentar = mIntent.getStringExtra(EXTRA_KOMENTAR);

            mGetLatittude = Double.valueOf(strLatittude);
            mGetLongitude = Double.valueOf(strLongitude);

            mLatLng = new LatLng(mGetLatittude, mGetLongitude);

            Marker mMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(mLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title("Kondisi : " + strInfoKondisi + ", " + "Tanggal : " + strInfoWaktu)
                    .snippet(strInfoNamaJalan));

            mMarker.showInfoWindow();
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 17));
        }

        setupToolbar();
    }


    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_maps);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(strInfoNamaJalan);
            getSupportActionBar().setSubtitle(getResources().getString(R.string.txtKondisi) + " : " + strInfoKondisi);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_info_traffic_maps_detail, menu);
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

        return super.onOptionsItemSelected(item);
    }*/
}
