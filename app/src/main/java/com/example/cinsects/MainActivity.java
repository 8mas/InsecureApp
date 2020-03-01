package com.example.cinsects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cinsects.HttpRequests.VolleyBasedPinning.VolleyRequestQueue;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText hardcodedPw;
    private EditText sharedPrefPw;
    private EditText logcatPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        // Create Requests queues
        final VolleyRequestQueue pinningQueue = new VolleyRequestQueue(context, true);
        final VolleyRequestQueue noPinningQueue = new VolleyRequestQueue(context, false);


        // Shared Preference
        SharedPreferences sharedPref = context.getSharedPreferences("INSEKTEN_SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GeheimesSharedPref", UUID.randomUUID().toString());
        editor.apply();

        // Access views
        hardcodedPw = findViewById(R.id.hardcodedPWTextField);
        sharedPrefPw = findViewById(R.id.sharedPrefPWTextField);
        logcatPw = findViewById(R.id.logcatPWTextField);

        Button httpRequestButton = findViewById(R.id.httpRequestButton);
        Button httpsRequestButton = findViewById(R.id.httpsRequstButton);
        Button httpsPinningRequestButton = findViewById(R.id.httpsPinningRequestButton);


        hardcodedPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("superinsekt")) {
                    Log.d(TAG, "Hardcoded Done!");
                    Toast.makeText(getApplicationContext(), "Hardcoded Done!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final String sharedPrefPassword = sharedPref.getString("GeheimesSharedPref", "a");
        sharedPrefPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(sharedPrefPassword)) {
                    Log.d(TAG, "SharedPref Done!");
                    Toast.makeText(getApplicationContext(), "SharedPref Done!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final String logcatPassword = UUID.randomUUID().toString();
        logcatPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals(logcatPassword)) {
                    Toast.makeText(getApplicationContext(), "Logcat Done!", Toast.LENGTH_LONG).show();

                } else {
                    Log.d(TAG, "onTextChanged: Wrong password. Password should be " + logcatPassword);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        httpRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = generateHttpGetRequest("http://httpbin.org/get");
                noPinningQueue.addToRequestQueue(stringRequest);
            }
        });

        httpsRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = generateHttpGetRequest("https://httpbin.org/get");
                noPinningQueue.addToRequestQueue(stringRequest);
            }
        });

        httpsPinningRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = generateHttpGetRequest("https://httpbin.org/get");
                pinningQueue.addToRequestQueue(stringRequest);
            }
        });
    }

    private StringRequest generateHttpGetRequest(final String url) {
        return new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Response for " + url + " seems valid - did you see the request?", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Response for " + url + " seems NOT valid", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });
    }
}
