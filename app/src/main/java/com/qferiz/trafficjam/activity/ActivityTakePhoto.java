package com.qferiz.trafficjam.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.qferiz.trafficjam.R;


public class ActivityTakePhoto extends AppCompatActivity {

    static final int REQUEST_CAPTURE_IMAGE = 1;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);

        Button mButtonTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);
        mImageView = (ImageView) findViewById(R.id.imagePhoto);

        if (!hasCamera()) {
            mButtonTakePhoto.setEnabled(false);
        }

    }

    // Check if the user has a Camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchCamera(View view) {
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Take a picture and pass result along to onActivityResult
        startActivityForResult(mIntent, REQUEST_CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            // Get a Photo
            Bundle mBundle = data.getExtras();
            Bitmap mPhoto = (Bitmap) mBundle.get("data");
            mImageView.setImageBitmap(mPhoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_take_photo, menu);
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
