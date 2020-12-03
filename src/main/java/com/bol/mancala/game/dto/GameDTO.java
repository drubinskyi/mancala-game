package com.bol.mancala.game.dto;

import com.bol.mancala.game.GameResult;
import com.bol.mancala.game.model.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

import static com.bol.mancala.game.dto.BoardDTO.fromBoard;

@AllArgsConstructor
@Getter
public class GameDTO {
    private UUID id;
    private BoardDTO board;
    private UUID playerOneId;
    private UUID playerTwoId;
    private UUID currentMove;
    private GameResult result;

    public static GameDTO fromGame(Game game) {
        return new GameDTO(
                game.getId(),
                fromBoard(game.getBoard()),
                game.getPlayerOne().getId(),
                game.getPlayerTwo().getId(),
                game.getCurrentMove().getId(),
                game.getResult()
        );
    }
}
