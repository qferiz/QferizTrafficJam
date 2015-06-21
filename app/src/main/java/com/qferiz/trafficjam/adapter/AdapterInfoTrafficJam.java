package com.qferiz.trafficjam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.qferiz.trafficjam.R;
import com.qferiz.trafficjam.activity.ActivityInfoTrafficDetail;
import com.qferiz.trafficjam.extras.TrafficJam;
import com.qferiz.trafficjam.logging.L;
import com.qferiz.trafficjam.network.VolleySingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Qferiz on 11-06-2015.
 */
public class AdapterInfoTrafficJam extends RecyclerView.Adapter<AdapterInfoTrafficJam.ViewHolderInfoTrafficJam> {
    private ArrayList<TrafficJam> listTraffic = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private ImageLoader mImageLoader;
    private VolleySingleton mVolleySingleton;
    DateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private int previousPosition = 0;

//    OnItemClickListener mClickListener;

    public AdapterInfoTrafficJam(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mVolleySingleton = VolleySingleton.getInstance();
        mImageLoader = mVolleySingleton.getImageLoader();
    }

    public void setListTraffic(ArrayList<TrafficJam> listTraffic) {
        this.listTraffic = listTraffic;
        notifyDataSetChanged();
    }

    @Override
    public AdapterInfoTrafficJam.ViewHolderInfoTrafficJam onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.info_list_item, parent, false);
        ViewHolderInfoTrafficJam viewHolderInfoTrafficJam = new ViewHolderInfoTrafficJam(view);
        return viewHolderInfoTrafficJam;
    }


    @Override
    public void onBindViewHolder(final AdapterInfoTrafficJam.ViewHolderInfoTrafficJam holder, int position) {
        TrafficJam currentTrafficJam = listTraffic.get(position);
        holder.mNamaJalan.setText(currentTrafficJam.getNama_jalan());
        holder.mWaktu.setText(currentTrafficJam.getWaktu());
//        Date infoTrafficWaktu = currentTrafficJam.getWaktu();
//        if (infoTrafficWaktu != null){
//            String formatDate = mDateFormat.format(infoTrafficWaktu);
//            holder.mWaktu.setText(formatDate);
//        } else {
//            holder.mWaktu.setText(Constants.NA);
//        }

        holder.mKondisi.setText(currentTrafficJam.getKondisi());

        final String urlInfoFoto = currentTrafficJam.getLokasi_file_foto();
        loadImages(urlInfoFoto, holder);

        final String mLongitude = currentTrafficJam.getLongitude();
        final String mLatittude = currentTrafficJam.getLatittude();
        final String mNamaWilayah = currentTrafficJam.getNama_wilayah();
        final String mLokasiFoto = currentTrafficJam.getLokasi_file_foto();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = view.getContext();
                Intent mIntent = new Intent(mContext, ActivityInfoTrafficDetail.class);
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_NAMA_JALAN, holder.mNamaJalan.getText().toString());
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_WAKTU, holder.mWaktu.getText().toString());
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_KONDISI, holder.mKondisi.getText().toString());
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_FOTO, urlInfoFoto);
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_LONGITUDE, mLongitude);
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_LATITTUDE, mLatittude);
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_NAMA_WILAYAH, mNamaWilayah);
                mIntent.putExtra(ActivityInfoTrafficDetail.EXTRA_LOKASI_FILE_FOTO, mLokasiFoto);

                mContext.startActivity(mIntent);
            }
        });


    }

    public void loadImages(String urlThumbnail, final ViewHolderInfoTrafficJam holderInfoTrafficJam) {
        if (urlThumbnail != null) {
            mImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holderInfoTrafficJam.mInfoFoto.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
//                    Snackbar.make(this, "Error ImageLoader"+ error, Snackbar.LENGTH_LONG).show();
                    L.m("Error ImageLoader " + error);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listTraffic.size();
    }

    public class ViewHolderInfoTrafficJam extends RecyclerView.ViewHolder {
        public final CardView mCardItemLayout;
        public final CircleImageView mInfoFoto;
        public final TextView mNamaJalan;
        public final TextView mWaktu;
        public final TextView mKondisi;
        public final View mView;


        public ViewHolderInfoTrafficJam(View itemView) {
            super(itemView);
            mView = itemView;

            mCardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            mInfoFoto = (CircleImageView) itemView.findViewById(R.id.foto_info_item);
            mNamaJalan = (TextView) itemView.findViewById(R.id.nama_jalan_item);
            mWaktu = (TextView) itemView.findViewById(R.id.waktu_item);
            mKondisi = (TextView) itemView.findViewById(R.id.kondisi_item);
//            itemView.setOnClickListener(this);
//            mCardItemLayout.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNamaJalan.getText();
        }

        //        @Override
//        public void onClick(View view) {
//            mClickListener.onItemClick(view, getAdapterPosition());
//        }
    }

//    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
}
