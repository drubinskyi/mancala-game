package com.bol.mancala.player;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Player returnPlayer(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public Player createPlayer() {
        return service.createPlayer();
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable UUID id) {
        service.deleteById(id);
    }
}

