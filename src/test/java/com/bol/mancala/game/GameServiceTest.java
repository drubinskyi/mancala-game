package com.bol.mancala.game;

import com.bol.mancala.error.exception.GameAlreadyFinishedException;
import com.bol.mancala.error.exception.GameNotFoundException;
import com.bol.mancala.error.exception.WrongPlayerMoveException;
import com.bol.mancala.game.model.Game;
import com.bol.mancala.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository repository;
    @InjectMocks
    private GameService service;

    @Test
    void testCreateGame() {
        Player playerOne = new Player();
        Player playerTwo = new Player();
        Game game = new Game(playerOne, playerTwo);

        Mockito.when(repository.save(any())).thenReturn(game);

        Assertions.assertEquals(service.createGame(playerOne, playerTwo), game);
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Game game = new Game(new Player(), new Player());

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(game));

        Assertions.assertEquals(service.findById(id), game);
    }

    @Test
    void testFindByIdShouldReturnNotFoundException() {
        Assertions.assertThrows(GameNotFoundException.class, () -> {
            service.findById(UUID.randomUUID());
        });
    }

    @Test
    void testUpdateGame() {
        UUID id = UUID.randomUUID();
        Game game = new Game(new Player(), new Player());
        Game gameSpy = Mockito.spy(game);
        Player currentMove = game.getCurrentMove();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(gameSpy));
        Game result = service.updateGame(id, 0, currentMove);

        Mockito.verify(gameSpy).move(0, currentMove);
        Assertions.assertEquals(result, gameSpy);
    }

    @Test
    void testUpdateGameShouldReturnWrongMoveException() {
        UUID id = UUID.randomUUID();
        Game game = new Game(new Player(), new Player());

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(game));

        Assertions.assertThrows(WrongPlayerMoveException.class, () -> {
            service.updateGame(id, 0, new Player());
        });
    }

    @Test
    void testUpdateGameShouldReturnGameFinishedException() {
        UUID id = UUID.randomUUID();
        Game game = new Game(new Player(), new Player());
        ReflectionTestUtils.setField(game, "result", GameResult.DRAW);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(game));

        Assertions.assertThrows(GameAlreadyFinishedException.class, () -> {
            service.updateGame(id, 0, game.getCurrentMove());
        });
    }
}
