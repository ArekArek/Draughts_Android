package com.tuco.draughtsui.menu.configuration.views;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.tuco.draughtsui.R;
import com.tuco.draughtsui.menu.configuration.utils.EnumSequentionWrapper;

public class EnumPicker extends AppCompatButton {

    private EnumSequentionWrapper enumSequentionWrapper;

    private OnClickListener baseOnClickListener = v -> {
        enumSequentionWrapper.next();
        refreshText();
    };

    public EnumPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context, Enum defaultValue) {
        this.enumSequentionWrapper = new EnumSequentionWrapper(defaultValue);

        super.setOnClickListener(baseOnClickListener);
        setTextColor(context.getColor(R.color.colorPrimaryDark));
        refreshText();
    }

    private void refreshText() {
        setText(enumSequentionWrapper.toString());
    }

    public Enum getValue() {
        return enumSequentionWrapper.getValue();
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener((v) -> {
            this.baseOnClickListener.onClick(v);
            l.onClick(v);
        });
    }
}
