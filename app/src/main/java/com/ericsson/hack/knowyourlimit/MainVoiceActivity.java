package com.ericsson.hack.knowyourlimit;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;

public class MainVoiceActivity extends Activity implements
        TextToSpeech.OnInitListener,AdapterView.OnItemSelectedListener {
    /** Called when the activity is first created. */

    private TextToSpeech tts;
    private Button button1;
    private int count = 0;
    private int fixedValue = 20;
    private int beforeAlert = 5;
    private Random r;
    EditText textView1;
    EditText textView2;
    EditText textView3;
    private String textWrite;
    Timer T;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_voice);
        textView1 = (EditText) findViewById(R.id.editCurrent);
        textView1.setGravity(Gravity.CENTER);
        textView2 = (EditText) findViewById(R.id.editLimit);
        textView2.setGravity(Gravity.CENTER);
        textView3 = (EditText) findViewById(R.id.editText);
        button1 = (Button) findViewById(R.id.nextBtn);
        textView3.setVisibility(View.INVISIBLE);
        tts = new TextToSpeech(this, this);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CountDownTimer myCountDown = new CountDownTimer(50000, 5000) {
                    public void onTick(long millisUntilFinished) {
                        textView1.setText(String.valueOf((50000 - millisUntilFinished) / 1000));
                        textView2.setText("20");
                        textWrite = setSpeed((50000 - millisUntilFinished) / 1000);
                        button1.setVisibility(View.INVISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        textView3.setText(textWrite);
                    }
                    public void onFinish() {
                        textView1.setText(" -- ");
                        textView2.setText(" -- ");
                        button1.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.INVISIBLE);
                    }
                };
                myCountDown.start();
            }
        });
    }

        /*button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                r = new Random();
                number = 1 + r.nextInt(40 - 1 + 1);
                textView1.setText(String.valueOf(number));
                textView2.setText("20");
                textWrite = setSpeed(number);
                button1.setVisibility(View.INVISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView3.setText(textWrite);
            }
        });*/
    //}

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
            }

        } else { Log.e("TTS", "Initilization Failed!");}

    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private String setSpeed(long speed){

        if(speed <= fixedValue && (fixedValue - speed) == beforeAlert){
            tts.setSpeechRate(1.0f);
            tts.setLanguage(Locale.US);
            String text = "100 meter from school zone. Speed limit is 30km/h..";
            textWrite = text;
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            textView1.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        }
        if(speed > fixedValue){
            String text = "You have exceeded the speed limit. Please Slow Down..";
            tts.setLanguage(Locale.US);
            tts.speak(text, TextToSpeech.QUEUE_ADD, null);
            textWrite = text;
            tts.setSpeechRate(1.0f);
            textView1.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
        return textWrite;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_voice, menu);
        return true;
    }

}