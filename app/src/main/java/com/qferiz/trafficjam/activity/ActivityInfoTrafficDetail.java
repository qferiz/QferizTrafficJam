package com.qferiz.trafficjam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qferiz.trafficjam.R;

public class ActivityInfoTrafficDetail extends AppCompatActivity {
    public static final String EXTRA_NAMA_JALAN = "info_traffic_jam";
    public static final String EXTRA_WAKTU = "info_waktu";
    public static final String EXTRA_KONDISI = "info_kondisi";
    public static final String EXTRA_FOTO = "info_foto";
    public static final String EXTRA_LONGITUDE = "info_longitude";
    public static final String EXTRA_LATITTUDE = "info_latittude";
    public static final String EXTRA_NAMA_WILAYAH = "info_nama_wilayah";
    public static final String EXTRA_LOKASI_FILE_FOTO = "info_lokasi_file_foto";
    public static final String EXTRA_KOMENTAR = "info_komentar";
    private Context mContext;
    private String strInfoNamaJalan = "info_nama_jalan";
    private String strInfoWaktu = "info_waktu";
    private String strInfoKondisi = "info_kondisi";
    private String strInfoFoto = "info_foto";
    private String strLongitude = "info_longitude";
    private String strLatitude = "info_latitude";
    private String strNamaWilayah = "info_nama_wilayah";
    private String strLokasiFileFoto = "info_lokasi_file_foto";
    private String strKomentar = "info_komentar";
    private TextView mInfoWaktu, mLongitude, mLatitude, mInfoKondisi, mNamaWilayah, mKomentar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_traffic_detail);

        Intent mIntent = getIntent();
//        Bundle extras = mIntent.getExtras();
//        if (extras != null) {
        strInfoNamaJalan = mIntent.getStringExtra(EXTRA_NAMA_JALAN);
        strInfoWaktu = mIntent.getStringExtra(EXTRA_WAKTU);
        strInfoKondisi = mIntent.getStringExtra(EXTRA_KONDISI);
        strInfoFoto = mIntent.getStringExtra(EXTRA_FOTO);
        strLatitude = mIntent.getStringExtra(EXTRA_LATITTUDE);
        strLongitude = mIntent.getStringExtra(EXTRA_LONGITUDE);
        strNamaWilayah = mIntent.getStringExtra(EXTRA_NAMA_WILAYAH);
        strLokasiFileFoto = mIntent.getStringExtra(EXTRA_LOKASI_FILE_FOTO);
        strKomentar = mIntent.getStringExtra(EXTRA_KOMENTAR);

//        }

        // Menampilkan waktu & koordinat
        mInfoWaktu = (TextView) findViewById(R.id.subtitle_waktu_ket);
        mInfoWaktu.setText(strInfoWaktu);

        mLatitude = (TextView) findViewById(R.id.txtLatitude);
        mLatitude.setText(strLatitude);

        mLongitude = (TextView) findViewById(R.id.txtLongitude);
        mLongitude.setText(strLongitude);

        mInfoKondisi = (TextView) findViewById(R.id.subtitle_kondisi);
        mInfoKondisi.setText(strInfoKondisi);

        mKomentar = (TextView) findViewById(R.id.subtitle_komentar);
        mKomentar.setText(strKomentar);

        setupToolbar();

        CollapsingToolbarLayout mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mCollapsingToolbarLayout.setTitle(strInfoNamaJalan);

        setupFAB();

        loadBackdrop(strInfoFoto);

    }

    private void setupToolbar() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFAB() {
        FloatingActionButton mFAB = (FloatingActionButton) findViewById(R.id.fab_infotraffic_detail);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getApplicationContext(), ActivityInfoTrafficMapsDetail.class);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_NAMA_JALAN, strInfoNamaJalan);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_WAKTU, strInfoWaktu);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_KONDISI, strInfoKondisi);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_FOTO, strInfoFoto);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_LATITTUDE, strLatitude);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_LONGITUDE, strLongitude);
                mIntent.putExtra(ActivityInfoTrafficMapsDetail.EXTRA_KOMENTAR, strKomentar);

                startActivity(mIntent);
            }
        });
    }

    private void loadBackdrop(String urlFoto) {
        final ImageView mImageView = (ImageView) findViewById(R.id.backdrop);
        // Fetching Image dengan Glide
        Glide.with(this).load(urlFoto)
                .centerCrop()
                .placeholder(R.drawable.loading_spinner4)
                .crossFade()
                .into(mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_info_traffic_detail, menu);
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
    }
}
