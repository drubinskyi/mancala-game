package com.bol.mancala.error.exception;

public class WrongPitException extends BadRequestException {
    public WrongPitException(int index) {
        super(String.format("Pit with index=%s not found", index));
    }
}
