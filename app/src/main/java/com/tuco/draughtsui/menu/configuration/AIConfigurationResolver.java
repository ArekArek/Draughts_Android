package com.tuco.draughtsui.menu.configuration;

import com.tuco.draughts.movement.maker.AlgorithmType;
import com.tuco.draughts.movement.maker.Heuristic;
import com.tuco.draughtsui.menu.configuration.enums.DifficultyType;

public class AIConfigurationResolver {

    public static void resolveAiType(PlayerConfigurationDTO configurationDTO) {
        switch (configurationDTO.getDifficultyType()) {
            case EASY:
                setEasyConfiguration(configurationDTO);
                break;
            case MEDIUM:
                setMediumConfiguration(configurationDTO);
                break;
            case HARD:
                setHardConfiguration(configurationDTO);
                break;
        }
    }

    private static void setEasyConfiguration(PlayerConfigurationDTO configurationDTO) {
        configurationDTO.toBuilder()
                .difficultyType(DifficultyType.EASY)
                .depth(1)
                .algorithm(AlgorithmType.MINMAX)
                .heuristic(Heuristic.SIMPLE)
                .timeLimit(1)
                .build();
    }

    private static void setMediumConfiguration(PlayerConfigurationDTO configurationDTO) {
        configurationDTO.toBuilder()
                .difficultyType(DifficultyType.MEDIUM)
                .depth(3)
                .algorithm(AlgorithmType.ALPHABETA)
                .heuristic(Heuristic.SIMPLE)
                .timeLimit(5)
                .build();
    }

    private static void setHardConfiguration(PlayerConfigurationDTO configurationDTO) {
        configurationDTO.toBuilder()
                .difficultyType(DifficultyType.HARD)
                .depth(4.5f)
                .algorithm(AlgorithmType.SCOUT)
                .heuristic(Heuristic.SIMPLE)
                .timeLimit(10)
                .build();
    }
}
