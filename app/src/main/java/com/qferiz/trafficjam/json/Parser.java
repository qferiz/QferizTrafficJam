package com.qferiz.trafficjam.json;

import com.qferiz.trafficjam.callback.Constants;
import com.qferiz.trafficjam.extras.TrafficJam;
import com.qferiz.trafficjam.logging.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_ID_LOKASI;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_INFO_LOKASI;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_KOMENTAR;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_KONDISI_JALAN;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_LATITUDE;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_LOKASI_FOTO;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_LONGITUDE;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_NAMA_FILE_FOTO;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_NAMA_JALAN;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_NAMA_WILAYAH;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_NOHP;
import static com.qferiz.trafficjam.callback.Keys.EndpointInfoTraffic.KEY_WAKTU;

/**
 * Created by Qferiz on 15-06-2015.
 */
public class Parser {
    public static ArrayList<TrafficJam> parseInfoTrafficJSON(JSONObject response) {
        DateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ArrayList<TrafficJam> listTraffic = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayTraffic = response.getJSONArray(KEY_INFO_LOKASI);
                for (int i = 0; i < arrayTraffic.length(); i++) {
                    int idInfoLokasi = -1;
                    String noHp = Constants.NA;
                    String longitude = Constants.NA;
                    String latitude = Constants.NA;
                    String namaJalan = Constants.NA;
                    String namaWilayah = Constants.NA;
                    String kondisiJalan = Constants.NA;
                    String waktu = Constants.NA;
                    String namaFileFoto = Constants.NA;
                    String lokasiFoto = Constants.NA;
                    String komentar = Constants.NA;

                    JSONObject currentTraffic = arrayTraffic.getJSONObject(i);

                    // ambil id info lokasi pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_ID_LOKASI)) {
                        idInfoLokasi = currentTraffic.getInt(KEY_ID_LOKASI);
                    }

                    // ambil noHp pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_NOHP)) {
                        noHp = currentTraffic.getString(KEY_NOHP);
                    }

                    // ambil Longitude pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_LONGITUDE)) {
                        longitude = currentTraffic.getString(KEY_LONGITUDE);
                    }

                    // ambil Latitude pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_LATITUDE)) {
                        latitude = currentTraffic.getString(KEY_LATITUDE);
                    }

                    // ambil Nama Jalan pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_NAMA_JALAN)) {
                        namaJalan = currentTraffic.getString(KEY_NAMA_JALAN);
                    }

                    // ambil Nama Wilayah pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_NAMA_WILAYAH)) {
                        namaWilayah = currentTraffic.getString(KEY_NAMA_WILAYAH);
                    }

                    // ambil Kondisi Jalan pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_KONDISI_JALAN)) {
                        kondisiJalan = currentTraffic.getString(KEY_KONDISI_JALAN);
                    }

                    // ambil Waktu pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_WAKTU)) {
                        waktu = currentTraffic.getString(KEY_WAKTU);
                    }

                    // ambil Nama File Foto pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_NAMA_FILE_FOTO)) {
                        namaFileFoto = currentTraffic.getString(KEY_NAMA_FILE_FOTO);
                    }

                    // ambil Lokasi File Foto pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_LOKASI_FOTO)) {
                        lokasiFoto = currentTraffic.getString(KEY_LOKASI_FOTO);
                    }

                    // ambil Komentar pada pada Current Traffic (record yg aktif)
                    if (UtilsJson.contains(currentTraffic, KEY_KOMENTAR)) {
                        komentar = currentTraffic.getString(KEY_KOMENTAR);
                    }


                    TrafficJam mTrafficJam = new TrafficJam();
                    mTrafficJam.setId_info_lokasi(idInfoLokasi);
                    mTrafficJam.setNohp(noHp);
                    mTrafficJam.setLongitude(longitude);
                    mTrafficJam.setLatitude(latitude);
                    mTrafficJam.setNama_jalan(namaJalan);
                    mTrafficJam.setNama_wilayah(namaWilayah);
                    mTrafficJam.setKondisi(kondisiJalan);
//                    Date mDate = new Date();

//                    if (waktu != null){
//                        try {
//                            mDate = mDateFormat.parse(waktu);
//                        } catch (ParseException e) {
                    //a parse exception generated here will store null in the release date, be sure to handle it
//                            L.m("ParseException Error : "+ e +"Variabel mDate = " + mDate);
//                            if (mDate == null){
//                                waktu = currentTraffic.getString(KEY_WAKTU);
////                            mDate = mDateFormat.parse(waktu);
//                            }

//                        }

//                    }


//                    mDate = mDateFormat.parse(waktu);
                    mTrafficJam.setWaktu(waktu);
                    mTrafficJam.setNama_file_foto(namaFileFoto);
                    mTrafficJam.setLokasi_file_foto(lokasiFoto);
                    mTrafficJam.setKomentar(komentar);

                    if (idInfoLokasi != -1 && !longitude.equals(Constants.NA)) {
                        listTraffic.add(mTrafficJam);
                    }

                }

            } catch (JSONException e) {
                L.m("JSONObject Error : " + e);
            }
        }

        L.m("Jumlah Record : " + listTraffic.size() + " QFERIZ");
        return listTraffic;
    }
}
