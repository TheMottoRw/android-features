package com.example.a17rp01001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.speedata.r6lib.IMifareManager;
import com.speedata.r6lib.R6Manager;

import java.util.Timer;
import java.util.TimerTask;

import static com.speedata.r6lib.R6Manager.CardType.MIFARE;

public class Scan extends AppCompatActivity {
    private IMifareManager dev;
    Timer timer;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        dev = R6Manager.getMifareInstance(MIFARE);
        if (dev.InitDev() != 0) {
            Toast.makeText(this,"Open device error!",Toast.LENGTH_LONG).show();
            return;
        }
        Log.e("12", "init ok");
        timer = new Timer();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String id = msg.getData().getString("id").toString();

//                setBlockValue();//load default amount
                Intent intent = new Intent();
                intent.putExtra("cardid",id);
                setResult(Activity.RESULT_OK,intent);
                finish();
                Toast.makeText(getApplicationContext(),"Card ID "+id,Toast.LENGTH_LONG).show();
                return false;
            }
        });

        timerTask = new TimerTask() {
            @Override
            public void run() {
                byte[] ID = dev.SearchCard();
                if (ID == null) {
                    //Log.e("CARD SEARCH", "NO CARD FOUND");
                    return;
                }
                String IDString = new String("");
                for (byte a : ID) {
                    IDString += String.format("%02X", a);
                }
                Message msg = new Message();
                Bundle data = new Bundle();
                msg.setData(data);
                Log.d("Card ID ",IDString);
                data.putString("id",IDString);
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask,0,1000);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        timer.purge();
        timer.cancel();
    }
}
