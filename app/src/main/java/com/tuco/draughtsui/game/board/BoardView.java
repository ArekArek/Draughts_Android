package com.tuco.draughtsui.game.board;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.tuco.draughts.board.Board;
import com.tuco.draughts.board.Chequer;

public class BoardView extends TableLayout {

    private Context context;
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
        for (Chequer[] row : base) {
            TableRow tableRow = generateTableRow(row);
            addView(tableRow);
        }
    }

    public void update(Board board) {
        removeAllViews();

        fillTable(board);

        invalidate();
        postInvalidate();
    }


    @NonNull
    private TableRow generateTableRow(Chequer[] row) {
        TableRow tableRow = new TableRow(context);

        for (Chequer chequer : row) {
            PlaceView placeView = new PlaceView(context, chequer);
            tableRow.addView(placeView);
        }

        tableRow.setLayoutParams(rowLayout);
        return tableRow;
    }
}
