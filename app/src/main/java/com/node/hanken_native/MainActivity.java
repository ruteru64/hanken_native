package com.node.hanken_native;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // アダプタを扱うための変数
    private NfcAdapter mNfcAdapter;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // アダプタのインスタンスを取得
        mNfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(this);

    }

    @Override
    protected void onResume(){
        super.onResume();

        // NFCがかざされたときの設定
        Intent intent = new Intent(this, this.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // ほかのアプリを開かないようにする
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

    }
    @Override
    protected void onPause(){
        super.onPause();

        // Activityがバックグラウンドになったときは、受け取らない
        mNfcAdapter.disableForegroundDispatch(this);

    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        // NFCのUIDを取得
        byte[] uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        String out = "";
        for (int i = 0;i<uid.length;i++){
            int tempint = uid[i];
            tempint = tempint<0?tempint+256:tempint;
            String tempS = Integer.toString(tempint,16);
            tempS = tempS.length()==1?"0"+tempS:tempS;
            out += tempS;
        }
        Log.d(TAG,out);
        // 表示
        Toast.makeText(this,out, Toast.LENGTH_SHORT).show();
    }
}