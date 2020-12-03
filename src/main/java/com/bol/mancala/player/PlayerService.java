package com.bol.mancala.player;

import com.bol.mancala.error.exception.PlayerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlayerService {
    private PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }

    public Player createPlayer() {
        return repository.save(new Player());
    }

    public void deleteById(UUID id) {
        repository.delete(findById(id));
    }
}
