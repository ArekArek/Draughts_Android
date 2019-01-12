package com.tuco.draughtsui.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tuco.draughts.game.util.Player;
import com.tuco.draughtsui.R;
import com.tuco.draughtsui.game.board.BoardView;
import com.tuco.draughtsui.menu.MenuActivity;

import lombok.Getter;


public class GameActivity extends AppCompatActivity {

    @Getter
    private BoardView boardView;
    private Button startButton;
    private TextView winnerLabel;
    private TextView logWindow;
    private Button backToMenuButton;
    private GameActivityLogic gameActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardView = findViewById(R.id.boardView);

        gameActivityLogic = new GameActivityLogic(this);
        gameActivityLogic.initializeGame();

        initializeViews();
    }


    private void initializeViews() {
        boardView.init(gameActivityLogic.getBoard());

        winnerLabel = findViewById(R.id.winnerLabel);

        logWindow = findViewById(R.id.logWindow);
        logWindow.setMovementMethod(new ScrollingMovementMethod());

        startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(view -> startGame());

        backToMenuButton = findViewById(R.id.backToMenuButton);
        backToMenuButton.setOnClickListener(view -> backToMenu());
    }

    private void startGame() {
        startButton.setClickable(false);
        startButton.setVisibility(View.GONE);
        addLog("       START");
        gameActivityLogic.startGameThread();
    }

    public void finishGame(Player winner) {
        winnerLabel.setTextColor(getColor(R.color.colorPrimaryDark));
        String winnerLabelText = "nobody won";
        if (winner != null) {
            switch (winner) {
                case BLACK:
                    winnerLabel.setTextColor(getColor(R.color.black));
                    winnerLabelText = "BLACK won";
                    break;
                case WHITE:
                    winnerLabel.setTextColor(getColor(R.color.white));
                    winnerLabelText = "WHITE won";
                    break;
                case BOTH:
                    winnerLabelText = "DRAW";
                    break;
            }
        }
        winnerLabel.setText(winnerLabelText);
        winnerLabel.setVisibility(View.VISIBLE);
    }

    private void backToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void update() {
        boardView.update(gameActivityLogic.getBoard());
    }

    public void addLog(CharSequence charSequence) {
        runOnUiThread(() -> logWindow.append(charSequence));
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameActivityLogic.stopGameThread();
    }
}
