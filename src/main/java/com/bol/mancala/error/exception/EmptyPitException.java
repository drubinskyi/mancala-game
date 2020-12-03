package com.bol.mancala.error.exception;

public class EmptyPitException extends BadRequestException{
    public EmptyPitException(int index) {
        super(String.format("Pit with index=%s is empty", index));
    }
}
