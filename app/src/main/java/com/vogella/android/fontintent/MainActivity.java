package com.vogella.android.fontintent;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static EditText et_entry, et_urlEntry ;
    private static TextView tv_display,tv_url;
    private static Button but_displayText, but_urlClick;
    private static int requestCode;
    private static int colorA, colorR, colorG, colorB, setFontSize;
    private static String setStyle = null, setMyTypeFace;
    private String TAG = MainActivity.class.getSimpleName();
    private LinearLayout linearLayout;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_display = (TextView)findViewById(R.id.tv_displaymain);
        tv_url = (TextView)findViewById(R.id.tv_url);

        linearLayout = (LinearLayout)findViewById(R.id.activity_main);
        webView = (WebView)findViewById(R.id.webview);

        but_displayText = (Button)findViewById(R.id.button_displayText);
        but_urlClick = (Button)findViewById(R.id.button_urlClick);

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
            case  R.id.m_buttons_change:
                changeButtons();
                return true;
            case R.id.m_change_font:
                requestCode = 404;
                callIntent(requestCode);
                return true;
            case R.id.m_changebackground:
                linearLayout.setBackgroundColor(Color.argb(colorA,colorR,colorG,colorB));
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void callIntent(int requestCode){
        Intent fontIntent = new Intent("msud.cs3013.ACTION_RETRIEVE_FONT");
        fontIntent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION);
        if (fontIntent != null) {
            Log.d(TAG, "Called the Intent application");
            startActivityForResult(fontIntent, requestCode);
        }else{
            System.err.println(TAG + ": There is no activity found to handle Intent");
        }
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
            if (data.hasExtra("FontSize") && (data.getIntExtra("FontSize",25) != 2012)) {
                setFontSize = data.getIntExtra("FontSize",25);
                Log.i(TAG, "Font value : " + setFontSize);
            }
            if (data.hasExtra("Typeface") && !(data.getStringExtra("Typeface").matches("NONE"))){
                setStyle = data.getStringExtra ("Typeface");
                Log.i(TAG, "The Font Typeface value: " + setStyle);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void imageBut_click(View view) {
        setFont(tv_display);
        setSize(tv_display);
        setFontStyle(tv_display);
    }

    public void button_click(View view) {
        et_entry = (EditText)findViewById(R.id.et_inputmain);
        Log.i(TAG, "The value of Edit text is: " + et_entry.getText().toString());
        tv_display.setText(et_entry.getText().toString());
    }

    public void buttonURLClick(View view) {
        webView.loadUrl("http://rowdysites.msudenver.edu/~gordona/cs3013/hw/Android4_W2017-intents.html");
        //webView.loadUrl("https://developer.android.com/index.html")
        tv_url.setText("http://rowdysites.msudenver.edu/~gordona/cs3013/hw/Android4_W2017-intents.html");

    }

    public void imageButWebView(View view) {
        webView.setBackgroundColor(colorA);
        Log.v(TAG, " setting the background color " + colorA);
        webView.getSettings().supportZoom();
        webView.setBackgroundColor(colorA);
       // webView.setBackgroundColor(Color.argb(colorA,colorR,colorG,colorB));
    }

    public void setFontStyle(TextView tv) {
        Log.e(TAG, "setFontStyle method" + setStyle);
        if (setStyle != null) {
            Log.e(TAG, "setFontStyle method: " + setStyle);
            if (setStyle.equals("DEFAULT")) {
                tv.setTypeface(Typeface.DEFAULT);
            } else if (setStyle.equals("DEFAULT_BOLD")) {
                tv.setTypeface(Typeface.DEFAULT_BOLD);
            } else if (setStyle.equals("MONOSPACE")) {
                tv.setTypeface(Typeface.MONOSPACE);
            } else if (setStyle.equals("SANS_SERIF")) {
                tv.setTypeface(Typeface.SANS_SERIF);
            } else if (setStyle.equals("SERIF")) {
                tv.setTypeface(Typeface.SERIF);
            } else if (setStyle.equals("BOLD")) {
                tv.setTypeface(null, Typeface.BOLD);
            } else if (setStyle.equals("BOLD_ITALIC")) {
                tv.setTypeface(null, Typeface.BOLD_ITALIC);
            } else if (setStyle.equals("ITALIC")) {
                tv.setTypeface(null, Typeface.ITALIC);
            } else if(setStyle.equals("NORMAL")) {
                tv.setTypeface(null, Typeface.NORMAL);
            }
        }
    }

    public void setFont(TextView tv) {
        if (requestCode == 404){
            Log.i(TAG, "Setting Font");
            tv.setTextColor(Color.argb(colorA, colorR, colorG, colorB ));
        }
    }

    public void setSize(TextView tv){
        if (setFontSize  != 2012){
            Log.i(TAG, "Setting Size");
            tv.setTextSize(setFontSize);
        }
    }

    private void changeButtons() {
        but_displayText.setBackgroundColor(Color.argb(colorA,colorR,colorB,colorB));
        but_urlClick.setBackgroundColor(Color.argb(colorA,colorR,colorB,colorB));
    }
}