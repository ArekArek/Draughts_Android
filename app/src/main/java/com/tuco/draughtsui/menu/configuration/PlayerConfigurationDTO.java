package com.tuco.draughtsui.menu.configuration;

import android.os.Parcel;
import android.os.Parcelable;

import com.tuco.draughts.movement.maker.AlgorithmType;
import com.tuco.draughts.movement.maker.Heuristic;
import com.tuco.draughtsui.menu.configuration.enums.DifficultyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayerConfigurationDTO implements Parcelable {
    private boolean human;
    private DifficultyType difficultyType;
    private float depth;
    private AlgorithmType algorithm;
    private Heuristic heuristic;
    private float timeLimit;

    protected PlayerConfigurationDTO(Parcel in) {
        this.human = (Boolean) in.readValue(null);
        if (!isHuman()) {
            this.difficultyType = (DifficultyType) in.readSerializable();
            this.depth = in.readFloat();
            this.algorithm = (AlgorithmType) in.readSerializable();
            this.heuristic = (Heuristic) in.readSerializable();
            this.timeLimit = in.readFloat();

            AIConfigurationResolver.resolveAiType(this);
        }
    }

    public static final Creator<PlayerConfigurationDTO> CREATOR = new Creator<PlayerConfigurationDTO>() {
        @Override
        public PlayerConfigurationDTO createFromParcel(Parcel in) {
            return new PlayerConfigurationDTO(in);
        }

        @Override
        public PlayerConfigurationDTO[] newArray(int size) {
            return new PlayerConfigurationDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(human);
        if (!isHuman()) {
            dest.writeSerializable(difficultyType);
            dest.writeFloat(depth);
            dest.writeSerializable(algorithm);
            dest.writeSerializable(heuristic);
            dest.writeFloat(timeLimit);
        }
    }
}
