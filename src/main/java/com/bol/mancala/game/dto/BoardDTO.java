package com.bol.mancala.game.dto;

import com.bol.mancala.game.model.Board;
import com.bol.mancala.game.model.Pit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class BoardDTO {
    private List<Integer> playerOnePits;
    private int playerOneKalah;
    private List<Integer> playerTwoPits;
    private int playerTwoKalah;

    static BoardDTO fromBoard(Board board) {
        return new BoardDTO(
                board.getPlayerOnePits().stream().map(Pit::getStones).collect(Collectors.toList()),
                board.getPlayerOneKalah().getStones(),
                board.getPlayerTwoPits().stream().map(Pit::getStones).collect(Collectors.toList()),
                board.getPlayerTwoKalah().getStones()
        );
    }
}
