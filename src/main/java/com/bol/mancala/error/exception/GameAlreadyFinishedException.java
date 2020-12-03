package com.bol.mancala.error.exception;

import java.util.UUID;

public class GameAlreadyFinishedException  extends BadRequestException {
    public GameAlreadyFinishedException(UUID id) {
        super(String.format("Game with id=%s already finished", id));
    }
}
