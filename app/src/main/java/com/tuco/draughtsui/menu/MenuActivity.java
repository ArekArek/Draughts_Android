package com.tuco.draughtsui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.tuco.draughtsui.R;
import com.tuco.draughtsui.game.GameActivity;
import com.tuco.draughtsui.menu.configuration.PlayerConfigurationView;

public class MenuActivity extends AppCompatActivity {

    private Button startButton;
    private PlayerConfigurationView playerWhiteConfiguration;
    private PlayerConfigurationView playerBlackConfiguration;

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
    }

    private void startGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("whiteConfiguration", playerWhiteConfiguration.getData());
        intent.putExtra("blackConfiguration", playerBlackConfiguration.getData());
        startActivity(intent);
    }
}
