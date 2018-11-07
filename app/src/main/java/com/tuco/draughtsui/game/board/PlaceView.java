package com.tuco.draughtsui.game.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;

import lombok.Getter;
import lombok.Setter;

public class PlaceView extends android.support.v7.widget.AppCompatImageView {

    private static final int CLICK_DURATION = 600;

    @Getter
    private Chequer chequer;

    @Getter
    private Coordinate coordinate;

    private TransitionDrawable transitionDrawable;

    @Getter
    private Drawable chequerDrawable;

    @Setter
    private boolean hidden;

    public static final TableRow.LayoutParams CELL_LAYOUT;

    static {
        CELL_LAYOUT = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT);
        CELL_LAYOUT.weight = 1;
    }

    public PlaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private PlaceView(Context context, Chequer chequer, Coordinate coordinate) {
        super(context);
        this.chequer = chequer;
        this.coordinate = coordinate;

        setLayoutParams(CELL_LAYOUT);
        initDrawables();
    }

    private void initDrawables() {
        ChequerDrawablePalette chequerDrawablePalette = ChequerDrawablePalette.getDefault(getResources());

        chequerDrawable = chequerDrawablePalette.getImage(chequer);

        Drawable images[] = new Drawable[2];
        images[0] = chequerDrawablePalette.getBackground(chequer);
        images[1] = chequerDrawablePalette.getClickView();
        transitionDrawable = new TransitionDrawable(images);

        setImageDrawable(transitionDrawable);
    }

    public void showClick() {
        transitionDrawable.startTransition(CLICK_DURATION / 2);
        transitionDrawable.reverseTransition(CLICK_DURATION / 2);
    }

    public void setChequer(Chequer chequer) {
        this.chequer = chequer;
        initDrawables();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (chequerDrawable != null) {
            chequerDrawable.setBounds(canvas.getClipBounds());
            chequerDrawable.draw(canvas);
        }
        if (hidden) {
            canvas.drawARGB(95, 0, 0, 0);
        }
    }

    public static class Builder {
        private Chequer chequer;
        private Coordinate coordinate;
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

        public PlaceView build() {
            return new PlaceView(context, chequer, coordinate);
        }
    }

    public void setValues(PlaceView placeView) {
        this.chequer = placeView.chequer;
        this.coordinate = new Coordinate(placeView.coordinate.getColumn(), placeView.coordinate.getRow());
        this.chequerDrawable = placeView.chequerDrawable.getConstantState().newDrawable().mutate();
        this.hidden = placeView.hidden;

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = placeView.getHeight();
        layoutParams.width = placeView.getWidth();
        setLayoutParams(layoutParams);

        initDrawables();
    }

    public void setOnlyForeground() {
        ChequerDrawablePalette chequerDrawablePalette = ChequerDrawablePalette.getDefault(getResources());
        Drawable chequerDrawable = chequerDrawablePalette.getImage(chequer);
        setImageDrawable(chequerDrawable);
    }
}
