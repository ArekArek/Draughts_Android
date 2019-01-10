package com.tuco.draughtsui.menu.configuration;

import com.tuco.draughts.game.heuristic.Heuristic;
import com.tuco.draughts.movement.maker.AlgorithmType;
import com.tuco.draughtsui.menu.configuration.enums.BoolType;
import com.tuco.draughtsui.menu.configuration.enums.DifficultyType;

public class AIConfigurationResolver {

    public static void resolveAiType(PlayerConfigurationDTO configurationDTO) {
        switch (configurationDTO.getDifficultyType()) {
            case EASY:
                easyConfiguration(configurationDTO);
                break;
            case MEDIUM:
                mediumConfiguration(configurationDTO);
                break;
            case HARD:
                hardConfiguration(configurationDTO);
                break;
        }
    }

    private static void easyConfiguration(PlayerConfigurationDTO configurationDTO) {
        configurationDTO.setDifficultyType(DifficultyType.EASY);
        configurationDTO.setDepth(0.5f);
        configurationDTO.setAlgorithm(AlgorithmType.MINMAX);
        configurationDTO.setHeuristic(Heuristic.SIMPLE);
        configurationDTO.setTimeLimit(0);
        configurationDTO.setQuiescence(BoolType.FALSE);
    }

    private static void mediumConfiguration(PlayerConfigurationDTO configurationDTO) {
        configurationDTO.setDifficultyType(DifficultyType.MEDIUM);
        configurationDTO.setDepth(2);
        configurationDTO.setAlgorithm(AlgorithmType.ALPHABETA);
        configurationDTO.setHeuristic(Heuristic.SIMPLE);
        configurationDTO.setTimeLimit(6);

        configurationDTO.setQuiescence(BoolType.TRUE);
    }

    private static void hardConfiguration(PlayerConfigurationDTO configurationDTO) {
        configurationDTO.setDifficultyType(DifficultyType.HARD);
        configurationDTO.setDepth(3);
        configurationDTO.setAlgorithm(AlgorithmType.SCOUT);
        configurationDTO.setHeuristic(Heuristic.SIMPLE);
        configurationDTO.setTimeLimit(0);
        configurationDTO.setQuiescence(BoolType.TRUE);
    }
}
