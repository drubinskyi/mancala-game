package com.bol.mancala.game.model;

import com.bol.mancala.error.exception.EmptyPitException;
import com.bol.mancala.error.exception.WrongPitException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.bol.mancala.config.MancalaConstants.*;
import static com.bol.mancala.game.model.Kalah.createKalah;
import static com.bol.mancala.game.model.Pit.*;

@Getter
public class Board {
    private List<Pit> playerOnePits;
    private Kalah playerOneKalah;
    private List<Pit> playerTwoPits;
    private Kalah playerTwoKalah;

    public Board() {
        initBoard();
    }

    public void move(int pitIndex, boolean firstPlayer) {
        Pit pit = getPit(pitIndex, firstPlayer);
        int stones = pit.getStones();

        if (pit.isEmpty()) {
            throw new EmptyPitException(pitIndex);
        }

        emptyPit(pitIndex, firstPlayer);
        IntStream.range(pitIndex + 1, pitIndex + stones).forEach(i -> sewStone(i % PITS_IN_LAP, firstPlayer));
        handleLastPit((pitIndex + stones) % PITS_IN_LAP, firstPlayer);
    }

    public boolean isLastStoneLandsInKalah(int pitIndex, boolean firstPlayer) {
        return (pitIndex + getPit(pitIndex, firstPlayer).getStones()) % PITS_IN_LAP == KALAH_INDEX;
    }

    public boolean isAnyPlayersPitsEmpty() {
        return playerOnePits.stream().noneMatch(Pit::isNotEmpty) || playerTwoPits.stream().noneMatch(Pit::isNotEmpty);
    }

    public void finalizeBoard() {
        addStonesToKalah(playerOnePits.stream().map(Pit::getStones).reduce(0, Integer::sum), true);
        addStonesToKalah(playerTwoPits.stream().map(Pit::getStones).reduce(0, Integer::sum), false);

        IntStream.range(0, PITS_AMOUNT_FOR_PLAYER)
                .forEach(idx -> {
                            emptyPit(idx, true);
                            emptyPit(idx, false);
                        }
                );
    }

    private void initBoard() {
        playerOnePits = new ArrayList<>(); {
            IntStream.range(0, PITS_AMOUNT_FOR_PLAYER).forEach(it -> playerOnePits.add(createPit()));
        }
        playerOneKalah = createKalah();

        playerTwoPits = new ArrayList<>();
        IntStream.range(0, PITS_AMOUNT_FOR_PLAYER).forEach(it -> playerTwoPits.add(createPit()));
        playerTwoKalah = createKalah();
    }

    private Pit getPit(int index, boolean firstPlayer) {
        if (index < 0 || index >= KALAH_INDEX) {
            throw new WrongPitException(index);
        }

        return getPits(firstPlayer).get(index);
    }

    private void sewStone(int pitIndex, boolean firstPlayer) {
        if (pitIndex < KALAH_INDEX) {
            addStoneToPit(pitIndex, firstPlayer);
        } else if (pitIndex == KALAH_INDEX) {
            addStonesToKalah(1, firstPlayer);
        } else {
            addStoneToPit(pitIndex - PITS_AMOUNT_FOR_PLAYER - 1, !firstPlayer);
        }
    }

    private void handleLastPit(int pitIndex, boolean firstPlayer) {
        if (pitIndex < KALAH_INDEX) {
            int stonesInTheLastPit = getPit(pitIndex, firstPlayer).getStones();
            int oppositePitIndex = PITS_AMOUNT_FOR_PLAYER - 1 - pitIndex;
            int stonesInOppositePit = getPit(oppositePitIndex, !firstPlayer).getStones();

            if (stonesInTheLastPit == 0 && stonesInOppositePit != 0) {
                emptyPit(oppositePitIndex, !firstPlayer);
                addStonesToKalah(stonesInOppositePit + 1, firstPlayer);
            } else {
                sewStone(pitIndex, firstPlayer);
            }
        } else {
            sewStone(pitIndex, firstPlayer);
        }
    }

    private void emptyPit(int index, boolean firstPlayer) {
        getPits(firstPlayer).set(index, createEmptyPit());
    }

    private void addStoneToPit(int index, boolean firstPlayer) {
        List<Pit> pits = getPits(firstPlayer);
        pits.set(index, pits.get(index).addStone());
    }

    private void addStonesToKalah(int value, boolean firstPlayer) {
        if (firstPlayer) {
            playerOneKalah = playerOneKalah.addStones(value);
        } else {
            playerTwoKalah = playerTwoKalah.addStones(value);
        }
    }

    private List<Pit> getPits(boolean firstPlayer) {
        return firstPlayer ? playerOnePits : playerTwoPits;
    }
}
