package com.bol.mancala.error.exception;

import java.util.UUID;

public class GameNotFoundException extends ResourceNotFoundException {
    public GameNotFoundException(UUID id) {
        super(String.format("Game with id=%s not found", id));
    }
}
