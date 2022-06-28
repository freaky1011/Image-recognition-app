package com.example.forenseekforenseek;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class Text2speech extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarSpeed, mSeekBarPitch;
    private Button mButtonSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2speech);

        mButtonSpeak = findViewById(R.id.button_speak);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                  int result =  mTTS.setLanguage(Locale.ENGLISH);

                  if(result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED ){
                      Log.e("TTS","Language not Supported");
                  }else{
                      mButtonSpeak.setEnabled(true);
                  }
                }else {
                    Log.e("TTS","Initialization Failed");
                }

            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    private void speak(){
        String text = mEditText.getText().toString();
        float   pitch =(float) mSeekBarPitch.getProgress() / 50;
        if(pitch < 0.1) pitch = 0.1f;
        float   speed =(float) mSeekBarSpeed.getProgress() / 50;
        if(pitch < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH, null);
    }
}