package com.tuco.draughtsui.game.board;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughts.movement.util.Movement;
import com.tuco.draughtsui.game.movement.HumanPositionLoader;

import java.util.List;

import lombok.Getter;

public class BoardView extends TableLayout {

    private Context context;

    @Getter
    private final HumanPositionLoader humanPositionLoader = HumanPositionLoader.getInstance();

    private static final TableLayout.LayoutParams rowLayout;

    static {
        rowLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0);
        rowLayout.weight = 1;
    }

    public BoardView(Context context) {
        super(context);
        this.context = context;
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void init(Board board) {
        fillTable(board);

        setStretchAllColumns(true);
        setShrinkAllColumns(true);
    }


    private void fillTable(Board board) {
        Chequer[][] base = board.getBase();
        int columnCount = 0;
        for (Chequer[] row : base) {
            TableRow tableRow = generateTableRow(row, columnCount);
            addView(tableRow);
            columnCount++;
        }
    }

    public void update(Board board) {
        removeAllViews();

        fillTable(board);

        invalidate();

        synchronized (this) {
            this.notify();
        }
    }

    public void highlightPossible(List<Coordinate> possiblePositions) {
        ((AppCompatActivity) context).runOnUiThread(() -> {
            for (int i = 0; i < getChildCount(); i++) {
                if (getChildAt(i) instanceof TableRow) {
                    TableRow tableRow = (TableRow) getChildAt(i);
                    for (int j = 0; j < tableRow.getChildCount(); j++) {
                        if (tableRow.getChildAt(j) instanceof PlaceView) {
                            PlaceView placeView = (PlaceView) tableRow.getChildAt(j);
                            highlitingPlace(placeView, possiblePositions);
                        }
                    }
                }
            }
        });
    }

    private void highlitingPlace(PlaceView placeView, List<Coordinate> possiblePositions) {
        if (!possiblePositions.contains(placeView.getCoordinate())) {
            placeView.setHidden(true);
            placeView.setClickable(false);
        } else {
            placeView.setHidden(false);
            placeView.setClickable(true);
            placeView.setOnClickListener(humanPositionLoader);
        }
        placeView.invalidate();
    }

    @NonNull
    private TableRow generateTableRow(Chequer[] row, int columnCount) {
        TableRow tableRow = new TableRow(context);

        int rowCount = 0;
        for (Chequer chequer : row) {
            Coordinate placeCoordinates = new Coordinate(columnCount, rowCount);

            PlaceView placeView = new PlaceView.Builder(context)
                    .setChequer(chequer)
                    .setCoordinate(placeCoordinates)
                    .build();

            placeView.setRotation(getRotation()*(-1));
            tableRow.addView(placeView);
            rowCount++;
        }

        tableRow.setLayoutParams(rowLayout);
        return tableRow;
    }

    public PlaceView getPlaceView(Coordinate coordinate) {
        TableRow row = (TableRow) getChildAt(coordinate.getColumn());
        return (PlaceView) row.getChildAt(coordinate.getRow());
    }

    public void showMove(Movement movement) {
        new MoveAnimator(this, movement).start();
    }
}
