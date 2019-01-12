package com.tuco.draughtsui.game.movement;

import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.game.util.Player;
import com.tuco.draughts.movement.maker.AIMovementDescription;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughtsui.game.GameActivity;
import com.tuco.draughtsui.game.board.BoardView;

public class GameTurnChanger implements ChangeTurnListener {


    private final GameActivity gameActivity;

    public GameTurnChanger(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    public void beforeTurn(DraughtGameManager gameManager) {
        StringBuilder stringBuilder = new StringBuilder();
        int turnCount = gameManager.getTurnCounter();
        stringBuilder.append("\n\n--------Turn: ").append(turnCount).append("--------");
        Player player = gameManager.getState().getPlayer();
        stringBuilder.append("\n   Player ").append(player.getValue()).append("   ");
        gameActivity.addLog(stringBuilder.toString());
    }

    @Override
    public void afterTurn(Movement movement) {
        try {
            BoardView boardView = gameActivity.getBoardView();
            synchronized (boardView) {
                gameActivity.runOnUiThread(() -> boardView.showMove(movement));
                boardView.wait();
                gameActivity.runOnUiThread(gameActivity::update);
                boardView.wait();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\nMove:");
                movement.toHumanReadableList().forEach(s -> stringBuilder.append("\n\t").append(s));
                gameActivity.addLog(stringBuilder.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterAITurn(AIMovementDescription movementDescription) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nMove score:  ").append(movementDescription.getScore());
        stringBuilder.append("\nClosed count:  ").append(movementDescription.getClosedCount());
        stringBuilder.append("\nDepth reached:  ").append(movementDescription.getDepthReached());
        stringBuilder.append("\nDuration:  ").append(movementDescription.getDuration()).append("ms");
        gameActivity.addLog(stringBuilder.toString());
    }
}
