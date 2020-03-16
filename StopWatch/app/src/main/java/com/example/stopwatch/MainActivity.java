package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button btn_start, btn_stop, btn_record, btn_pause;
    private TextView timeView, recordView;
    private Thread timeThread = null;
    private Boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_stop = (Button)findViewById(R.id.btn_stop);
        btn_record = (Button)findViewById(R.id.btn_record);
        btn_pause = (Button)findViewById(R.id.btn_pause);
        timeView = (TextView)findViewById(R.id.timeView);
        recordView = (TextView)findViewById(R.id.recordView);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                btn_stop.setVisibility(View.VISIBLE);
                btn_record.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.VISIBLE);

                timeThread = new Thread(new timeThread());
                timeThread.start();
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                btn_start.setVisibility(View.VISIBLE);
                btn_record.setVisibility(View.GONE);
                btn_pause.setVisibility(View.GONE);
                recordView.setText("");
                timeThread.interrupt();
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordView.setText(recordView.getText() + timeView.getText().toString() + "\n");
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                if(isRunning){
                    btn_pause.setText("일시정지");
                }else{
                    btn_pause.setText("시작");
               }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) /60;
            int hour = (msg.arg1 / 100) / 360;

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);
            if(result.equals("00:01:00:00")){
                Toast.makeText(MainActivity.this, "1분이 지났습니다.", Toast.LENGTH_SHORT).show();
            }
            timeView.setText(result);

        }
    };

    public class timeThread implements Runnable{
        @Override
        public void run(){
            int i = 0;

            while(true){
                while(isRunning){
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try{
                        Thread.sleep(10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeView.setText("");
                                timeView.setText("00:00:00:00");
                            }
                        });
                        return;
                    }
                }
            }
        }
    }
}
