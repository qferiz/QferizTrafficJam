package com.qferiz.trafficjam.extras;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Qferiz on 13-06-2015.
 */
public class TrafficJam implements Parcelable {

    private int id_info_lokasi;
    private String nohp;
    private String longitude;
    private String latitude;
    private String nama_jalan;
    private String nama_wilayah;
    private String kondisi;
    //    private Date waktu;
    private String waktu;
    private String nama_file_foto;
    private String lokasi_file_foto;
    private String komentar;

    public TrafficJam(int id_info_lokasi,
                      String nohp,
                      String longitude,
                      String latitude,
                      String nama_jalan,
                      String nama_wilayah,
                      String kondisi,
//                      Date waktu,
                      String waktu,
                      String nama_file_foto,
                      String lokasi_file_foto,
                      String komentar) {
        super();
        this.id_info_lokasi = id_info_lokasi;
        this.nohp = nohp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nama_jalan = nama_jalan;
        this.nama_wilayah = nama_wilayah;
        this.kondisi = kondisi;
        this.waktu = waktu;
        this.nama_file_foto = nama_file_foto;
        this.lokasi_file_foto = lokasi_file_foto;
        this.komentar = komentar;

    }

    public TrafficJam() {
//        super();
//        this.id_info_lokasi = 0;
//        this.nohp = "";
//        this.longitude = "";
//        this.latitude = "";
//        this.nama_jalan = "";
//        this.nama_wilayah = "";
//        this.kondisi = "";
//        this.waktu = null;
//        this.nama_file_foto = "";
//        this.lokasi_file_foto = "";
    }

    public TrafficJam(Parcel input) {
        this.id_info_lokasi = input.readInt();
        this.nohp = input.readString();
        this.longitude = input.readString();
        this.latitude = input.readString();
        this.nama_jalan = input.readString();
        this.nama_wilayah = input.readString();
        this.kondisi = input.readString();
        long dateMillis = input.readLong();
//        this.waktu = (dateMillis == -1 ? null: new Date(dateMillis));
        this.waktu = input.readString();
        this.nama_file_foto = input.readString();
        this.lokasi_file_foto = input.readString();
        this.komentar = input.readString();
    }

    public int getId_info_lokasi() {
        return id_info_lokasi;
    }

    public void setId_info_lokasi(int id_info_lokasi) {
        this.id_info_lokasi = id_info_lokasi;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getNama_jalan() {
        return nama_jalan;
    }

    public void setNama_jalan(String nama_jalan) {
        this.nama_jalan = nama_jalan;
    }

    public String getNama_wilayah() {
        return nama_wilayah;
    }

    public void setNama_wilayah(String nama_wilayah) {
        this.nama_wilayah = nama_wilayah;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
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
                "\nLatitude : " + latitude +
                "\nNama Jalan : " + nama_jalan +
                "\nNama Wilayah : " + nama_wilayah +
                "\nKondisi : " + kondisi +
                "\nWaktu : " + waktu +
                "\nNama File Foto : " + nama_file_foto +
                "\nLokasi Foto : " + lokasi_file_foto +
                "\nKomentar : " + komentar +
                "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_info_lokasi);
        parcel.writeString(nohp);
        parcel.writeString(longitude);
        parcel.writeString(latitude);
        parcel.writeString(nama_jalan);
        parcel.writeString(nama_wilayah);
        parcel.writeString(kondisi);
//        parcel.writeLong(waktu == null ? -1 : waktu.getTime());
        parcel.writeString(waktu);
        parcel.writeString(nama_file_foto);
        parcel.writeString(lokasi_file_foto);
        parcel.writeString(komentar);
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
