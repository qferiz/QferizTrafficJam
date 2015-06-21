package com.qferiz.trafficjam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_traffic_detail);

        Intent mIntent = getIntent();
//        Bundle extras = mIntent.getExtras();
//        if (extras != null) {
        final String strInfoNamaJalan = mIntent.getStringExtra(EXTRA_NAMA_JALAN);
        final String strInfoWaktu = mIntent.getStringExtra(EXTRA_WAKTU);
        final String strInfoKondisi = mIntent.getStringExtra(EXTRA_KONDISI);
        final String strInfoFoto = mIntent.getStringExtra(EXTRA_FOTO);
        final String strLongitude = mIntent.getStringExtra(EXTRA_LONGITUDE);
        final String strLatittude = mIntent.getStringExtra(EXTRA_LATITTUDE);
        final String strNamaWilayah = mIntent.getStringExtra(EXTRA_NAMA_WILAYAH);
        final String strLokasiFileFoto = mIntent.getStringExtra(EXTRA_LOKASI_FILE_FOTO);

//        }

        // Menampilkan waktu & koordinat
        TextView mInfoWaktu = (TextView) findViewById(R.id.subtitle_info);
        mInfoWaktu.setText(strInfoWaktu);

        TextView mLongitude = (TextView) findViewById(R.id.subtitle_info_maps);
        mLongitude.setText(strLongitude + ", " + strLatittude);

        TextView mInfoKondisi = (TextView) findViewById(R.id.subtitle_kondisi);
        mInfoKondisi.setText(strInfoKondisi);

        TextView mNamaWilayah = (TextView) findViewById(R.id.subtitle_keterangan);
        mNamaWilayah.setText(strNamaWilayah);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        mCollapsingToolbarLayout.setTitle(strInfoNamaJalan);

        loadBackdrop(strInfoFoto);

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
