package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private Button loginButton;

    // SharedPreferences keys
    private static final String PREF_NAME = "login_prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_REMEMBER_ME = "remember_me";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        loginButton = findViewById(R.id.loginButton);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Check if "remember me" option was selected in the previous session
        boolean rememberMe = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false);
        if (rememberMe) {
            // If selected, populate the username field
            String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
            usernameEditText.setText(savedUsername);
            rememberMeCheckBox.setChecked(true);
        }
    }

    public void onLoginClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean rememberMe = rememberMeCheckBox.isChecked();

        // Perform login logic (e.g., validate credentials)

        // Save login status locally based on "remember me" option using SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);

        // If "remember me" is checked, save the status
        editor.putBoolean(KEY_REMEMBER_ME, rememberMe);

        // Apply the changes
        editor.apply();

        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

        // Redirect to MainActivity
        startActivity(new Intent(this, MainActivity.class));
    }
}
