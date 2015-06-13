package com.qferiz.trafficjam.extras;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Qferiz on 13-06-2015.
 */
public class TrafficJam implements Parcelable {

    private String id_info_lokasi;
    private String nohp;
    private String longitude;
    private String latittude;
    private String id_jalan;
    private String id_wilayah;
    private String id_kondisi_jalan;
    private String waktu;
    private String nama_file_foto;
    private String lokasi_file_foto;

    public TrafficJam(String id_info_lokasi,
                      String nohp,
                      String longitude,
                      String latittude,
                      String id_jalan,
                      String id_wilayah,
                      String id_kondisi_jalan,
                      String waktu,
                      String nama_file_foto,
                      String lokasi_file_foto) {
        super();
        this.id_info_lokasi = id_info_lokasi;
        this.nohp = nohp;
        this.longitude = longitude;
        this.latittude = latittude;
        this.id_jalan = id_jalan;
        this.id_wilayah = id_wilayah;
        this.id_kondisi_jalan = id_kondisi_jalan;
        this.waktu = waktu;
        this.nama_file_foto = nama_file_foto;
        this.lokasi_file_foto = lokasi_file_foto;

    }

    public TrafficJam() {
        super();
        this.id_info_lokasi = "";
        this.nohp = "";
        this.longitude = "";
        this.latittude = "";
        this.id_jalan = "";
        this.id_wilayah = "";
        this.id_kondisi_jalan = "";
        this.waktu = "";
        this.nama_file_foto = "";
        this.lokasi_file_foto = "";
    }

    public TrafficJam(Parcel input) {
        this.id_info_lokasi = input.readString();
        this.nohp = input.readString();
        this.longitude = input.readString();
        this.latittude = input.readString();
        this.id_jalan = input.readString();
        this.id_wilayah = input.readString();
        this.id_kondisi_jalan = input.readString();
        this.waktu = input.readString();
        this.nama_file_foto = input.readString();
        this.lokasi_file_foto = input.readString();
    }

    public String getId_info_lokasi() {
        return id_info_lokasi;
    }

    public void setId_info_lokasi(String id_info_lokasi) {
        this.id_info_lokasi = id_info_lokasi;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatittude() {
        return latittude;
    }

    public void setLatittude(String latittude) {
        this.latittude = latittude;
    }

    public String getId_jalan() {
        return id_jalan;
    }

    public void setId_jalan(String id_jalan) {
        this.id_jalan = id_jalan;
    }

    public String getId_wilayah() {
        return id_wilayah;
    }

    public void setId_wilayah(String id_wilayah) {
        this.id_wilayah = id_wilayah;
    }

    public String getId_kondisi_jalan() {
        return id_kondisi_jalan;
    }

    public void setId_kondisi_jalan(String id_kondisi_jalan) {
        this.id_kondisi_jalan = id_kondisi_jalan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getNama_file_foto() {
        return nama_file_foto;
    }

    public void setNama_file_foto(String nama_file_foto) {
        this.nama_file_foto = nama_file_foto;
    }

    public String getLokasi_file_foto() {
        return lokasi_file_foto;
    }

    public void setLokasi_file_foto(String lokasi_file_foto) {
        this.lokasi_file_foto = lokasi_file_foto;
    }

    @Override
    public String toString() {
        return "\nID Info Lokasi : " + id_info_lokasi +
                "\nNo Hp : " + nohp +
                "\nLongitude : " + longitude +
                "\nLatitude : " + latittude +
                "\nID Jalan : " + id_jalan +
                "\nID Wilayah : " + id_wilayah +
                "\nID Kondisi Jalan : " + id_kondisi_jalan +
                "\nWaktu : " + waktu +
                "\nNama File Foto : " + nama_file_foto +
                "\nLokasi Foto : " + lokasi_file_foto +
                "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_info_lokasi);
        parcel.writeString(nohp);
        parcel.writeString(longitude);
        parcel.writeString(latittude);
        parcel.writeString(id_jalan);
        parcel.writeString(id_wilayah);
        parcel.writeString(id_kondisi_jalan);
        parcel.writeString(waktu);
        parcel.writeString(nama_file_foto);
        parcel.writeString(lokasi_file_foto);
    }

    public static final Parcelable.Creator<TrafficJam> CREATOR = new Parcelable.Creator<TrafficJam>() {
        public TrafficJam createFromParcel(Parcel in) {
            return new TrafficJam(in);
        }

        @Override
        public TrafficJam[] newArray(int size) {
            return new TrafficJam[size];
        }
    };
}
