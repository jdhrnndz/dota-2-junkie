package com.stratpoint.jdhrnndz.dota2junkie;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by johndeniellehernandez on 7/27/16.
 */
public class LogInActivity extends AppCompatActivity{
    private AppCompatButton mLogInButton;
    private ProgressDialog mLogInDialog;
    private TextInputLayout mLogInInputLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mLogInInputLayout = (TextInputLayout) findViewById(R.id.user_sign_up_steam_id);

        mLogInDialog = new ProgressDialog(this);
        mLogInDialog.setTitle(R.string.log_in_dialog_title);
        mLogInDialog.setMessage(getString(R.string.log_in_dialog_message));
        mLogInDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mLogInDialog.setCancelable(false);

        final TextView mTextView = (TextView) findViewById(R.id.text);

        mLogInButton = (AppCompatButton) findViewById(R.id.button_log_in);

        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fontAwesome.ttf");
        mLogInButton.setTypeface(fontAwesome);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLogInDialog.show();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2?key=22247A2A498D945AA1F4A20FF96F1911&steamids=76561198060797038";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            mTextView.setText("Response is: "+ response.substring(0,500));
                            mLogInDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mTextView.setText("That didn't work!");
                            mLogInDialog.dismiss();
                        }
                    }
                );
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }
}
