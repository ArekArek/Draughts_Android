package com.tuco.draughtsui.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tuco.draughts.board.util.StandardBoardCreator;
import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.movement.maker.AIMovementMaker;
import com.tuco.draughts.movement.maker.AlgorithmType;
import com.tuco.draughts.movement.maker.Heuristic;
import com.tuco.draughts.movement.maker.HumanMovementInformator;
import com.tuco.draughts.movement.maker.HumanMovementMaker;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughtsui.R;
import com.tuco.draughtsui.game.board.BoardView;
import com.tuco.draughtsui.game.movement.AndroidMovementInformator;


public class GameActivity extends AppCompatActivity {

    private DraughtGameManager gameManager;
    private BoardView boardView;
    private Button startButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardView = findViewById(R.id.boardView);

        initializeGame();

        initializeViews();
    }

    private void initializeGame() {
        ChangeTurnListener generalChangeTurnListener = new ChangeTurnListener() {
            @Override
            public void afterTurn(Movement movement) {
                try {
                    synchronized (boardView) {
                        runOnUiThread(() -> boardView.showMove(movement));
                        boardView.wait();
                        runOnUiThread(() -> updateScreen());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        DraughtsState state = new DraughtsState(new StandardBoardCreator());

        HumanMovementInformator humanMovementInformator = new AndroidMovementInformator(boardView);
        MovementMaker human = new HumanMovementMaker(state, boardView.getHumanPositionLoader(), humanMovementInformator);
        MovementMaker ai = new AIMovementMaker(state, AlgorithmType.SCOUT, Heuristic.SIMPLE);

        gameManager = DraughtGameManager.builder()
                .state(state)
                .playerWhite(human)
                .playerBlack(ai)
                .generalChangeTurnListener(generalChangeTurnListener)
                .build();
    }

    private void initializeViews() {
        boardView.init(gameManager.getState().getBoard());

        startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(view -> {
            startButton.setClickable(false);
            refreshButtonText();

            Runnable runnable = () -> gameManager.play();
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }

    private void refreshButtonText() {
        if (gameManager.isPlaying()) {
            switch (gameManager.getState().getPlayer()) {
                case WHITE:
                    startButton.setText("Player WHITE");
                    break;
                case BLACK:
                    startButton.setText("Player BLACK");
                    break;
            }
        }
    }

    private void updateScreen() {
        boardView.update(gameManager.getState().getBoard());

        refreshButtonText();
    }
}
