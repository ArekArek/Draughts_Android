package com.tuco.draughtsui.game.board;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.TableRow;

import com.tuco.draughts.board.Chequer;

public class PlaceView extends android.support.v7.widget.AppCompatImageView {
    private Chequer chequer;

    private static final TableRow.LayoutParams cellLayout;

    static {
        cellLayout = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
        cellLayout.weight = 1;
        cellLayout.setMargins(5, 5, 5, 5);
    }

    public PlaceView(Context context, Chequer chequer) {
        super(context);
        this.chequer = chequer;
        init();
    }

    private void init() {
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


}
