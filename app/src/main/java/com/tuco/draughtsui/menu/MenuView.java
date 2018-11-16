package com.tuco.draughtsui.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.tuco.draughtsui.R;
import com.tuco.draughtsui.menu.configuration.enums.DifficultyType;
import com.tuco.draughtsui.menu.configuration.enums.PlayerType;
import com.tuco.draughtsui.menu.configuration.views.EnumPicker;


public class MenuView extends LinearLayout {

    private EnumPicker playerTypePicker;
    private EnumPicker difficultyPicker;
    private LinearLayout aiMenu;
    private LinearLayout aiConfiguration;

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayoutFromResources(context);
        initViews();
        initUsingAttributes(context, attrs);
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
                setBackgroundColor(context.getColor(R.color.white));
            } else {
                setBackgroundColor(context.getColor(R.color.black));
            }
        } finally {
            a.recycle();

        }
    }

    private void initViews() {
        initPlayerTypePicker();
        initDifficultPicker();
    }

    private void initPlayerTypePicker() {
        aiMenu = findViewById(R.id.aiMenu);

        playerTypePicker = findViewById(R.id.playerTypePicker);
        playerTypePicker.init(getContext(), PlayerType.HUMAN);
        playerTypePicker.setOnClickListener((l) -> {
            aiMenu.setVisibility(playerTypePicker.getValue() == PlayerType.AI ? VISIBLE : INVISIBLE);
        });
    }

    private void initDifficultPicker() {
        aiConfiguration = findViewById(R.id.aiConfiguration);

        difficultyPicker = findViewById(R.id.difficultyPicker);
        difficultyPicker.init(getContext(), DifficultyType.MEDIUM);
        difficultyPicker.setOnClickListener((l) -> {
            aiConfiguration.setVisibility(difficultyPicker.getValue() == DifficultyType.CUSTOM ? VISIBLE : INVISIBLE);
        });
    }
}
