package com.qferiz.trafficjam.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qferiz.trafficjam.R;
import com.qferiz.trafficjam.extras.AndroidMultiPartEntity;
import com.qferiz.trafficjam.extras.AndroidMultiPartEntity.ProgressListener;
import com.qferiz.trafficjam.extras.UrlEndpoints;
import com.qferiz.trafficjam.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityUpload extends AppCompatActivity {
    // image or video path that is captured in previous activity
    public static final String EXTRA_FILE_PATH = "file_path";
    // boolean flag to identify the media type, image or video
    public static final String EXTRA_MEDIA_TYPE = "media_type";

    // LogCat tag
    private static final String TAG = ActivityUpload.class.getSimpleName();

    private ProgressBar mProgressBar;
    private String mFilePath = null;
    private Boolean bolMediaType;
    private TextView txtPercentage;
    private CircleImageView mImageUpload;
    long mTotalSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        setupToolbar();

        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mImageUpload = (CircleImageView) findViewById(R.id.imageUpload);

        Intent mIntent = getIntent();

        if (mIntent != null) {
            // image or video path that is captured in previous activity
            mFilePath = mIntent.getStringExtra(EXTRA_FILE_PATH);

            // boolean flag to identify the media type, image or video
            bolMediaType = mIntent.getBooleanExtra(EXTRA_MEDIA_TYPE, true);

            if (mFilePath != null) {
                // Displaying the image or video on the screen
                previewMedia(bolMediaType);
            }

            // uploading the file to server
            new UploadFileToServer().execute();
        }
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.submain_toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Displaying captured image/video on the screen
     */
    private void previewMedia(Boolean bolMediaType) {
        // Checking whether captured media is image or video
        if (bolMediaType) { // true = IMAGE FILE
            mImageUpload.setVisibility(View.VISIBLE);
//            vidPreview.setVisibility(View.GONE);
            mImageUpload.setImageBitmap(Utils.lessResolution(mFilePath, 250, 250));
        }
        /*else { // false = VIDEO FILE
            imgPreview.setVisibility(View.GONE);
            vidPreview.setVisibility(View.VISIBLE);
            vidPreview.setVideoPath(filePath);
            // start playing
            vidPreview.start();
        }*/
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            mProgressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
//            super.onProgressUpdate(progress);

            // Making progress bar visible
            mProgressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            mProgressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... voids) {
            return uploadMediaFile();
        }


        @SuppressWarnings("deprecation")
        private String uploadMediaFile() {
            String mResponseString = null;

            HttpClient mHttpClient = new DefaultHttpClient();
            HttpPost mHttpPost = new HttpPost(UrlEndpoints.INSERT_TRAFFIC_JAM);

            try {
                AndroidMultiPartEntity mEntity = new AndroidMultiPartEntity(
                        new ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) mTotalSize) * 100));
                            }
                        });

                File mSourceFile = new File(mFilePath);

                // Adding file data to http body
                mEntity.addPart("image", new FileBody(mSourceFile));

                // Extra parameters if you want to pass to server
                mEntity.addPart("website", new StringBody("www.trafficjam.qferiz.com"));
                mEntity.addPart("email", new StringBody("rizki.feriz@gmail.com"));

                mTotalSize = mEntity.getContentLength();
                mHttpPost.setEntity(mEntity);

                // Making server call
                HttpResponse mResponse = mHttpClient.execute(mHttpPost);
                HttpEntity r_entity = mResponse.getEntity();

                int mStatusCode = mResponse.getStatusLine().getStatusCode();
                if (mStatusCode == 200) { // Jika Berhasil Upload File
                    // Server Response
                    mResponseString = EntityUtils.toString(r_entity);

                } else {
                    mResponseString = "Error occurred! Http Status Code: " + mStatusCode;
                }

            } catch (ClientProtocolException e) {
                mResponseString = e.toString();

            } catch (IOException e) {
                mResponseString = e.toString();
            }

            return mResponseString;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
            showAlert(result);

            super.onPostExecute(result);
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setMessage(message).setTitle("Response from Server")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do Nothing
                    }
                });

        AlertDialog mAlertDialog = mBuilder.create();
        mAlertDialog.show();
    }


}
