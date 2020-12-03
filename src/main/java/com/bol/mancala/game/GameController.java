package com.bol.mancala.game;

import com.bol.mancala.game.dto.GameDTO;
import com.bol.mancala.game.dto.MoveDTO;
import com.bol.mancala.game.dto.NewGameRequestDTO;
import com.bol.mancala.player.Player;
import com.bol.mancala.player.PlayerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.bol.mancala.game.dto.GameDTO.fromGame;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private GameService gameService;
    private PlayerService playerService;

    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public GameDTO returnGame(@PathVariable UUID id) {
        return fromGame(gameService.findById(id));
    }

    @PostMapping
    public GameDTO createGame(@RequestBody @Valid NewGameRequestDTO request) {
        Player playerOne = playerService.findById(request.getPlayerOneId());
        Player playerTwo = playerService.findById(request.getPlayerTwoId());

        return fromGame(gameService.createGame(playerOne, playerTwo));
    }

    @PutMapping("/{id}")
    public GameDTO move(@PathVariable UUID id, @RequestBody @Valid MoveDTO move) {
        Player player = playerService.findById(move.getPlayerId());
        return fromGame(gameService.updateGame(id, move.getPitIndex(), player));
    }
}
