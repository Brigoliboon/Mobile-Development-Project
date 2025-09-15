package com.cmu.lighthead;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout main;
    Button automaticButton;
    Button manualButton;

    enum State{
        ASCEND, DESCEND
    }
    int[] colorSequence = {Color.RED, Color.BLUE, Color.WHITE};
    int positionPointer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ConstraintLayout main = findViewById(R.id.main);
        Button automaticButton = findViewById(R.id.automaticButton);
        automaticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button manualButton = findViewById(R.id.manualButton);
        manualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionPointer <= colorSequence.length-1){
                    positionPointer++;
                    changeColor(main, positionPointer);
                }
                positionPointer = 0;

            }
        });
    }

    public void autoChange(){
        ConstraintLayout main = findViewById(R.id.main);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (true){
                    if (positionPointer <= colorSequence.length-1){
                        positionPointer++;
                    }else{
                        positionPointer--;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeColor(main, positionPointer);
                        }
                    });
                }

            }
        },0,1000);
    }

    public void changeColor(View main,State state, int positionPointer){
        main.setBackgroundColor(colorSequence[positionPointer]);
    }
}