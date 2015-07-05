package com.qferiz.trafficjam.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qferiz.trafficjam.R;
import com.qferiz.trafficjam.adapter.AdapterInfoTrafficJam;
import com.qferiz.trafficjam.callback.InfoTrafficJamLoadedListener;
import com.qferiz.trafficjam.extras.MyApplication;
import com.qferiz.trafficjam.extras.TrafficJam;
import com.qferiz.trafficjam.logging.L;
import com.qferiz.trafficjam.task.TaskLoadInfoTrafficJam;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInfoTrafficList extends Fragment implements InfoTrafficJamLoadedListener, SwipeRefreshLayout.OnRefreshListener {
    //the adapter responsible for displaying our movies within a RecyclerView
    private AdapterInfoTrafficJam mAdapter;

    //The key used to store arraylist of info Traffic Jam objects to and from parcelable
    private static final String STATE_INFO_TRAFFIC_JAM = "state_info_traffic_jam";

    //the arraylist containing our list of info Traffic jam his
    private ArrayList<TrafficJam> mListTraffic = new ArrayList<>();

    // swipeRefresh animate
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerTraffic;

    private View mView;


    public FragmentInfoTrafficList() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_info_traffic_list, container, false);
        mView = inflater.inflate(R.layout.fragment_info_traffic_list, container, false);

        setupFAB();

        // SwipeRefreshLayout Animation Gesture
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipeInfoTraffic);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.MAGENTA, Color.GREEN);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorAnimateSwipe1, R.color.colorAnimateSwipe2);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerTraffic = (RecyclerView) mView.findViewById(R.id.listRecyclerInfo);
        mRecyclerTraffic.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new AdapterInfoTrafficJam(getActivity());
        mRecyclerTraffic.setAdapter(mAdapter);
//        setupRecyclerView(mRecyclerTraffic);

        if (savedInstanceState != null) {
            //if this fragment starts after a rotation or configuration change, load the existing traffic from a parcelable
            mListTraffic = savedInstanceState.getParcelableArrayList(STATE_INFO_TRAFFIC_JAM);
        } else {
            //if this fragment starts for the first time, load the list of movies from a database
            // database
            mListTraffic = MyApplication.getWritableDatabase().readTrafficJam();

            //if the database is empty, trigger an AsycnTask to download traffic list from the web
            if (mListTraffic.isEmpty()) {
                new TaskLoadInfoTrafficJam(this).execute();
            }
        }

        //update your Adapter to containg the retrieved movies
        mAdapter.setListTraffic(mListTraffic);
        return mView;
    }

/*    private void setupRecyclerView(RecyclerView recyclerView){
        recyclerView = (RecyclerView) mView.findViewById(R.id.listRecyclerInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        mAdapter = new AdapterInfoTrafficJam(getActivity());
        recyclerView.setAdapter(mAdapter);
        // Update adapter to contain the retrieved traffic jam
        mAdapter.setListTraffic(mListTraffic);
    }*/

    private void setupFAB() {
        FloatingActionButton mFAB = (FloatingActionButton) mView.findViewById(R.id.fabTrafficList);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.t(getActivity(), "Pilih Direction");
//                Snackbar.make(mView, "Direction", Snackbar.LENGTH_LONG)
//                .setAction("GO", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        L.t(getActivity(), "Direction Maps");
//                    }
//                }).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save the info traffic list to a parcelable prior to rotation or configuration change
        outState.putParcelableArrayList(STATE_INFO_TRAFFIC_JAM, mListTraffic);
    }

    @Override
    public void onInfoTrafficJamLoaded(ArrayList<TrafficJam> mListTraffic) {
        //update the Adapter to contain the new traffic downloaded from the web
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdapter.setListTraffic(mListTraffic);
    }


    @Override
    public void onRefresh() {
        L.t(getActivity(), "onRefresh");
        //load the whole feed again on refresh, dont try this at home :)
        new TaskLoadInfoTrafficJam(this).execute();
    }
}
