package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText messageEditText;
    private ListView chatListView;
    private ArrayList<String> chatMessages;
    private ArrayAdapter<String> chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageEditText = findViewById(R.id.messageEditText);
        chatListView = findViewById(R.id.chatListView);

        // Initialize chat messages
        chatMessages = new ArrayList<>();

        // Initialize chat adapter
        chatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatMessages);
        chatListView.setAdapter(chatAdapter);
    }

    public void onSendMessageClick(View view) {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            // Add the message to the chat
            chatMessages.add("You: " + message);
            chatAdapter.notifyDataSetChanged();

            // For demonstration purposes, let's simulate a reply from the potential travel buddy
            simulateReceivedMessage();

            // Clear the message EditText
            messageEditText.getText().clear();
        }
    }

    private void simulateReceivedMessage() {
        // Simulate a reply after a short delay
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // Add a simulated received message to the chat
                        chatMessages.add("Buddy: Hi! Let's plan our trip!");
                        chatAdapter.notifyDataSetChanged();
                    }
                },
                1000 // 1 second delay
        );
    }
}

