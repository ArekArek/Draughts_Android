package com.tuco.draughtsui.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.tuco.draughtsui.R;


public class MenuView extends LinearLayout {

    private ToggleButton playerTypePicker;
    private LinearLayout aiMenu;

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayoutFromResources(context);
        initUsingAttributes(context, attrs);
        initViews();
    }

    private void setLayoutFromResources(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.menu_view, this, true);
    }

    private void initUsingAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MenuView,
                0, 0);

        try {
            if (a.getInteger(R.styleable.MenuView_type, 0) == 0) {
                setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                setBackgroundColor(getResources().getColor(R.color.black));
            }
        } finally {
            a.recycle();

        }
    }

    private void initViews() {
        aiMenu = findViewById(R.id.aiMenu);

        playerTypePicker = findViewById(R.id.playerTypePicker);
        playerTypePicker.setOnCheckedChangeListener((buttonView, isChecked) -> {
            aiMenu.setVisibility(isChecked ? VISIBLE : INVISIBLE);
        });
    }
}
