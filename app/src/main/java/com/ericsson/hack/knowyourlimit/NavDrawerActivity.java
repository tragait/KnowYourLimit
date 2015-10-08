package com.ericsson.hack.knowyourlimit;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Button button1;
    private int fixedValue = 20;
    private int beforeAlert = 5;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    private String textWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView1 = (TextView) findViewById(R.id.editCurrent);
        textView2 = (TextView) findViewById(R.id.editLimit);
        textView3 = (TextView) findViewById(R.id.editText);
        button1 = (Button) findViewById(R.id.nextBtn);
        textView3.setVisibility(View.INVISIBLE);
        tts = new TextToSpeech(this, this);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                COAPClient.getSpeedLimit(Constants.URI_SPEED_LIMIT);
                CountDownTimer myCountDown = new CountDownTimer(50000, 5000) {
                    public void onTick(long millisUntilFinished) {
                        textView1.setText(String.valueOf((50000 - millisUntilFinished) / 1000));
                        textView2.setText(String.valueOf(fixedValue));
                        textWrite = setSpeed((50000 - millisUntilFinished) / 1000);
                        button1.setVisibility(View.INVISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        textView3.setText(textWrite);
                    }

                    public void onFinish() {
                        textView1.setText(" -- ");
                        textView2.setText(" -- ");
                        textView1.setBackground(getResources().getDrawable(R.drawable.rectangular_gray));
                        button1.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.INVISIBLE);
                    }
                };
                myCountDown.start();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_demo) {
            // Don't do anything. Stay on the same activity.
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_alert) {
            Intent intent = new Intent(this, AlertActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private String setSpeed(long speed) {

        if (speed <= fixedValue && (fixedValue - speed) == beforeAlert) {
            tts.setSpeechRate(1.0f);
            tts.setLanguage(Locale.US);
            String text = "100 meter from school zone. Speed limit is 30km/h..";
            textWrite = text;
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
        }
        if (speed > fixedValue) {
            String text = "You have exceeded the speed limit. Please Slow Down..";
            tts.setLanguage(Locale.US);
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            textWrite = text;
            tts.setSpeechRate(1.0f);
            textView1.setBackground(getResources().getDrawable(R.drawable.rectangular_red));
        }
        return textWrite;
    }
}
