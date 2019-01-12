package com.tuco.draughtsui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tuco.draughtsui.R;
import com.tuco.draughtsui.game.GameActivity;
import com.tuco.draughtsui.menu.configuration.PlayerConfigurationView;
import com.tuco.draughtsui.menu.configuration.enums.BoolType;
import com.tuco.draughtsui.menu.configuration.views.EnumPicker;

public class MenuActivity extends AppCompatActivity {

    private Button startButton;
    private PlayerConfigurationView playerWhiteConfiguration;
    private PlayerConfigurationView playerBlackConfiguration;
    private EnumPicker logPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();
    }

    private void initViews() {
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener((l) -> startGameActivity());

        playerWhiteConfiguration = findViewById(R.id.playerWhiteConfiguration);
        playerBlackConfiguration = findViewById(R.id.playerBlackConfiguration);

        logPicker = findViewById(R.id.logPicker);
        logPicker.init(getApplicationContext(), BoolType.FALSE);
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("whiteConfiguration", playerWhiteConfiguration.getData());
        intent.putExtra("blackConfiguration", playerBlackConfiguration.getData());
        intent.putExtra("logs", logPicker.getValue() == BoolType.TRUE);
        startActivity(intent);
    }
}
