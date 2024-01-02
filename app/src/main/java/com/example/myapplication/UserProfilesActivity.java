package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserProfilesActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ListView userProfilesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profiles);

        requestQueue = Volley.newRequestQueue(this);
        userProfilesListView = findViewById(R.id.userProfilesListView);

        // Fetch and display user profiles on app launch
        fetchUserProfiles();

        // Set item click listener for the ListView
        userProfilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click, e.g., open a detailed profile activity
                onUserProfileClick(position);
            }
        });

        // Set click listener for the "Edit Profile" button
        Button editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditProfileClick();
            }
        });
    }

    private void fetchUserProfiles() {
        // Replace 'your_api_url' with the actual API endpoint providing user profiles
        String apiUrl = "https://api.api-ninjas.com/v1/cats";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<String> userProfilesList = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userObject = response.getJSONObject(i);
                                String username = userObject.getString("username");
                                userProfilesList.add(username);
                            } catch (JSONException e) {
                                Log.e("JSONError", "Error parsing JSON response: " + e.getMessage());
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(UserProfilesActivity.this,
                                android.R.layout.simple_list_item_1, userProfilesList);
                        userProfilesListView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error fetching user profiles: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(jsonArrayRequest);
    }

    // Handle item click (e.g., open a detailed profile activity)
    private void onUserProfileClick(int position) {
        // Get the selected username from the ArrayList
        String selectedUsername = ((ArrayAdapter<String>) userProfilesListView.getAdapter()).getItem(position);

        Intent intent = new Intent(this, UserProfileDetailsActivity.class);
        intent.putExtra("username", selectedUsername);
        startActivity(intent);
    }

    // Handle "Edit Profile" button click
    private void onEditProfileClick() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }


}
