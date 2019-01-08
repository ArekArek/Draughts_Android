package com.tuco.draughtsui.game;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.util.Draughts64BoardCreator;
import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughtsui.game.board.BoardView;
import com.tuco.draughtsui.game.movement.GameTurnChanger;
import com.tuco.draughtsui.game.movement.MovementMakerCreator;
import com.tuco.draughtsui.menu.configuration.PlayerConfigurationDTO;

public class GameActivityLogic {

    private GameActivity gameActivity;
    private BoardView boardView;
    private DraughtGameManager gameManager;
    private Thread gameManagerThread;

    public GameActivityLogic(GameActivity gameActivity, BoardView boardView) {
        this.gameActivity = gameActivity;
        this.boardView = boardView;
    }

    public void initializeGame() {
        DraughtsState state = new DraughtsState(new Draughts64BoardCreator());

        MovementMaker playerWhite = createPlayer(state, "whiteConfiguration");
        MovementMaker playerBlack = createPlayer(state, "blackConfiguration");

        ChangeTurnListener generalChangeTurnListener = new GameTurnChanger(boardView, gameActivity);

        gameManager = DraughtGameManager.builder()
                .state(state)
                .playerWhite(playerWhite)
                .playerBlack(playerBlack)
                .generalChangeTurnListener(generalChangeTurnListener)
                .build();
    }

    private MovementMaker createPlayer(DraughtsState state, String extraName) {
        PlayerConfigurationDTO whitePlayerConfiguration = gameActivity.getIntent().getExtras().getParcelable(extraName);
        return MovementMakerCreator.create(whitePlayerConfiguration, boardView, state);
    }

    public void startGameThread() {
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

    public void stopGameThread() {
        gameManager.stopGame();
    }

    public Board getBoard() {
        return gameManager.getState().getBoard();
    }
}