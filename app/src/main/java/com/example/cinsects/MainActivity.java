package com.example.cinsects;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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
        SharedPreferences sharedPref = context.getSharedPreferences("INSEKTEN_SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("GeheimesSharedPref", UUID.randomUUID().toString());
        editor.apply();

        hardcodedPw = findViewById(R.id.hardcodedPWTextField);
        sharedPrefPw = findViewById(R.id.sharedPrefPWTextField);
        logcatPw = findViewById(R.id.logcatPWTextField);

        hardcodedPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("superinsekt")) {
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

    }
}
