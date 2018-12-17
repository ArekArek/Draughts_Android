package com.tuco.draughtsui.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tuco.draughtsui.R;
import com.tuco.draughtsui.game.board.BoardView;
import com.tuco.draughtsui.menu.MenuActivity;


public class GameActivity extends AppCompatActivity {

    private BoardView boardView;
    private Button startButton;
    private Button backToMenuButton;
    private GameActivityLogic gameActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardView = findViewById(R.id.boardView);

        gameActivityLogic = new GameActivityLogic(this, boardView);
        gameActivityLogic.initializeGame();

        initializeViews();
    }


    private void initializeViews() {
        boardView.init(gameActivityLogic.getBoard());

        startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(view -> startGame());

        backToMenuButton = findViewById(R.id.backToMenuButton);
        backToMenuButton.setOnClickListener(view -> backToMenu());
    }

    private void startGame() {
        startButton.setClickable(false);
        startButton.setVisibility(View.GONE);
        gameActivityLogic.startGameThread();
    }

    private void backToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void update() {
        boardView.update(gameActivityLogic.getBoard());
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameActivityLogic.stopGameThread();
    }
}
