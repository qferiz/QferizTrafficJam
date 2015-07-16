package com.qferiz.trafficjam.callback;

/**
 * Created by Qferiz on 16-07-2015.
 * Passing Data From Activity to Fragment
 */

public interface FragmentCommunicator {
    void passDataToFragment(String latitude, String longitude, String namaJalan,
                            String namaKecamatan, String namaKota,
                            String namaPropinsi, String namaNegara);

}
