package com.bol.mancala.game.model;

import com.bol.mancala.error.exception.EmptyPitException;
import com.bol.mancala.error.exception.WrongPitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class BoardTest {
    @Mock
    private Pit pit;
    @Mock
    private List<Pit> pits;

    private Board board;

    @BeforeEach
    public void beforeEach() {
        board = new Board();
    }

    @ParameterizedTest
    @MethodSource("valuesForTestMove")
    public void testMove(List<Pit> playerOnePits, Kalah playerOneKalah, List<Pit> playerTwoPits, Kalah playerTwoKalah, int pitIndex, boolean firstPlayer,
                         List<Pit> playerOnePitsExpected, Kalah playerOneKalahExpected, List<Pit> playerTwoPitsExpected, Kalah playerTwoKalahExpected) {
        ReflectionTestUtils.setField(board, "playerOnePits", playerOnePits);
        ReflectionTestUtils.setField(board, "playerOneKalah", playerOneKalah);
        ReflectionTestUtils.setField(board, "playerTwoPits", playerTwoPits);
        ReflectionTestUtils.setField(board, "playerTwoKalah", playerTwoKalah);

        board.move(pitIndex, firstPlayer);

        Assertions.assertEquals(board.getPlayerOnePits(), playerOnePitsExpected);
        Assertions.assertEquals(board.getPlayerOneKalah(), playerOneKalahExpected);
        Assertions.assertEquals(board.getPlayerTwoPits(), playerTwoPitsExpected);
        Assertions.assertEquals(board.getPlayerTwoKalah(), playerTwoKalahExpected);
    }

    private static Stream<Arguments> valuesForTestMove() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(new Pit(7), new Pit(6), new Pit(6), new Pit(6), new Pit(6), new Pit(6)),
                        Kalah.createKalah().addStones(12),
                        Arrays.asList(new Pit(6), new Pit(6), new Pit(6), new Pit(6), new Pit(6), new Pit(6)),
                        Kalah.createKalah().addStones(12),
                        0,
                        true,
                        Arrays.asList(new Pit(0), new Pit(7), new Pit(7), new Pit(7), new Pit(7), new Pit(7)),
                        Kalah.createKalah().addStones(13),
                        Arrays.asList(new Pit(7), new Pit(6), new Pit(6), new Pit(6), new Pit(6), new Pit(6)),
                        Kalah.createKalah().addStones(12)
                        ),
                Arguments.of(
                        Arrays.asList(new Pit(4), new Pit(8), new Pit(18), new Pit(0), new Pit(3), new Pit(6)),
                        Kalah.createKalah().addStones(10),
                        Arrays.asList(new Pit(0), new Pit(7), new Pit(12), new Pit(6), new Pit(6), new Pit(6)),
                        Kalah.createKalah().addStones(2),
                        2,
                        true,
                        Arrays.asList(new Pit(5), new Pit(9), new Pit(1), new Pit(2), new Pit(5), new Pit(8)),
                        Kalah.createKalah().addStones(12),
                        Arrays.asList(new Pit(2), new Pit(8), new Pit(13), new Pit(7), new Pit(7), new Pit(7)),
                        Kalah.createKalah().addStones(2)
                        ),
                Arguments.of(
                        Arrays.asList(new Pit(5), new Pit(5), new Pit(5), new Pit(5), new Pit(5), new Pit(5)),
                        Kalah.createKalah().addStones(10),
                        Arrays.asList(new Pit(8), new Pit(8), new Pit(1), new Pit(0), new Pit(8), new Pit(8)),
                        Kalah.createKalah().addStones(2),
                        2,
                        false,
                        Arrays.asList(new Pit(5), new Pit(5), new Pit(0), new Pit(5), new Pit(5), new Pit(5)),
                        Kalah.createKalah().addStones(10),
                        Arrays.asList(new Pit(8), new Pit(8), new Pit(0), new Pit(0), new Pit(8), new Pit(8)),
                        Kalah.createKalah().addStones(8)
                        )
        );
    }


    @Test
    public void testMoveShouldReturnEmptyPitException() {
        ReflectionTestUtils.setField(board, "playerOnePits", Collections.singletonList(pit));
        Mockito.when(pit.isEmpty()).thenReturn(true);

        Assertions.assertThrows(EmptyPitException.class, () -> {
            board.move(0, true);
        });
    }

    @Test
    public void testMoveShouldReturnWrongPitException() {
        Assertions.assertThrows(WrongPitException.class, () -> {
            board.move(8, false);
        });
    }

    @ParameterizedTest
    @MethodSource("valuesForTestLandsInKalah")
    public void lastStoneShouldLandInKalah(int stones, int pitIndex) {
        ReflectionTestUtils.setField(board, "playerOnePits", pits);

        Mockito.when(pits.get(pitIndex)).thenReturn(pit);
        Mockito.when(pit.getStones()).thenReturn(stones);

        Assertions.assertTrue(board.isLastStoneLandsInKalah(pitIndex, true));
    }

    private static Stream<Arguments> valuesForTestLandsInKalah() {
        return Stream.of(
                Arguments.of(6, 0),
                Arguments.of(5, 1),
                Arguments.of(4, 2),
                Arguments.of(3, 3),
                Arguments.of(2, 4),
                Arguments.of(1, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("valuesForTestNotLandsInKalah")
    public void lastStoneShouldNotLandInKalah(int stones, int pitIndex) {
        ReflectionTestUtils.setField(board, "playerOnePits", pits);

        Mockito.when(pits.get(pitIndex)).thenReturn(pit);
        Mockito.when(pit.getStones()).thenReturn(stones);

        Assertions.assertFalse(board.isLastStoneLandsInKalah(pitIndex, true));

    }

    private static Stream<Arguments> valuesForTestNotLandsInKalah() {
        return Stream.of(
                Arguments.of(6, 3),
                Arguments.of(5, 4),
                Arguments.of(4, 3));
    }

    @Test
    public void testIsAnyPitsEmptyShouldReturnFalse() {
        Assertions.assertFalse(board.isAnyPlayersPitsEmpty());
    }

    @Test
    public void testIsAnyPitsEmptyShouldReturnTrue() {
        ReflectionTestUtils.setField(board, "playerOnePits", Collections.singletonList(pit));

        Mockito.when(pit.isNotEmpty()).thenReturn(false);

        Assertions.assertTrue(board.isAnyPlayersPitsEmpty());
    }

    @Test
    public void testFinalizeBoard() {
        board.finalizeBoard();

        Assertions.assertEquals(board.getPlayerOneKalah().getStones(), 36);
        Assertions.assertEquals(board.getPlayerTwoKalah().getStones(), 36);
        board.getPlayerOnePits().forEach(it ->
                Assertions.assertEquals(it.getStones(), 0)
        );
        board.getPlayerTwoPits().forEach(it ->
                Assertions.assertEquals(it.getStones(), 0)
        );
    }
}
