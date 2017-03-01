package com.vogella.android.fontintent;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static EditText et_entry ;
    private static TextView tv_display;
    private static int requestCode;
    private static int colorA, colorR, colorG, colorB, setFontSize;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_display = (TextView)findViewById(R.id.tv_displaymain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //set action on menu selection
        int id = item.getItemId();
        switch (id){
            case  R.id.m_display_text:
                et_entry = (EditText)findViewById(R.id.et_inputmain);
                tv_display.setText(et_entry.getText().toString());
                return true;
            case R.id.m_change_font:
                Intent fontIntent = new Intent("msud.cs3013.ACTION_RETRIEVE_FONT");
                fontIntent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
                //fontIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if (fontIntent != null) {
                    requestCode = 404;
                    Log.d(TAG, "Called the Intent application");
                    startActivityForResult(fontIntent, requestCode);
                }else{
                    System.err.println(TAG + ": There is no activity found to handle Intent");
                }

                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == this.requestCode) {
            Log.i(TAG, " onActivityResult: Returned from choosing font");
            if (data.hasExtra("Color A")) {
                colorA = data.getIntExtra("Color A",200);
                Log.i(TAG, "Color value A: " + colorA);
            }
            if (data.hasExtra("Color R")) {
                colorR = data.getIntExtra("Color R",200);
                Log.i(TAG, "Color value R: " + colorR);
            }
            if (data.hasExtra("Color G")) {
                colorG = data.getIntExtra("Color G",200);
                Log.i(TAG, "Color value G: " + colorG);
            }
            if (data.hasExtra("Color B")) {
                colorB = data.getIntExtra("Color B",200);
                Log.i(TAG, "Color value B: " + colorB);
            }
            if (data.hasExtra("FontSize")) {
                setFontSize = data.getIntExtra("FontSize",25);
                Log.i(TAG, "Font value : " + setFontSize);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
