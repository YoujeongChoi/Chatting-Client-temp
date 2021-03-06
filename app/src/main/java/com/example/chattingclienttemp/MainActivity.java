package com.example.chattingclienttemp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    InetAddress serverAddr;
    Socket socket;
    PrintWriter printwriter;
    String IpAddress;
//    private String ip = "your ip";
    private int port = 8887;
    TextView main_top_userId;
   // TextView textView;
    String UserID;
    ImageButton chatbutton;
    TextView chatView;
    EditText message;
    String sendmsg;
    String read;

    @Override
    protected void onStop() {
        super.onStop();
        try {
            printwriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        handler = new Handler();
//        textView = (TextView) findViewById(R.id.textView);
        chatView = (TextView) findViewById(R.id.chatRv);
        message = (EditText) findViewById(R.id.getMessageText);

        UserID = intent.getStringExtra("username");
        IpAddress = intent.getStringExtra("ip");

        main_top_userId = (TextView) findViewById(R.id.main_top_userId);
        main_top_userId.setText(UserID);

        // textView.setText(UserID);
        chatbutton = (ImageButton) findViewById(R.id.chatbutton);

        new Thread() {
            public void run() {
                try {
                    InetAddress serverAddr = InetAddress.getByName(IpAddress);
                    socket = new Socket(serverAddr, port);
                    printwriter = new PrintWriter(socket.getOutputStream());
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while(true){
                        read = input.readLine();

                        System.out.println("TTTTTTTT"+read);
                        if(read!=null){
                            handler.post(new msgUpdate(read));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } }}.start();

        chatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmsg = message.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            printwriter.println(UserID +": "+ sendmsg);
                            printwriter.flush();
                            message.setText("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    class msgUpdate implements Runnable{
        private String msg;
        public msgUpdate(String str) {this.msg=str;}

        @Override
        public void run() {
            chatView.setText(chatView.getText().toString()+msg+"\n");
        }
    }
}