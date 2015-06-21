package com.qferiz.trafficjam.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.qferiz.trafficjam.extras.TrafficJam;
import com.qferiz.trafficjam.logging.L;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Qferiz on 21-06-2015.
 */
public class TrafficJamDB {
    public static int INFO_TRAFFIC = 0;
    private SQLiteDatabase mDatabase;
    private TrafficHelper mHelper;

    public TrafficJamDB(Context context) {
        mHelper = new TrafficHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertTrafficJam(ArrayList<TrafficJam> listTraffic, boolean clearPrevious) {
        if (clearPrevious) {
            deleteTrafficJam();
        }

        // create a sql prepared statement
        String sql = " INSERT INTO " + TrafficHelper.TABLE_INFO_TRAFFIC + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < listTraffic.size(); i++) {
            TrafficJam currentTraffic = listTraffic.get(i);
            statement.clearBindings();
            // For a given coloumn index, simply bind the data to be put inside that index
            statement.bindString(2, currentTraffic.getNohp());
            statement.bindString(3, currentTraffic.getLongitude());
            statement.bindString(4, currentTraffic.getLatittude());
            statement.bindString(5, currentTraffic.getNama_jalan());
            statement.bindString(6, currentTraffic.getNama_wilayah());
            statement.bindString(7, currentTraffic.getKondisi());
            statement.bindString(8, currentTraffic.getWaktu());
            statement.bindString(9, currentTraffic.getNama_file_foto());
            statement.bindString(10, currentTraffic.getLokasi_file_foto());
            L.m("Inserting entry " + i);
            statement.execute();
        }

        // Set the Transaction as Succesfull and end the transaction
        L.m("Inserting entries " + listTraffic.size() + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }


    public ArrayList<TrafficJam> readTrafficJam() {
        ArrayList<TrafficJam> listTraffic = new ArrayList<>();

        // Get a list of coloumn to be retrieved, we need all of them
        String[] columns = {TrafficHelper.COLUMN_UID,
                TrafficHelper.COLUMN_NOHP,
                TrafficHelper.COLUMN_LONGITUDE,
                TrafficHelper.COLUMN_LATITTUDE,
                TrafficHelper.COLUMN_NAMA_JALAN,
                TrafficHelper.COLUMN_NAMA_WILAYAH,
                TrafficHelper.COLUMN_KONDISI,
                TrafficHelper.COLUMN_WAKTU,
                TrafficHelper.COLUMN_NAMA_FILE_FOTO,
                TrafficHelper.COLUMN_URL_LOKASI_FILE_FOTO
        };

        Cursor mCursor = mDatabase.query(TrafficHelper.TABLE_INFO_TRAFFIC, columns, null, null, null, null, null);
        if (mCursor != null && mCursor.moveToFirst()) {
            L.m("Loading entries " + mCursor.getCount() + new Date(System.currentTimeMillis()));
            do {
                // Create a new movie object and retrieve the data from the mCursor to be stored in this movie object
                TrafficJam mTrafficJam = new TrafficJam();
                // Each step is a 2 part process, find the index of the columns first, find the data of that columns using
                // that index and finally set our a blank mMovie object to contain our data
                mTrafficJam.setNohp(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_NOHP)));
                mTrafficJam.setLongitude(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_LONGITUDE)));
                mTrafficJam.setLatittude(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_LATITTUDE)));
                mTrafficJam.setNama_jalan(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_NAMA_JALAN)));
                mTrafficJam.setNama_wilayah(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_NAMA_WILAYAH)));
                mTrafficJam.setKondisi(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_KONDISI)));
                mTrafficJam.setWaktu(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_WAKTU)));
                mTrafficJam.setNama_file_foto(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_NAMA_FILE_FOTO)));
                mTrafficJam.setLokasi_file_foto(mCursor.getString(mCursor.getColumnIndex(TrafficHelper.COLUMN_URL_LOKASI_FILE_FOTO)));
                // Add the movie to the list of movie object which we plan to return
                L.m("Getting TrafficJam Object " + mTrafficJam);
                listTraffic.add(mTrafficJam);

            } while (mCursor.moveToNext());
        }

        return listTraffic;
    }

    private void deleteTrafficJam() {
        mDatabase.delete(TrafficHelper.TABLE_INFO_TRAFFIC, null, null);
    }

    private static class TrafficHelper extends SQLiteOpenHelper {
        private Context mContext;
        private static final String DB_NAME = "trafficjam_db";
        private static final int DB_VERSION = 1;
        private static final String TABLE_INFO_TRAFFIC = "tb_info_traffic";
        private static final String COLUMN_UID = "_id";
        private static final String COLUMN_NOHP = "nohp";
        private static final String COLUMN_LONGITUDE = "longitude";
        private static final String COLUMN_LATITTUDE = "latittude";
        private static final String COLUMN_NAMA_JALAN = "nama_jalan";
        private static final String COLUMN_NAMA_WILAYAH = "nama_wilayah";
        private static final String COLUMN_KONDISI = "kondisi";
        private static final String COLUMN_WAKTU = "waktu";
        private static final String COLUMN_NAMA_FILE_FOTO = "nama_file_foto";
        private static final String COLUMN_URL_LOKASI_FILE_FOTO = "url_lokasi_file_foto";
        private static final String CREATE_TABLE_INFO_TRAFFIC = "CREATE TABLE " + TABLE_INFO_TRAFFIC + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOHP + " TEXT," +
                COLUMN_LONGITUDE + " TEXT," +
                COLUMN_LATITTUDE + " TEXT," +
                COLUMN_NAMA_JALAN + " TEXT," +
                COLUMN_NAMA_WILAYAH + " TEXT," +
                COLUMN_KONDISI + " TEXT," +
                COLUMN_WAKTU + " TEXT," +
                COLUMN_NAMA_FILE_FOTO + " TEXT," +
                COLUMN_URL_LOKASI_FILE_FOTO + " TEXT" +
                ");";

        public TrafficHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE_INFO_TRAFFIC);
                L.m("Create Tabel Info Traffic Executed");
            } catch (SQLiteException exception) {
                L.t(mContext, "SQLite Error OnCreate : " + exception);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            try {
                L.m("Upgrade Tabel Info Traffic Executed");
                sqLiteDatabase.execSQL(" DROP TABLE " + TABLE_INFO_TRAFFIC + " IF EXIST;");
                onCreate(sqLiteDatabase);

            } catch (SQLiteException exception) {
                L.t(mContext, "SQLite Error OnUpgrade : " + exception);


            }


        }
    }

}
