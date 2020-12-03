package com.bol.mancala.error.exception;

import java.util.UUID;

public class WrongPlayerMoveException extends BadRequestException {
    public WrongPlayerMoveException(UUID id) {
        super(String.format("Player with id=%s is not allowed to move now", id));
    }
}