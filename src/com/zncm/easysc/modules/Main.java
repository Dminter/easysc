package com.zncm.easysc.modules;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Main.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }


}