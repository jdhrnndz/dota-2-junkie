package com.stratpoint.jdhrnndz.dota2junkie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.android.volley.VolleyError;
import com.stratpoint.jdhrnndz.dota2junkie.network.ApiManager;
import com.stratpoint.jdhrnndz.dota2junkie.network.DotaApiResponseListener;
import com.stratpoint.jdhrnndz.dota2junkie.network.UrlBuilder;

import java.util.HashMap;

/**
 * Created by johndeniellehernandez on 7/27/16.
 */
public class LogInActivity extends AppCompatActivity implements DotaApiResponseListener{
    public final static String EXTRA_USER_INFO = "com.stratpoint.jdhrnndz.EXTRA_USER_INFO";

    private ProgressDialog mLogInDialog;
    private Snackbar mErrorMessage;
    private Intent mLogInIntent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mLogInDialog = new ProgressDialog(this);
        mLogInDialog.setTitle(R.string.log_in_dialog_title);
        mLogInDialog.setMessage(getString(R.string.log_in_dialog_message));
        mLogInDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLogInDialog.setCancelable(false);

        AppCompatButton mLogInButton = (AppCompatButton) findViewById(R.id.button_log_in);

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fontAwesome.ttf");
        mLogInButton.setTypeface(fontAwesome);

        mErrorMessage = Snackbar.make(findViewById(R.id.sign_in_layout), R.string.log_in_error_message, Snackbar.LENGTH_LONG);
        mLogInIntent = new Intent(this, MainActivity.class);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogInDialog.show();

                // Build the url's query section
                HashMap<String, String> args = new HashMap<>();

                String steamId = ((TextInputEditText) findViewById(R.id.user_sign_up_steam_id)).getText().toString();
                args.put("steamids", steamId);

                // Build the url to retrieve user info
                String url = UrlBuilder.buildUrl(LogInActivity.this, R.string.get_player_summaries, args);

                ApiManager.fetchUserInfo(getApplicationContext(), url, LogInActivity.this);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLogInDialog.dismiss();
    }

    public void onReceiveStringResponse(int statusCode, String responseString) {
        mLogInDialog.setMessage(getResources().getString(R.string.log_in_response_message));
        // Pass response to the main activity
        mLogInIntent.putExtra(EXTRA_USER_INFO, responseString);
        startActivity(mLogInIntent);
    }

    public void onReceiveMatchHistoryResponse(int statusCode, MatchHistory response) {}

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        mLogInDialog.dismiss();
        mErrorMessage.show();
    }
}
