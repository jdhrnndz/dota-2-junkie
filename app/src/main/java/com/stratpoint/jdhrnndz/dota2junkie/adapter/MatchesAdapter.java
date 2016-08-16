package com.stratpoint.jdhrnndz.dota2junkie.adapter;

import android.content.Context;
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
import com.stratpoint.jdhrnndz.dota2junkie.network.UrlBuilder;
import com.stratpoint.jdhrnndz.dota2junkie.network.VolleySingleton;

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
        TextView mKillCount, mDeathCount, mAssistCount, mGameDuration, mMatchId, mGameMode;
        KdaBar mKdaBar;
        View mResultIndicator;
        NetworkImageView mHeroImage, mItemSlot0, mItemSlot1, mItemSlot2, mItemSlot3, mItemSlot4, mItemSlot5;

        public ViewHolder(View v) {
            super(v);
            mResultIndicator = v.findViewById(R.id.result_indicator);
            mKillCount = (TextView) v.findViewById(R.id.player_kills);
            mDeathCount = (TextView) v.findViewById(R.id.player_deaths);
            mAssistCount = (TextView) v.findViewById(R.id.player_assists);
            mKdaBar = (KdaBar) v.findViewById(R.id.kda_bar);

            mHeroImage = (NetworkImageView) v.findViewById(R.id.hero_image);
            mItemSlot0 = (NetworkImageView) v.findViewById(R.id.item_slot_0);
            mItemSlot1 = (NetworkImageView) v.findViewById(R.id.item_slot_1);
            mItemSlot2 = (NetworkImageView) v.findViewById(R.id.item_slot_2);
            mItemSlot3 = (NetworkImageView) v.findViewById(R.id.item_slot_3);
            mItemSlot4 = (NetworkImageView) v.findViewById(R.id.item_slot_4);
            mItemSlot5 = (NetworkImageView) v.findViewById(R.id.item_slot_5);

            mGameDuration = (TextView) v.findViewById(R.id.game_duration);
            mMatchId = (TextView) v.findViewById(R.id.match_id);
            mGameMode = (TextView) v.findViewById(R.id.game_mode);
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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageLoader imageLoader = VolleySingleton.getInstance(mContext).getImageLoader();

        MatchHistory.Match item = mDataset.get(position);
        MatchHistory.MatchPlayer[] players = item.getPlayers();

        for(MatchHistory.MatchPlayer player: players) {
            int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
            if (steamId32 == player.getAccountId()) {
                mCurrentMatchPlayer = player;
            }
        }

        boolean didPlayerWin = false;
        // Identifies if the player won or not
        if (mCurrentMatchPlayer != null) {
            int playerSlot = mCurrentMatchPlayer.getPlayerSlot();
            didPlayerWin = (item.didRadiantWin())?
                    playerSlot >> 7 == 0:
                    playerSlot >> 7 != 0;
        }

        // Match Item Result Indicator
        int indicatorId = didPlayerWin ? R.drawable.victory_indicator : R.drawable.defeat_indicator;
        holder.mResultIndicator.setBackgroundResource(indicatorId);

        holder.mKdaBar.setValues(mCurrentMatchPlayer.getKills(), mCurrentMatchPlayer.getDeaths(), mCurrentMatchPlayer.getAssists());

        // Kills
        holder.mKillCount.setText(String.valueOf(mCurrentMatchPlayer.getKills()));

        // Deaths
        holder.mDeathCount.setText(String.valueOf(mCurrentMatchPlayer.getDeaths()));

        // Assists
        holder.mAssistCount.setText(String.valueOf(mCurrentMatchPlayer.getAssists()));

        // Hero Image
        String heroImageUrl = UrlBuilder.buildHeroImageUrl(mContext, MainActivity.heroRef.getHero(mCurrentMatchPlayer.getHeroId()).getName().substring(14));
        holder.mHeroImage.setImageUrl(heroImageUrl, imageLoader);

        // Item Images
        String itemUrlTemplate = UrlBuilder.buildItemImageUrlTemplate(mContext);

        int[] items = {
                mCurrentMatchPlayer.getItem0(),
                mCurrentMatchPlayer.getItem1(),
                mCurrentMatchPlayer.getItem2(),
                mCurrentMatchPlayer.getItem3(),
                mCurrentMatchPlayer.getItem4(),
                mCurrentMatchPlayer.getItem5()
        };

        NetworkImageView[] slots = {
                holder.mItemSlot0,
                holder.mItemSlot1,
                holder.mItemSlot2,
                holder.mItemSlot3,
                holder.mItemSlot4,
                holder.mItemSlot5
        };
        assignItemsToSlots(items, slots, itemUrlTemplate, imageLoader);

        // Match Duration
        holder.mGameDuration.setText(parseMatchDuration(item.getDuration()));
        // Match ID
        holder.mMatchId.setText(String.valueOf(item.getId()));
        // Match Mode
        holder.mGameMode.setText(mContext.getResources().getStringArray(R.array.game_modes)[item.getGameMode()]);
    }

    private void assignItemsToSlots(int[] items, NetworkImageView[] slots, String itemUrl, ImageLoader imageLoader) {
        int len = items.length;
        for (int i=0; i<len; i++) {
            if (items[i] > 0)
                slots[i].setImageUrl(String.format(itemUrl, MainActivity.itemRef.getItem(items[i]).getName().substring(5)), imageLoader);
        }
    }

    private String parseMatchDuration(long duration) {
        StringBuilder matchDuration = new StringBuilder();

        long hours = TimeUnit.SECONDS.toHours(duration);
        if (hours > 0) {
            matchDuration.append(String.format(Locale.ENGLISH, "%02d", hours));
            matchDuration.append(':');
            duration -= TimeUnit.HOURS.toSeconds(hours);
        }

        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        matchDuration.append(String.format(Locale.ENGLISH, "%02d", minutes));
        matchDuration.append(':');
        duration -= TimeUnit.MINUTES.toSeconds(minutes);

        matchDuration.append(String.format(Locale.ENGLISH, "%02d", duration));

        return matchDuration.toString();
    }

    @Override
    public int getItemCount() {
        if (mDataset == null) return 0;
        return mDataset.size();
    }
}
