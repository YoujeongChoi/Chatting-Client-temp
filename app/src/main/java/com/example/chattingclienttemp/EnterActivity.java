package com.example.chattingclienttemp;


import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EnterActivity extends AppCompatActivity {

    EditText editText, ipAddress;
    Button enterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        enterButton = (Button)findViewById(R.id.enterButton);
        editText = (EditText)findViewById(R.id.editText);
        ipAddress = (EditText)findViewById((R.id.getIpAddress));
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                String ip = ipAddress.getText().toString();
                String username = editText.getText().toString();
//                ArrayList<String> data = new ArrayList<>();
//                data.add(ip);
//                data.add(username);
                intent.putExtra("username", username);
                intent.putExtra("ip",ip);
                // intent.putExtra("ip", ip);
                // makeText(getApplicationContext(), "${editText}님이 입장하셨습니다.", Toast.LENGTH_SHORT);
                startActivity(intent);
                finish();

            }
        });

    }
}