package com.bol.mancala.game.model;

import com.bol.mancala.game.GameResult;
import com.bol.mancala.player.Player;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

import java.util.*;

import static com.bol.mancala.game.GameResult.*;

@Getter
@KeySpace("games")
public class Game {
    @Id
    private final UUID id;
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;
    private Player currentMove;
    private GameResult result;

    public Game(Player playerOne, Player playerTwo) {
        this.id = UUID.randomUUID();
        this.board = new Board();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.currentMove = new Random().nextInt(2) == 0 ? playerOne : playerTwo;
        this.result = IN_PROGRESS;
    }

    public void move(int pitIndex, Player player) {
        boolean firstPlayer = player == playerOne;

        boolean lastStoneLandedInKalah = board.isLastStoneLandsInKalah(pitIndex, firstPlayer);
        board.move(pitIndex, firstPlayer);

        if (lastStoneLandedInKalah) {
            currentMove = player;
        } else {
            currentMove = player.equals(playerOne) ? playerTwo : playerOne;
        }

        if (board.isAnyPlayersPitsEmpty()) {
            calculateWinner();
        }
    }

    private void calculateWinner() {
        board.finalizeBoard();

        int playerOneStones = board.getPlayerOneKalah().getStones();
        int playerTwoStones = board.getPlayerTwoKalah().getStones();

        if (playerOneStones == playerTwoStones) {
            result = GameResult.DRAW;
        } else {
            result = playerOneStones > playerTwoStones ? GameResult.FIRST_PLAYER_WIN : GameResult.SECOND_PLAYER_WIN;
        }
    }
}
