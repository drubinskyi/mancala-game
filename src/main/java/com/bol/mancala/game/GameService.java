package com.bol.mancala.game;

import com.bol.mancala.error.exception.GameAlreadyFinishedException;
import com.bol.mancala.error.exception.GameNotFoundException;
import com.bol.mancala.error.exception.WrongPlayerMoveException;
import com.bol.mancala.game.model.Game;
import com.bol.mancala.player.Player;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.bol.mancala.game.GameResult.*;

@Service
public class GameService {
    private GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game createGame(Player playerOne, Player playerTwo) {
        return repository.save(new Game(playerOne, playerTwo));
    }

    public Game findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
    }

    public Game updateGame(UUID id, int pitIndex, Player player) {
        Game game = findById(id);

        if (game.getResult() != IN_PROGRESS) {
            throw new GameAlreadyFinishedException(id);
        }

        if (player != game.getCurrentMove()) {
            throw new WrongPlayerMoveException(player.getId());
        }

        game.move(pitIndex, player);

        return findById(id);
    }

}
