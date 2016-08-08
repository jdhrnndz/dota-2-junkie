package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.adapter.MatchesAdapter;
import com.stratpoint.jdhrnndz.dota2junkie.adapter.SimpleDividerItemDecoration;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchHistory;

import java.util.ArrayList;

/**
 * Created by johndeniellehernandez on 7/21/16.
 */
public class MatchesFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_matches;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<MatchHistory.Match> mMatches = new ArrayList<>();

    public static MatchesFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_matches);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MatchesAdapter(container.getContext(), mMatches);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(ContextCompat.getDrawable(container.getContext(), R.drawable.line_divider)));

        return view;
    }

    public void updateMatches(MatchHistory.Match match) {
        mMatches.add(match);
        mAdapter.notifyDataSetChanged();
    }
}
