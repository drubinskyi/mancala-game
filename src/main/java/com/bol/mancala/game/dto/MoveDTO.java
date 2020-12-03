package com.bol.mancala.game.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class MoveDTO {
    @NotNull(message = "playerId should not be null")
    private UUID playerId;
    @NotNull(message = "pitIndex should not be null")
    private Integer pitIndex;

    @JsonCreator
    public MoveDTO(@JsonProperty("playerId") UUID playerId,
                   @JsonProperty("pitIndex") Integer pitIndex) {
        this.playerId = playerId;
        this.pitIndex = pitIndex;
    }
}
