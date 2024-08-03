package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean player1Active;
    private TextView player1Score, player2Score, playerStatus;
    private Button[] buttons = new Button[9];
    private Button reset, playagain;

    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPosition = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    int rounds;

    private int player1ScoreCount, player2ScoreCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        player1Score = findViewById(R.id.scoreP1);
        player2Score = findViewById(R.id.scoreP2);
        playerStatus = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btnReset);
        playagain = findViewById(R.id.btnPlayAgain);

        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);

        for(int i=1; i<buttons.length; i++)
        {
            buttons[i].setOnClickListener(this);
        }

        player1ScoreCount = 0;
        player2ScoreCount = 0;
        player1Active = true;
        rounds = 0;


    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals(""))
        {
            return;
        } else if (checkWinner()) {
            return;
        }
        String buttonId = v.getResources().getResourceEntryName(v.getId());
        int gameStartPointer = Integer.parseInt(buttonId.substring(buttonId.length()-1,buttonId.length()));

        if (player1Active){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#ffc34a"));
            gameState[gameStartPointer] = 0;
        }
        else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70fc3a"));
            gameState[gameStartPointer] = 1;
        }
        rounds++;

        if (checkWinner()){
            if (player1Active){
                player1ScoreCount++;
                updatePlayerScore();
                playerStatus.setText("player 1 has won");
            }
            else {
                player2ScoreCount++;
                updatePlayerScore();
                playerStatus.setText("player 2 has won");
            }
        }
        else if (rounds==9) {
            playerStatus.setText("No Winner");
        }
        else {
            player1Active = !player1Active;
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playagain();
                player1ScoreCount = 0;
                player2ScoreCount = 0;
                updatePlayerScore();
            }
        });

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playagain();
            }
        });
    }

    private boolean checkWinner() {
        boolean winnerResult = false;
        for (int[] winningPosition: winningPosition){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] !=2)
            {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    private void playagain() {
        rounds = 0;
        player1Active = true;
        for (int i=0; i<buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
        playerStatus.setText("Status");
    }

    private void updatePlayerScore() {
        player1Score.setText(Integer.toString(player1ScoreCount));
        player2Score.setText(Integer.toString(player2ScoreCount));
    }
}