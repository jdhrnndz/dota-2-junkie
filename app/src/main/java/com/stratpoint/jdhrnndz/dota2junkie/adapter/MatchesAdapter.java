package com.stratpoint.jdhrnndz.dota2junkie.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.stratpoint.jdhrnndz.dota2junkie.activity.MainActivity;
import com.stratpoint.jdhrnndz.dota2junkie.customview.KdaBar;
import com.stratpoint.jdhrnndz.dota2junkie.model.MatchHistory;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.model.PlayerSummary;
import com.stratpoint.jdhrnndz.dota2junkie.network.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/20/16.
 * Description: Provides values for matches recycler view
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private List<MatchHistory.Match> mDataset;
    private Context mContext;
    private PlayerSummary.DotaPlayer mCurrentPlayer;
    private MatchHistory.MatchPlayer mCurrentMatchPlayer;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    public MatchesAdapter(Context context, List<MatchHistory.Match> matches, PlayerSummary.DotaPlayer player) {
        mDataset = matches;
        mContext = context;
        mCurrentPlayer = player;
    }

    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_match_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader imageLoader = VolleySingleton.getInstance(mContext).getImageLoader();

        MatchHistory.Match item = mDataset.get(position);
        MatchHistory.MatchPlayer[] players = item.getPlayers();

        for(MatchHistory.MatchPlayer player: players) {
            int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
            if(Long.valueOf(steamId32) == player.getAccountId())
                mCurrentMatchPlayer = player;
        }

        int indicatorId;
        // Match Item Result Indicator
        if (item.didRadiantWin()) {
            if (mCurrentMatchPlayer.getPlayerSlot() >> 7 == 0) {
                indicatorId = R.drawable.victory_indicator;
            }
            else {
                indicatorId = R.drawable.defeat_indicator;
            }
        }
        else {
            if (mCurrentMatchPlayer.getPlayerSlot() >> 7 == 0) {
                indicatorId = R.drawable.defeat_indicator;
            }
            else {
                indicatorId = R.drawable.victory_indicator;
            }
        }

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.mView.findViewById(R.id.result_indicator).setBackgroundDrawable(ContextCompat.getDrawable(mContext, indicatorId));
        } else {
            holder.mView.findViewById(R.id.result_indicator).setBackground(ContextCompat.getDrawable(mContext, indicatorId));
        }

        ((KdaBar) holder.mView.findViewById(R.id.kda_bar)).setValues(mCurrentMatchPlayer.getKills(), mCurrentMatchPlayer.getDeaths(), mCurrentMatchPlayer.getAssists());

        // Kills
        ((TextView) holder.mView.findViewById(R.id.player_kills)).setText(String.valueOf(mCurrentMatchPlayer.getKills()));

        // Deaths
        ((TextView) holder.mView.findViewById(R.id.player_deaths)).setText(String.valueOf(mCurrentMatchPlayer.getDeaths()));

        // Assists
        ((TextView) holder.mView.findViewById(R.id.player_assists)).setText(String.valueOf(mCurrentMatchPlayer.getAssists()));

        HashMap<String, String> args = new HashMap<>();

        // Hero Image
        StringBuilder heroUrl = new StringBuilder();
        heroUrl.append(mContext.getString(R.string.get_hero_images));
        heroUrl.append(MainActivity.heroRef.getHero(mCurrentMatchPlayer.getHeroId()).getName().substring(14));
        heroUrl.append(mContext.getString(R.string.hero_image_suffix));
        ((NetworkImageView) holder.mView.findViewById(R.id.hero_image)).setImageUrl(heroUrl.toString(), imageLoader);

        // Item Images
        StringBuilder itemUrlBuilder = new StringBuilder();
        itemUrlBuilder.append(mContext.getString(R.string.get_item_images));
        itemUrlBuilder.append("%s");
        itemUrlBuilder.append(mContext.getString(R.string.item_image_suffix));

        String itemUrl = itemUrlBuilder.toString();

        if (mCurrentMatchPlayer.getItem0() > 0) ((NetworkImageView) holder.mView.findViewById(R.id.item_slot_0))
                .setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(mCurrentMatchPlayer.getItem0()).getName().substring(5)), imageLoader);
        if (mCurrentMatchPlayer.getItem1() > 0) ((NetworkImageView) holder.mView.findViewById(R.id.item_slot_1))
                .setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(mCurrentMatchPlayer.getItem1()).getName().substring(5)), imageLoader);
        if (mCurrentMatchPlayer.getItem2() > 0) ((NetworkImageView) holder.mView.findViewById(R.id.item_slot_2))
                .setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(mCurrentMatchPlayer.getItem2()).getName().substring(5)), imageLoader);
        if (mCurrentMatchPlayer.getItem3() > 0) ((NetworkImageView) holder.mView.findViewById(R.id.item_slot_3))
                .setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(mCurrentMatchPlayer.getItem3()).getName().substring(5)), imageLoader);
        if (mCurrentMatchPlayer.getItem4() > 0) ((NetworkImageView) holder.mView.findViewById(R.id.item_slot_4))
                .setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(mCurrentMatchPlayer.getItem4()).getName().substring(5)), imageLoader);
        if (mCurrentMatchPlayer.getItem5() > 0) ((NetworkImageView) holder.mView.findViewById(R.id.item_slot_5))
                .setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(mCurrentMatchPlayer.getItem5()).getName().substring(5)), imageLoader);

        // Match Duration
        StringBuilder matchDuration = new StringBuilder();
        long duration = item.getDuration();

        long hours = TimeUnit.SECONDS.toHours(duration);
        if (hours > 0) {
            matchDuration.append(String.format(Locale.ENGLISH, "%02d", hours));
            matchDuration.append(':');
            duration -= TimeUnit.HOURS.toSeconds(hours);
        }

        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        if (minutes > 0) {
            matchDuration.append(String.format(Locale.ENGLISH, "%02d", minutes));
            matchDuration.append(':');
            duration -= TimeUnit.MINUTES.toSeconds(minutes);
        }

        matchDuration.append(String.format(Locale.ENGLISH, "%02d", duration));

        ((TextView) holder.mView.findViewById(R.id.game_duration)).setText(matchDuration.toString());
        // Match ID
        ((TextView) holder.mView.findViewById(R.id.match_id)).setText(String.valueOf(item.getId()));
        // Match Mode
        ((TextView) holder.mView.findViewById(R.id.game_mode)).setText(mContext.getResources().getStringArray(R.array.game_modes)[item.getGameMode()]);
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }
}
