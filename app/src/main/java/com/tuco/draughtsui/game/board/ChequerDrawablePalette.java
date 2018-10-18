package com.tuco.draughtsui.game.board;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.tuco.draughts.board.Chequer;
import com.tuco.draughtsui.R;

import lombok.Builder;
import lombok.Getter;

@Builder(builderClassName = "Builder")
public class ChequerDrawablePalette {

    private Resources resources;

    private Drawable black;
    private Drawable blackKing;
    private Drawable white;
    private Drawable whiteKing;

    @Getter
    private Drawable clickView;

    private Drawable disabledBackground;
    private Drawable enabledBackground;

    public static ChequerDrawablePalette getDefault(Resources resources) {
        Builder builder = new Builder();

        builder.resources = resources;

        builder.black = ResourcesCompat.getDrawable(resources, R.drawable.simple_black, null);
        builder.blackKing = ResourcesCompat.getDrawable(resources, R.drawable.simple_black_king, null);
        builder.white = ResourcesCompat.getDrawable(resources, R.drawable.simple_white, null);
        builder.whiteKing = ResourcesCompat.getDrawable(resources, R.drawable.simple_white_king, null);

        builder.clickView = ResourcesCompat.getDrawable(resources, R.drawable.simple_click_view, null);

        builder.disabledBackground = new ColorDrawable(ResourcesCompat.getColor(resources, R.color.disabledBackground, null));
        builder.enabledBackground = new ColorDrawable(ResourcesCompat.getColor(resources, R.color.enabledBackground, null));

        return builder.build();
    }

    public Drawable getImage(Chequer chequer) {
        switch (chequer) {
            case BLACK:
                return black;
            case BLACK_KING:
                return blackKing;
            case WHITE:
                return white;
            case WHITE_KING:
                return whiteKing;
            default:
                return null;
        }
    }

    public Drawable getBackground(Chequer chequer) {
        switch (chequer) {
            case DISABLED:
                return disabledBackground;
            default:
                return enabledBackground;
        }
    }
}
