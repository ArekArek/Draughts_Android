package com.tuco.draughtsui.game.movement;

import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.maker.HumanMovementInformator;
import com.tuco.draughtsui.game.board.BoardView;

import java.util.List;

public class AndroidMovementInformator implements HumanMovementInformator {
    private BoardView boardView;

    public AndroidMovementInformator(BoardView boardView) {
        this.boardView = boardView;
    }

    @Override
    public void choosePosition(List<Coordinate> possiblePositions) {
        boardView.highlightPossible(possiblePositions);
    }

    @Override
    public void wrongPositionChosen() {

    }
}
