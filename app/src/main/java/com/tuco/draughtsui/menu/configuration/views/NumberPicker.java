package com.tuco.draughtsui.menu.configuration.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tuco.draughtsui.R;

import lombok.Getter;

public class NumberPicker extends LinearLayout {

    private Button decrement;
    private Button increment;
    private TextView numberPresenter;
    private float step;
    @Getter
    private float value;
    private float minValue;
    private float maxValue;
    private boolean floatingPoint;

    public NumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayoutFromResources(context);
        initViews();
        initUsingAttributes(context, attrs);
    }

    private void setLayoutFromResources(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.number_picker, this, true);
    }

    private void initViews() {
        decrement = findViewById(R.id.decrement);
        increment = findViewById(R.id.increment);
        numberPresenter = findViewById(R.id.numberPresenter);
    }

    private void initUsingAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NumberPicker,
                0, 0);

        step = a.getFloat(R.styleable.NumberPicker_step, 1);
        minValue = a.getFloat(R.styleable.NumberPicker_minValue, 1);
        maxValue = a.getFloat(R.styleable.NumberPicker_maxValue, 10);
        floatingPoint = a.getBoolean(R.styleable.NumberPicker_floatingPoint, false);

        value = a.getFloat(R.styleable.NumberPicker_defaultValue, 2);
        refreshNumberPresenter();

        increment.setOnClickListener(v -> incrementValue());
        decrement.setOnClickListener(v -> decrementValue());

        a.recycle();
    }

    protected void incrementValue() {
        if (value <= maxValue - step) {
            value += step;
            refreshNumberPresenter();
        }
    }

    protected void decrementValue() {
        if (value >= minValue + step) {
            value -= step;
            refreshNumberPresenter();
        }
    }

    private void refreshNumberPresenter() {
        numberPresenter.setText(floatingPoint ? String.valueOf(value) : String.valueOf((int) value));
    }
}
