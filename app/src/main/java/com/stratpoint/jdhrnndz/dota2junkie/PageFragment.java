package com.stratpoint.jdhrnndz.dota2junkie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by johndeniellehernandez on 7/20/16.
 */
public class PageFragment extends Fragment {
    public static final String ARG_LAYOUT = "ARG_LAYOUT";

    private int mLayout;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static PageFragment newInstance(int layout) {
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT, layout);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayout = getArguments().getInt(ARG_LAYOUT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);

        String[] matchesDataset = new String[] {"1928367", "4958603", "3847596", "1928733", "3847594", "3847593", "3485743", "3423456"};

        if(view.findViewById(R.id.recycler_view_matches) != null) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_matches);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(container.getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MatchesAdapter(matchesDataset);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(container.getContext()));
        }
        return view;
    }
}
