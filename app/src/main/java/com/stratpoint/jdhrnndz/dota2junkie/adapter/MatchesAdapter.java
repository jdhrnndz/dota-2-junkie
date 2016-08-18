package com.stratpoint.jdhrnndz.dota2junkie.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
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

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/20/16.
 * Description: Provides values for matches recycler view
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private Context mContext;
    private List<MatchHistory.Match> mDataset;
    private PlayerSummary.DotaPlayer mCurrentPlayer;
    private MatchHistory.MatchPlayer mCurrentMatchPlayer;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.result_indicator) View mResultIndicator;

        @BindView(R.id.player_kills) TextView mKillCount;
        @BindView(R.id.player_deaths) TextView mDeathCount;
        @BindView(R.id.player_assists) TextView mAssistCount;
        @BindView(R.id.kda_bar) KdaBar mKdaBar;

        @BindView(R.id.hero_image) NetworkImageView mHeroImage;
        @BindViews({ R.id.item_slot_0, R.id.item_slot_1, R.id.item_slot_2, R.id.item_slot_3, R.id.item_slot_4, R.id.item_slot_5})
        List<NetworkImageView> mItemSlots;

        @BindView(R.id.game_duration) TextView mGameDuration;
        @BindView(R.id.match_id) TextView mMatchId;
        @BindView(R.id.game_mode) TextView mGameMode;

        @BindArray(R.array.game_modes) String[] mGameModes;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
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

        // Converting SteamID64 to SteamID32
        int steamId32 = (int) Long.parseLong(mCurrentPlayer.getSteamId());
        for(MatchHistory.MatchPlayer player: players) {
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

        // KDA Bar
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
        ButterKnife.apply(holder.mItemSlots, ITEM, mCurrentMatchPlayer.getItems());

        // Match Duration
        holder.mGameDuration.setText(parseMatchDuration(item.getDuration()));
        // Match ID
        holder.mMatchId.setText(String.valueOf(item.getId()));
        // Match Mode
        holder.mGameMode.setText(holder.mGameModes[item.getGameMode()]);
    }

    static final ButterKnife.Setter<NetworkImageView, int[]> ITEM = new ButterKnife.Setter<NetworkImageView, int[]>() {
        @Override public void set(@NonNull NetworkImageView view, int[] items, int index) {
            int itemId = items[index];
            if (itemId > 0) {
                Context context = view.getContext();
                ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();
                String urlTemplate = UrlBuilder.buildItemImageUrlTemplate(context);
                String url = String.format(urlTemplate, MainActivity.itemRef.getItem(itemId).getName().substring(5));
                view.setImageUrl(url, imageLoader);
            }
        }
    };

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
