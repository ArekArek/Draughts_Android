package com.tuco.draughtsui.game.movement;

import android.view.View;

import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.maker.MoveStoppedException;
import com.tuco.draughts.movement.maker.PositionLoader;
import com.tuco.draughtsui.game.board.PlaceView;

public class HumanPositionLoader implements PositionLoader, View.OnClickListener {

    private Coordinate coordinate;

    private HumanPositionLoader() {
    }

    public static HumanPositionLoader getInstance() {
        return HumanPositionLoaderHolder.INSTANCE;
    }

    private static class HumanPositionLoaderHolder {
        private static final HumanPositionLoader INSTANCE = new HumanPositionLoader();
    }

    private final Object waitLock = new Object();

    @Override
    public Coordinate loadPositionFromUser() throws MoveStoppedException {
        coordinate = null;
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (coordinate == null) {
            throw new MoveStoppedException();
        }

        return coordinate;
    }

    @Override
    public void onClick(View view) {
        synchronized (waitLock) {
            if (view instanceof PlaceView) {
                PlaceView placeView = (PlaceView) view;
                placeView.showClick();

                coordinate = placeView.getCoordinate();
                waitLock.notifyAll();
            }
        }
    }

    @Override
    public void stop() {
        synchronized (waitLock) {
            coordinate = null;
            waitLock.notifyAll();
        }
    }
}
