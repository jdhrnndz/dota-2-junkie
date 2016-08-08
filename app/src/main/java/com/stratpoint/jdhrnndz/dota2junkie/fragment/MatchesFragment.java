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
 * Author: John Denielle F. Hernandez
 * Date: 7/21/16
 * Description: A recycler view whose adapter is updated in the main activity whenever a match
 * detail response arrives. Applies a custom divider.
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

        // Map views from content view as the activity's attributes
        assignViews(view);
        // Assign values to views
        populateViews();

        return view;
    }

    private void assignViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_matches);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new MatchesAdapter(view.getContext(), mMatches);
        mRecyclerView.addItemDecoration(
                new SimpleDividerItemDecoration(
                        ContextCompat.getDrawable(view.getContext(), R.drawable.line_divider)
                )
        );
    }

    private void populateViews() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void updateMatches(MatchHistory.Match match) {
        // Called by the main activity every time a match detail response arrives
        mMatches.add(match);
        mAdapter.notifyDataSetChanged();
    }
}
