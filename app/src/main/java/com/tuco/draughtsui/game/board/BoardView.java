package com.tuco.draughtsui.game.board;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;
import com.tuco.draughts.board.util.Coordinate;
import com.tuco.draughtsui.game.movement.HumanPositionLoader;

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
    }


    @NonNull
    private TableRow generateTableRow(Chequer[] row, int columnCount) {
        TableRow tableRow = new TableRow(context);

        int rowCount = 0;
        for (Chequer chequer : row) {
            PlaceView placeView = new PlaceView.Builder(context)
                    .setChequer(chequer)
                    .setCoordinate(new Coordinate(columnCount, rowCount))
                    .setOnClickListener(humanPositionLoader)
                    .build();

            tableRow.addView(placeView);
            rowCount++;
        }

        tableRow.setLayoutParams(rowLayout);
        return tableRow;
    }
}
