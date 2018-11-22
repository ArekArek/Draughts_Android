package com.tuco.draughtsui.game.movement;

import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughtsui.game.GameActivity;
import com.tuco.draughtsui.game.board.BoardView;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameTurnChanger implements ChangeTurnListener {

    private final BoardView boardView;
    private final GameActivity gameActivity;

    @Override
    public void afterTurn(Movement movement) {
        try {
            synchronized (boardView) {
                gameActivity.runOnUiThread(() -> boardView.showMove(movement));
                boardView.wait();
                gameActivity.runOnUiThread(gameActivity::update);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
