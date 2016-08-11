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
import com.stratpoint.jdhrnndz.dota2junkie.model.PlayerSummary;

import java.util.ArrayList;
import java.util.List;

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

    private List<MatchHistory.Match> mMatches = new ArrayList<>();
    private List<MatchHistory.Match> mMatchBuffer = new ArrayList<>();
    private boolean isConsumingBuffer = false;
    private PlayerSummary.DotaPlayer mCurrentPlayer;

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
        mAdapter = new MatchesAdapter(view.getContext(), mMatches, mCurrentPlayer);
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
        mMatchBuffer.add(mMatchBuffer.size(), match);

        if (!isConsumingBuffer)
            consumeBuffer();
    }

    private void consumeBuffer() {
        int bufferSize;

        isConsumingBuffer = true;

        do {
            MatchHistory.Match currentMatch = mMatchBuffer.get(0);
            int insertIndex = findInsertIndexInList(currentMatch, mMatches);
            mMatches.add(insertIndex, currentMatch);
            mMatchBuffer.remove(0);
        } while ((bufferSize = mMatchBuffer.size()) > 0);

        isConsumingBuffer = false;
        mAdapter.notifyDataSetChanged();
    }

    private int findInsertIndexInList(MatchHistory.Match match, List<MatchHistory.Match> matches) {
        int size = matches.size();

        for (int i = 0; i < size; i++) {
            if (matches.get(i).getSequenceNumber() < match.getSequenceNumber()) {
                return i;
            }
        }

        return size;
    }

    public void setCurrentPlayer(PlayerSummary.DotaPlayer player) {
        mCurrentPlayer = player;
    }
}
