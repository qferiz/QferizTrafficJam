package com.qferiz.trafficjam.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qferiz.trafficjam.R;
import com.qferiz.trafficjam.callback.OnItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Qferiz on 11-06-2015.
 */
public class AdapterInfoTrafficJam extends RecyclerView.Adapter<AdapterInfoTrafficJam.ViewHolderInfoTrafficJam> {
    OnItemClickListener clickListener;

    @Override
    public AdapterInfoTrafficJam.ViewHolderInfoTrafficJam onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AdapterInfoTrafficJam.ViewHolderInfoTrafficJam holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderInfoTrafficJam extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mCardItemLayout;
        CircleImageView mInfoFoto;
        TextView mNamaJalan;
        TextView mWaktu;
        TextView mKondisi;

        public ViewHolderInfoTrafficJam(View itemView) {
            super(itemView);

            mCardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);
            mInfoFoto = (CircleImageView) itemView.findViewById(R.id.foto_info_item);
            mNamaJalan = (TextView) itemView.findViewById(R.id.nama_jalan_item);
            mWaktu = (TextView) itemView.findViewById(R.id.waktu_item);
            mKondisi = (TextView) itemView.findViewById(R.id.kondisi_item);
//            mCardItemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }
}
