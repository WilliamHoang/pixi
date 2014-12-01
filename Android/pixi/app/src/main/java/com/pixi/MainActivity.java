package com.pixi;


import android.app.Activity;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.util.Log;

import com.couchbase.lite.*;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;




public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TAG = "HelloWorld";
        Log.d(TAG, "Beginning of: Pixi App");
        Log.d(TAG, "End of:  Pixi App");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
