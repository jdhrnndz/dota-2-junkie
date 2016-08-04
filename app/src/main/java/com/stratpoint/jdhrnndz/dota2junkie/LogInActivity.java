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

/**
 * Created by johndeniellehernandez on 7/27/16.
 */
public class LogInActivity extends AppCompatActivity implements DotaApiResponseListener{
    public final static String EXTRA_USER_INFO = "com.stratpoint.jdhrnndz.EXTRA_USER_INFO";

    private AppCompatButton mLogInButton;
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

        mLogInButton = (AppCompatButton) findViewById(R.id.button_log_in);

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fontAwesome.ttf");
        mLogInButton.setTypeface(fontAwesome);

        mErrorMessage = Snackbar.make(findViewById(R.id.sign_in_layout), R.string.log_in_error_message, Snackbar.LENGTH_LONG);
        mLogInIntent = new Intent(this, MainActivity.class);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogInDialog.show();

                // Builds the url to retrieve user info
                // TODO: Create a new class for building url
                StringBuilder url = new StringBuilder();
                url.append(getResources().getString(R.string.get_player_summaries));
                url.append("?key=");
                url.append(getResources().getString(R.string.api_key));
                url.append("&steamids=");
                url.append(((TextInputEditText) findViewById(R.id.user_sign_up_steam_id)).getText());

                ApiManager.fetchUserInfo(url.toString(), LogInActivity.this, getApplicationContext());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLogInDialog.dismiss();
    }

    public void onReceiveResponse(int statusCode, String responseString, String action, String cookie) {
        mLogInDialog.setMessage(getResources().getString(R.string.log_in_response_message));
        // Pass response to the main activity
        mLogInIntent.putExtra(EXTRA_USER_INFO, responseString);
        startActivity(mLogInIntent);
    }

    public void onReceiveErrorResponse(int statusCode, VolleyError error) {
        mLogInDialog.dismiss();
        mErrorMessage.show();
    }
}
