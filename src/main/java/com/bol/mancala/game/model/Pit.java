package com.bol.mancala.game.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static com.bol.mancala.config.MancalaConstants.STONES_IN_PIT;
import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor(access = PACKAGE)
@Getter
@EqualsAndHashCode
public class Pit {
    private int stones;

    public static Pit createPit() {
        return new Pit(STONES_IN_PIT);
    }

    public static Pit createEmptyPit() {
        return new Pit(0);
    }

    public Pit addStone() {
        return new Pit(++stones);
    }

    public boolean isEmpty() {
        return stones == 0;
    }

    public boolean isNotEmpty() {
        return stones != 0;
    }
}
