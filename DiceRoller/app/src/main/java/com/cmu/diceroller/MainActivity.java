package com.cmu.diceroller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView dice;
    Button diceRoller;
    int durationCounter = 0;
    int index = 0;
    String[] dices = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
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
        Button diceRoller = findViewById(R.id.diceRoller);

        diceRoller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }

    public void rollDice(){
        TextView dice = findViewById(R.id.dice);
        Button diceRoller = findViewById(R.id.diceRoller);
        // Generates a random duration for each roll
        int durationLimit = rollDurationRandomizer();

        int n = dices.length;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                durationCounter++;
                index = (index + 1) % n;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dice.setText(dices[index]);
                        // disables the button while rolling
                        diceRoller.setEnabled(false);

                        // resets the normal state
                        if (durationCounter >= durationLimit){
                            timer.cancel();
                            durationCounter=0;
                            diceRoller.setEnabled(true);
                            index = 0;
                        }
                    }
                });

            }
        }, 0, 600);
    }

    private int rollDurationRandomizer(){
        int min = 5;
        int max = 10;
        int randomNum = new Random().nextInt((max - min) + 1) + min;

        return  randomNum;
    }
}