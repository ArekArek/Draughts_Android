package com.tuco.draughtsui.game.movement;

import android.support.annotation.NonNull;

import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.movement.maker.AIMovementMaker;
import com.tuco.draughts.movement.maker.HumanMovementInformator;
import com.tuco.draughts.movement.maker.HumanMovementMaker;
import com.tuco.draughts.movement.maker.MovementMaker;
import com.tuco.draughtsui.game.board.BoardView;
import com.tuco.draughtsui.menu.configuration.PlayerConfigurationDTO;

public class MovementMakerCreator {
    public static MovementMaker create(PlayerConfigurationDTO playerConfiguration, BoardView boardView, DraughtsState state) {
        if (playerConfiguration == null || playerConfiguration.isHuman()) {
            return createHuman(boardView, state);
        } else {
            return createAI(playerConfiguration, state);
        }
    }

    @NonNull
    private static MovementMaker createHuman(BoardView boardView, DraughtsState state) {
        HumanMovementInformator humanMovementInformator = new AndroidMovementInformator(boardView);
        return new HumanMovementMaker(state, boardView.getHumanPositionLoader(), humanMovementInformator);
    }

    @NonNull
    private static MovementMaker createAI(PlayerConfigurationDTO playerConfiguration, DraughtsState state) {
        AIMovementMaker ai = new AIMovementMaker(state, playerConfiguration.getAlgorithm(), playerConfiguration.getHeuristic());
        ai.setDepthLimit(playerConfiguration.getDepth());
        ai.setQuiescenceOn(playerConfiguration.isQuiescence());
        if (playerConfiguration.getTimeLimit() > 0) {
            ai.setTimeLimit((long) (playerConfiguration.getTimeLimit() * 1000));
        }
        return ai;
    }
}
