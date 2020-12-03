package com.bol.mancala.game.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class NewGameRequestDTO {
    @NotNull(message = "playerOneId should not be null")
    private UUID playerOneId;
    @NotNull(message = "playerTwoId should not be null")
    private UUID playerTwoId;

    @JsonCreator
    public NewGameRequestDTO(@JsonProperty("playerOneId") UUID playerOneId,
                             @JsonProperty("playerTwoId") UUID playerTwoId) {
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
    }
}
