package com.bol.mancala.game.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
public class Kalah {
    private int stones;

    public static Kalah createKalah() {
        return new Kalah(0);
    }

    public Kalah addStones(int value) {
        return new Kalah(stones + value);
    }
}
