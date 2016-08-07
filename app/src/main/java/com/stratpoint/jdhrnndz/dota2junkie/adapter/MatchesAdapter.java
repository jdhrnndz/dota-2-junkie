package com.stratpoint.jdhrnndz.dota2junkie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stratpoint.jdhrnndz.dota2junkie.MatchHistory;
import com.stratpoint.jdhrnndz.dota2junkie.R;

/**
 * Created by johndeniellehernandez on 7/20/16.
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private MatchHistory.Match[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    public MatchesAdapter(MatchHistory.Match[] matches) {
        mDataset = matches;
    }

    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_match_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MatchHistory.Match item = mDataset[position];
        ((TextView) holder.mView.findViewById(R.id.match_id)).setText(String.valueOf(item.getId()));
        ((TextView) holder.mView.findViewById(R.id.game_mode)).setText(item.getGameMode());
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.length;
    }
}
