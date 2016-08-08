package com.stratpoint.jdhrnndz.dota2junkie.activity;

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
import com.stratpoint.jdhrnndz.dota2junkie.R;
import com.stratpoint.jdhrnndz.dota2junkie.network.ApiManager;
import com.stratpoint.jdhrnndz.dota2junkie.network.DotaApiResponseListener;
import com.stratpoint.jdhrnndz.dota2junkie.network.UrlBuilder;

import java.util.HashMap;

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
    private AppCompatButton mLogInButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Map views from content view as the activity's attributes
        assignViews();
        // Assign values to views
        populateViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLogInDialog.dismiss();
    }

    public void onReceiveResponse(int statusCode, Object responseString, int type) {
        mLogInDialog.setMessage(getResources().getString(R.string.log_in_response_message));
        // Pass response to the main activity
        mLogInIntent.putExtra(EXTRA_USER_INFO, (String) responseString);
        startActivity(mLogInIntent);
    }

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        mLogInDialog.dismiss();
        mErrorMessage.show();
    }

    private void assignViews() {
        mLogInDialog = new ProgressDialog(this);
        mLogInButton = (AppCompatButton) findViewById(R.id.button_log_in);

        mErrorMessage =
                Snackbar.make(findViewById(R.id.sign_in_layout),
                        R.string.log_in_error_message,
                        Snackbar.LENGTH_LONG);
        mLogInIntent = new Intent(this, MainActivity.class);
    }

    private void populateViews() {
        mLogInDialog.setTitle(R.string.log_in_dialog_title);
        mLogInDialog.setMessage(getString(R.string.log_in_dialog_message));
        mLogInDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLogInDialog.setCancelable(false);

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fontAwesome.ttf");
        mLogInButton.setTypeface(fontAwesome);

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
}
