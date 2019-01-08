package com.tuco.draughtsui.menu.configuration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.tuco.draughts.game.heuristic.Heuristic;
import com.tuco.draughts.movement.maker.AlgorithmType;
import com.tuco.draughtsui.R;
import com.tuco.draughtsui.menu.configuration.enums.DifficultyType;
import com.tuco.draughtsui.menu.configuration.enums.PlayerType;
import com.tuco.draughtsui.menu.configuration.views.EnumPicker;
import com.tuco.draughtsui.menu.configuration.views.NumberPicker;


public class PlayerConfigurationView extends LinearLayout {

    private EnumPicker playerTypePicker;
    private EnumPicker difficultyPicker;
    private LinearLayout aiMenu;
    private TableLayout aiConfiguration;

    private NumberPicker depthPicker;
    private EnumPicker algorithmPicker;
    private EnumPicker heuristicPicker;
    private NumberPicker timeLimitPicker;

    public PlayerConfigurationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayoutFromResources(context);
        initViews();
        initUsingAttributes(context, attrs);
    }

    private void setLayoutFromResources(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.player_configuration_view, this, true);
    }

    private void initUsingAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PlayerConfigurationView,
                0, 0);

        GradientDrawable background = new GradientDrawable();
        background.setStroke(16, context.getColor(R.color.enabledBackground));
        background.setCornerRadius(16);
        try {
            if (a.getInteger(R.styleable.PlayerConfigurationView_type, 0) == 0) {
                background.setColor(context.getColor(R.color.white));
                setBackground(background);
            } else {
                background.setColor(context.getColor(R.color.black));
                setBackground(background);
            }
        } finally {
            a.recycle();

        }
    }

    private void initViews() {
        initPlayerTypePicker();
        initDifficultPicker();

        initAiConfigurationViews();
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

    private void initAiConfigurationViews() {
        depthPicker = findViewById(R.id.depthPicker);

        algorithmPicker = findViewById(R.id.algorithmPicker);
        algorithmPicker.init(getContext(), AlgorithmType.MINMAX);

        heuristicPicker = findViewById(R.id.heuristicPicker);
        heuristicPicker.init(getContext(), Heuristic.SIMPLE);

        timeLimitPicker = findViewById(R.id.timeLimitPicker);
    }

    public PlayerConfigurationDTO getData() {
        if (playerTypePicker.getValue() == PlayerType.HUMAN) {
            return PlayerConfigurationDTO.builder()
                    .human(true)
                    .build();
        } else {
            return PlayerConfigurationDTO.builder()
                    .human(false)
                    .difficultyType((DifficultyType) difficultyPicker.getValue())
                    .depth(depthPicker.getValue())
                    .algorithm((AlgorithmType) algorithmPicker.getValue())
                    .heuristic((Heuristic) heuristicPicker.getValue())
                    .timeLimit(timeLimitPicker.getValue())
                    .build();
        }
    }
}
