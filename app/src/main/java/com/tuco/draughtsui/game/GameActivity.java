package com.tuco.draughtsui.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tuco.draughts.board.util.StandardBoardCreator;
import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughtsui.R;
import com.tuco.draughtsui.game.board.BoardView;
import com.tuco.draughtsui.game.movement.GameTurnChanger;
import com.tuco.draughtsui.game.movement.MovementMakerCreator;
import com.tuco.draughtsui.menu.MenuActivity;
import com.tuco.draughtsui.menu.configuration.PlayerConfigurationDTO;


public class GameActivity extends AppCompatActivity {

    private static DraughtGameManager gameManager;
    private BoardView boardView;
    private Button startButton;
    private Button backToMenuButton;
    private static Thread gameManagerThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        boardView = findViewById(R.id.boardView);

        initializeGame();

        initializeViews();
    }

    private void initializeGame() {
        DraughtsState state = new DraughtsState(new StandardBoardCreator());
        MovementMaker playerWhite = createPlayer(state, "whiteConfiguration");
        MovementMaker playerBlack = createPlayer(state, "blackConfiguration");
        ChangeTurnListener generalChangeTurnListener = new GameTurnChanger(boardView, this);

        gameManager = DraughtGameManager.builder()
                .state(state)
                .playerWhite(playerWhite)
                .playerBlack(playerBlack)
                .generalChangeTurnListener(generalChangeTurnListener)
                .build();
    }

    private MovementMaker createPlayer(DraughtsState state, String extraName) {
        PlayerConfigurationDTO whitePlayerConfiguration = getIntent().getExtras().getParcelable(extraName);
        return MovementMakerCreator.create(whitePlayerConfiguration, boardView, state);
    }

    private void initializeViews() {
        boardView.init(gameManager.getState().getBoard());

        startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(view -> startGame());

        backToMenuButton = findViewById(R.id.backToMenuButton);
        backToMenuButton.setOnClickListener(view -> backToMenu());

    }

    private void startGame() {
        startButton.setClickable(false);
        refreshButtonText();
        startGameThread();
    }

    private void startGameThread() {
        if (gameManagerThread != null) {
            try {
                gameManagerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameManagerThread = new Thread(() -> gameManager.play());
        gameManagerThread.start();
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

    private void backToMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void update() {
        boardView.update(gameManager.getState().getBoard());

        refreshButtonText();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameManager.stopGame();
    }
}
