package com.stratpoint.jdhrnndz.dota2junkie.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.adapter.MatchesAdapter;
import com.stratpoint.jdhrnndz.dota2junkie.adapter.SimpleDividerItemDecoration;
import com.stratpoint.jdhrnndz.dota2junkie.model.DotaPlayer;
import com.stratpoint.jdhrnndz.dota2junkie.model.Match;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/21/16
 * Description: A recycler view whose adapter is updated in the main activity whenever a match
 * detail response arrives. Applies a custom divider.
 */
public class MatchesFragment extends BaseFragment {
    private final static int LAYOUT = R.layout.fragment_matches;

    @BindView(R.id.recycler_view_matches) RecyclerView mRecyclerView;
    @BindView(R.id.matches_progress_bar) ProgressBar mProgressBar;

    @BindDrawable(R.drawable.line_divider) Drawable mLineDivider;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Match> mMatches = new ArrayList<>();
    private List<Match> mMatchBuffer = new ArrayList<>();
    private boolean isConsumingBuffer = false;

    private DotaPlayer mCurrentPlayer;

    public static MatchesFragment newInstance() {
        Bundle args = BaseFragment.initBundle(LAYOUT);
        MatchesFragment fragment = new MatchesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(mLayout, container, false);
        ButterKnife.bind(this, view);

        // Assign values to views
        populateViews(view);

        return view;
    }

    private void populateViews(View view) {
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mAdapter = new MatchesAdapter(view.getContext(), mMatches, mCurrentPlayer);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mLineDivider));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<Match> getMatches() {
        return this.mMatches;
    }

    public void updateMatches(Match match) {
        if(mProgressBar.getVisibility() == View.VISIBLE && mAdapter.getItemCount() > 0)
            mProgressBar.setVisibility(View.GONE);

        // Called by the main activity every time a match detail response arrives
        mMatchBuffer.add(mMatchBuffer.size(), match);

        if (!isConsumingBuffer) consumeBuffer();
    }

    private void consumeBuffer() {
        isConsumingBuffer = true;

        // Consume the buffer as a queue
        do {
            Match currentMatch = mMatchBuffer.get(0);
            int insertIndex = findInsertIndexInList(currentMatch, mMatches);
            mMatches.add(insertIndex, currentMatch);
            mAdapter.notifyItemInserted(insertIndex);
            mMatchBuffer.remove(0);
        } while (mMatchBuffer.size() > 0);

        isConsumingBuffer = false;
    }

    private int findInsertIndexInList(Match match, List<Match> matches) {
        int size = matches.size();

        for (int i = 0; i < size; i++) {
            if (matches.get(i).getSequenceNumber() < match.getSequenceNumber()) {
                return i;
            }
        }

        return size;
    }

    public void setCurrentPlayer(DotaPlayer player) {
        mCurrentPlayer = player;
    }
}
