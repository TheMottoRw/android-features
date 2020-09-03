package com.example.a17rp01001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.speedata.r6lib.IMifareManager;
public class CardReader extends AppCompatActivity {


    private static final int RC_OCR_CAPTURE = 9003;
    private static final int RC_BARCODE_CAPTURE = 9005;
    private static final String TAG = "CardReader";

    private TextView tvCardNo,tvOcr,tvCoded;
    private Button btnReadCard,btnOcr,btnCoded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);

        Log.e(TAG, "begin to init");
        tvCardNo = findViewById(R.id.cardNo);
        tvOcr = findViewById(R.id.tvOcr);
        tvCoded = findViewById(R.id.tvBarcode);
        btnReadCard = findViewById(R.id.btnReadCard);
        btnOcr = findViewById(R.id.btnOcr);
        btnCoded = findViewById(R.id.btnBarcode);

        btnOcr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardReader.this, OcrCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, true);
                intent.putExtra(OcrCaptureActivity.UseFlash, false);

                startActivityForResult(intent, RC_OCR_CAPTURE);
            }
        });
        btnCoded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardReader.this, BarcodeCaptureActivity.class);
                intent.putExtra(OcrCaptureActivity.AutoFocus, true);
                intent.putExtra(OcrCaptureActivity.UseFlash, false);

                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });

        btnReadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CardReader.this,Scan.class),1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent obj){
        super.onActivityResult(requestCode,resultCode,obj);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                tvCardNo.setText(obj.getStringExtra("cardid"));
            }
        } else if(requestCode == RC_OCR_CAPTURE){
            if(resultCode == CommonStatusCodes.SUCCESS){
                Intent intent = obj;
                String text = intent.getStringExtra("String");
                tvOcr.setText(text);
                Toast.makeText(getApplicationContext(),obj.getStringExtra("String"),Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == RC_BARCODE_CAPTURE){
            if(resultCode == CommonStatusCodes.SUCCESS){
                Intent intent = obj;
                String text = intent.getStringExtra("Barcode");
                tvCoded.setText(text);
                Toast.makeText(getApplicationContext(),"Scanned "+obj.getStringExtra("Barcode"),Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
//        startTimer();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
//        stopTimer();
    }
}
