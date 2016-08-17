package com.stratpoint.jdhrnndz.dota2junkie.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.network.ApiManager;
import com.stratpoint.jdhrnndz.dota2junkie.network.DotaApiResponseListener;
import com.stratpoint.jdhrnndz.dota2junkie.network.UrlBuilder;
import com.stratpoint.jdhrnndz.dota2junkie.util.StringAssetReader;

import java.util.HashMap;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: John Denielle F. Hernandez
 * Date: 7/21/16
 * Description: This class uses the activity_sign_in layout to create the view for the sign activity
 * Inside the class is where the data are linked to the respective views.
 */
public class LogInActivity extends AppCompatActivity implements DotaApiResponseListener{
    public final static String EXTRA_USER_INFO = "com.stratpoint.jdhrnndz.EXTRA_USER_INFO";

    private ProgressDialog mLogInDialog;
    private Snackbar mErrorMessage;
    private Intent mLogInIntent;
    @BindView(R.id.sign_in_layout) RelativeLayout mRootView;
    @BindView(R.id.button_log_in) AppCompatButton mLogInButton;
    @BindView(R.id.user_sign_up_steam_id) TextInputEditText mLogInInput;
    @BindString(R.string.sharedpref_herojson_key) String heroJsonKey;
    @BindString(R.string.sharedpref_itemjson_key) String itemJsonKey;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        // Initialize objects that are not needed to be in the xml layout
        initObjects();
        // Assign values to views
        populateViews();

        // Store some values in the shared preferences
        SharedPreferences defaultSP =  PreferenceManager.getDefaultSharedPreferences(this);

        String heroJson = defaultSP.getString(heroJsonKey, "");
        String itemJson = defaultSP.getString(itemJsonKey, "");

        try {
            String heroesJsonString;
            String itemsJsonString;

            if ("".equals(heroJson)) {
                heroesJsonString = StringAssetReader.getStringFromAsset(this, "heroes.json");
                defaultSP.edit().putString(heroJsonKey, heroesJsonString).apply();
            }
            if ("".equals(itemJson)) {
                itemsJsonString = StringAssetReader.getStringFromAsset(this, "items.json");
                defaultSP.edit().putString(itemJsonKey, itemsJsonString).apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLogInDialog.dismiss();
    }

    public void onReceiveResponse(int statusCode, Object responseString, int type) {
        // Change dialog message when player info has been received
        mLogInDialog.setMessage(getResources().getString(R.string.log_in_response_message));
        // Pass response to the main activity
        mLogInIntent.putExtra(EXTRA_USER_INFO, (String) responseString);
        startActivity(mLogInIntent);
    }

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        mLogInDialog.dismiss();
        if(!mErrorMessage.isShown()) {
            mErrorMessage.show();
        }
    }

    private void initObjects() {
        // Shown while fetching user info
        mLogInDialog = new ProgressDialog(this);
        // Stores hero and item references
        mLogInIntent = new Intent(this, MainActivity.class);
        // Shown when fetching player info fails
        mErrorMessage = Snackbar.make(mRootView, R.string.log_in_error_message, Snackbar.LENGTH_LONG);
    }

    private void populateViews() {
        mLogInDialog.setTitle(R.string.log_in_dialog_title);
        mLogInDialog.setMessage(getString(R.string.log_in_dialog_message));
        mLogInDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLogInDialog.setCancelable(false);

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fontAwesome.ttf");
        mLogInButton.setTypeface(fontAwesome);
    }

    @OnClick(R.id.button_log_in)
    public void logIn() {
        mLogInDialog.show();

        // Build the url's query section
        HashMap<String, String> args = new HashMap<>();

        String steamId = mLogInInput.getText().toString();
        args.put("steamids", steamId);

        // Build the url to retrieve user info
        String url = UrlBuilder.buildGenericUrl(LogInActivity.this, R.string.get_player_summaries, args);

        ApiManager.fetchUserInfo(getApplicationContext(), url, LogInActivity.this);
    }
}
