package com.tuco.draughtsui.game.board;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.TableRow;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;

import lombok.Getter;

public class PlaceView extends android.support.v7.widget.AppCompatImageView {

    private Chequer chequer;

    @Getter
    private Coordinate coordinate;

    private static final TableRow.LayoutParams cellLayout;

    static {
        cellLayout = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
        cellLayout.weight = 1;
        cellLayout.setMargins(5, 5, 5, 5);
    }

    private PlaceView(Context context, Chequer chequer, Coordinate coordinate, OnClickListener onClickListener) {
        super(context);
        this.chequer = chequer;
        this.coordinate = coordinate;

        setOnClickListener(onClickListener);
        setLayoutParams(cellLayout);
        initDrawables();
    }

    private void initDrawables() {
        ChequerDrawablePalette chequerDrawablePalette = ChequerDrawablePalette.getDefault(getResources());

        Drawable[] layers = new Drawable[2];
        layers[0] = chequerDrawablePalette.getBackground(chequer);
        layers[1] = chequerDrawablePalette.getImage(chequer);
        LayerDrawable layerDrawable = new LayerDrawable(layers);

        setImageDrawable(layerDrawable);
    }


    public static class Builder {
        private Chequer chequer;
        private Coordinate coordinate;
        private OnClickListener onClickListener;
        private Context context;

        Builder(Context context) {
            this.context = context;
        }

        public Builder setChequer(Chequer chequer) {
            this.chequer = chequer;
            return this;
        }

        public Builder setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }

        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public PlaceView build() {
            return new PlaceView(context, chequer, coordinate, onClickListener);
        }
    }
}
