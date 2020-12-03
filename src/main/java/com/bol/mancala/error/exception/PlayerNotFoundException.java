package com.bol.mancala.error.exception;

import java.util.UUID;

public class PlayerNotFoundException extends ResourceNotFoundException {
    public PlayerNotFoundException(UUID id) {
        super(String.format("Player with id=%s not found", id));
    }
}
