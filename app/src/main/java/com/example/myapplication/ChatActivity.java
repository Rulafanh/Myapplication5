package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private final String url = "https://api.api-ninjas.com/v1/cats?name";
    private final String apiKey = "live_kjFwvM7C1iqLbT01M2MRArgL9TUtdjIxIKsyRrn4gMrSmHSMTdidFF2KG1UwLa1R";
    private EditText edtName;
    private RequestQueue queue;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edtName = findViewById(R.id.edtName);
        txtResult = findViewById(R.id.txtResult);
        queue = Volley.newRequestQueue(this);
    }

    public void btnShow_Click(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API_RESPONSE", "Response: " + response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject dataObj = jsonArray.getJSONObject(0);

                            String imageUrl = dataObj.getString("url");

                            // Load the image using Glide library
                            ImageView imgCat = findViewById(R.id.imgCat);
                            Glide.with(ChatActivity.this)
                                    .load("imageUrl")
                                    .into(imgCat);

                            // Close keyboard
                            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                        } catch (JSONException exception) {
                            Log.e("API_PARSING", "Error parsing JSON: " + exception.getMessage());
                            exception.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API_ERROR", "Error: " + error.getMessage());
                Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", "515bcadc34msh0415d2999540012p1cde12jsn6cdca9a79630");
                headers.put("X-RapidAPI-Host", "fitness-calculator.p.rapidapi.com");
                return headers;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}