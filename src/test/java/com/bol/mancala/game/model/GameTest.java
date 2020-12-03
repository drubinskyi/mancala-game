package com.bol.mancala.game.model;

import com.bol.mancala.game.GameResult;
import com.bol.mancala.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class GameTest {
    @Mock
    private Board board;

    private Player playerOne = new Player();
    private Player playerTwo = new Player();
    private Game game;

    @BeforeEach
    public void beforeEach() {
        game = new Game(playerOne, playerTwo);
        ReflectionTestUtils.setField(game, "board", board);
    }

    @Test
    public void testMove() {
        int pitIndex = 0;
        Player currentMove = game.getCurrentMove();

        Mockito.when(board.isLastStoneLandsInKalah(pitIndex, isFirstPlayer(currentMove))).thenReturn(false);
        Mockito.when(board.isAnyPlayersPitsEmpty()).thenReturn(false);

        game.move(pitIndex, currentMove);
        Mockito.verify(board).move(0, isFirstPlayer(currentMove));
        Assertions.assertNotEquals(currentMove, game.getCurrentMove());
    }

    @Test
    public void testMoveWithLastStoneInKalah() {
        int pitIndex = 0;
        Player currentMove = game.getCurrentMove();

        Mockito.when(board.isLastStoneLandsInKalah(pitIndex, isFirstPlayer(currentMove))).thenReturn(true);
        Mockito.when(board.isAnyPlayersPitsEmpty()).thenReturn(false);

        game.move(pitIndex, currentMove);
        Mockito.verify(board).move(0, isFirstPlayer(currentMove));
        Assertions.assertEquals(currentMove, game.getCurrentMove());
    }

    @Test
    public void testMoveWithFinishingGame() {
        int pitIndex = 0;
        Player currentMove = game.getCurrentMove();

        Mockito.when(board.isLastStoneLandsInKalah(pitIndex, isFirstPlayer(currentMove))).thenReturn(false);
        Mockito.when(board.isAnyPlayersPitsEmpty()).thenReturn(true);
        Mockito.when(board.getPlayerOneKalah()).thenReturn(Kalah.createKalah().addStones(20));
        Mockito.when(board.getPlayerTwoKalah()).thenReturn(Kalah.createKalah().addStones(50));

        game.move(pitIndex, currentMove);
        Mockito.verify(board).move(0, isFirstPlayer(currentMove));
        Assertions.assertNotEquals(currentMove, game.getCurrentMove());
        Mockito.verify(board).finalizeBoard();
        Assertions.assertEquals(game.getResult(), GameResult.SECOND_PLAYER_WIN);
    }

    private boolean isFirstPlayer(Player player) {
        return playerOne.equals(player);
    }
}
