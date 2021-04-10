package com.example.tempaudiolistener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    private int record_audio = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        editText = findViewById(R.id.editText_text_display);

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> collection = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (collection != null){
                    editText.setText(collection.get(0));
                }
            }

            /**
             * Called when partial recognition results are available. The callback might be called at any
             * time between {@link #onBeginningOfSpeech()} and {@link #onResults(Bundle)} when partial
             * results are ready. This method may be called zero, one or multiple times for each call to
             * {@link SpeechRecognizer#startListening(Intent)}, depending on the speech recognition
             * service implementation.  To request partial results, use
             * {@link RecognizerIntent#EXTRA_PARTIAL_RESULTS}
             *
             * @param partialResults the returned results. To retrieve the results in
             *                       ArrayList&lt;String&gt; format use {@link Bundle#getStringArrayList(String)} with
             *                       {@link SpeechRecognizer#RESULTS_RECOGNITION} as a parameter
             */
            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            /**
             * Reserved for adding future events.
             *
             * @param eventType the type of the occurred event
             * @param params    a Bundle containing the passed parameters
             */
            @Override
            public void onEvent(int eventType, Bundle params) {

            }


        });

        findViewById(R.id.button_speak).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){

                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see the input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        editText.setText("");
                        editText.setHint("Listening...");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }
                return false;
            }

        });
    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if((ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED)){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO))
                {
                    Toast.makeText(this, "Please grant permission to record audio", Toast.LENGTH_SHORT).show();

                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO},
                            record_audio);
                } else{
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO},
                            record_audio);
                }

//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
//                startActivity(intent);
//                finish();
            }
        }
    }
}