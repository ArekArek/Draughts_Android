package com.tuco.draughtsui.game.board;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.TableRow;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;

import lombok.Getter;

public class PlaceView extends android.support.v7.widget.AppCompatImageView {

    private static final int CLICK_DURATION = 600;

    private Chequer chequer;

    @Getter
    private Coordinate coordinate;

    private TransitionDrawable transitionDrawable;
    private Drawable chequerDrawable;

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

        if (chequer == Chequer.DISABLED) {
            setClickable(false);
        } else {
            setClickable(true);
            setOnClickListener(onClickListener);
        }

        setLayoutParams(cellLayout);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (chequerDrawable != null) {
            chequerDrawable.setBounds(canvas.getClipBounds());
            chequerDrawable.draw(canvas);
        }
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
