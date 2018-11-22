package com.tuco.draughtsui.game.movement;

import android.view.View;

import com.tuco.draughts.board.util.Coordinate;
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

    @Override
    public Coordinate loadPositionFromUser() {
        synchronized (this) {
            try {
                coordinate = new Coordinate();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return coordinate;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof PlaceView) {
            PlaceView placeView = (PlaceView) view;
            placeView.showClick();

            synchronized (this) {
                coordinate = placeView.getCoordinate();
                notifyAll();
            }
        }
    }
}
